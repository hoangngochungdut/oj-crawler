package com.hung.project.ui;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import com.hung.project.API.AnalysisResult;
import com.hung.project.API.GeminiAnalyzer;
import com.hung.project.API.SubmissionCrawlResult;
import com.hung.project.crawlers.CodeforcesCrawler;
import com.hung.project.models.Submission;
import com.hung.project.models.User;
import com.hung.project.repositories.SubmissionRepository;
import com.hung.project.repositories.UserRepository;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class UserPanel extends JPanel {

    private JTable table;
    private DefaultTableModel model;
    private UserRepository userRepository;
    private SubmissionRepository submissionRepository;
    private CodeforcesCrawler crawler;
    private GeminiAnalyzer analyzer;
    private SwingWorker<Void, Void> worker;

    // Bảng màu đồng bộ
    private final Color COLOR_BG = new Color(242, 245, 249);
    private final Color COLOR_CARD = Color.WHITE;
    private final Color COLOR_TEXT_MAIN = new Color(40, 44, 52);
    private final Color COLOR_ACCENT = new Color(0, 123, 255);
    private final Color COLOR_DANGER = new Color(220, 53, 69);
    private final Color COLOR_BORDER = new Color(230, 233, 237);

    public UserPanel() {
        crawler = new CodeforcesCrawler();
        userRepository = new UserRepository();
        submissionRepository = new SubmissionRepository();
        analyzer = new GeminiAnalyzer();

        setLayout(new BorderLayout());
        setBackground(COLOR_BG);
        setBorder(new EmptyBorder(25, 30, 25, 30));

        // 1. TOP SECTION (Title & Toolbar)
        add(createTopPanel(), BorderLayout.NORTH);

        // 2. CENTER SECTION (Modern Table)
        setupTableModel();
        table = new JTable(model);
        setupModernTable();

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(COLOR_BORDER, 1));
        scrollPane.getViewport().setBackground(COLOR_CARD);
        add(scrollPane, BorderLayout.CENTER);

        // 3. ACTIONS
        setupEvents();
        loadUsers();
    }

    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.setBorder(new EmptyBorder(0, 0, 20, 0));

        JLabel titleLabel = new JLabel("Users");
        titleLabel.setFont(new Font("Inter", Font.BOLD, 26));
        titleLabel.setForeground(COLOR_TEXT_MAIN);

        // Toolbar chứa các nút
        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        toolbar.setOpaque(false);

        JButton addButton = createStyledButton("Add User", COLOR_ACCENT, Color.WHITE);
        JButton deleteButton = createStyledButton("Delete", Color.WHITE, COLOR_DANGER);
        JButton analyzeButton = createStyledButton("Analyze", new Color(40, 167, 69), Color.WHITE);
        JButton stopButton = createStyledButton("Stop", Color.WHITE, COLOR_TEXT_MAIN);
        JButton refreshButton = createStyledButton("Refresh", Color.WHITE, COLOR_TEXT_MAIN);

        toolbar.add(refreshButton);
        toolbar.add(addButton);
        toolbar.add(deleteButton);
        toolbar.add(new JSeparator(JSeparator.VERTICAL));
        toolbar.add(analyzeButton);
        toolbar.add(stopButton);

        topPanel.add(titleLabel, BorderLayout.WEST);
        topPanel.add(toolbar, BorderLayout.EAST);

        // Gán sự kiện nhanh cho các nút
        addButton.addActionListener(e -> handleAddUser());
        deleteButton.addActionListener(e -> handleDeleteUser());
        refreshButton.addActionListener(e -> loadUsers());
        analyzeButton.addActionListener(e -> handleAnalyze(analyzeButton));
        stopButton.addActionListener(e -> {
            if (worker != null) worker.cancel(true);
        });

        return topPanel;
    }

    private void setupTableModel() {
        model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex >= 2) return Double.class; // Để render rate chuyên nghiệp
                return Object.class;
            }
        };
        model.addColumn("ID");
        model.addColumn("Username");
        model.addColumn("DS Rate");
        model.addColumn("Algo Rate");
        model.addColumn("AI Rate");
    }

    private void setupModernTable() {
        table.setRowHeight(50);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setSelectionBackground(new Color(232, 240, 254));
        table.setSelectionForeground(COLOR_TEXT_MAIN);
        table.setFont(new Font("Inter", Font.PLAIN, 16));
        
        // Ẩn cột ID
        table.removeColumn(table.getColumnModel().getColumn(0));

        // Header Style
        JTableHeader header = table.getTableHeader();
        header.setPreferredSize(new Dimension(0, 50));
        header.setFont(new Font("Inter", Font.BOLD, 15));
        header.setBackground(Color.WHITE);
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, COLOR_BORDER));

        // Custom Renderer cho các cột Rate (hiển thị màu sắc/sao)
        DefaultTableCellRenderer rateRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                label.setHorizontalAlignment(JLabel.CENTER);
                if (value instanceof Double) {
                    double val = (Double) value;
                    if (val >= 8.0) label.setForeground(new Color(40, 167, 69)); // Xanh lá nếu tốt
                    else if (val >= 5.0) label.setForeground(new Color(255, 140, 0)); // Cam nếu trung bình
                    else label.setForeground(COLOR_DANGER); // Đỏ nếu thấp
                    label.setText(String.format("%.1f", val));
                }
                return label;
            }
        };

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(rateRenderer);
        }
    }

    private void setupEvents() {
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = table.getSelectedRow();
                    if (row == -1) return;
                    int userId = (int) model.getValueAt(row, 0);
                    String username = (String) model.getValueAt(row, 1);
                    
                    JFrame frame = new JFrame("Details: " + username);
                    frame.setSize(1200, 800);
                    frame.setLocationRelativeTo(null);
                    frame.setContentPane(new SubmissionPanel(userId));
                    frame.setVisible(true);
                }
            }
        });
    }

    // --- Utility Methods ---

    private JButton createStyledButton(String text, Color bg, Color fg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Inter", Font.BOLD, 14));
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(bg == Color.WHITE ? COLOR_BORDER : bg),
            BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));
        return btn;
    }

    private void handleAddUser() {
        String username = JOptionPane.showInputDialog(this, "Enter Codeforces Username:", "Add User", JOptionPane.PLAIN_MESSAGE);
        if (username != null && !username.isBlank()) {
            if (userRepository.add(new User(0, username))) loadUsers();
            else JOptionPane.showMessageDialog(this, "User already exists!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleDeleteUser() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a user to delete.");
            return;
        }
        int id = (int) model.getValueAt(row, 0);
        if (JOptionPane.showConfirmDialog(this, "Are you sure?") == JOptionPane.YES_OPTION) {
            userRepository.deleteById(id);
            loadUsers();
        }
    }

    private void handleAnalyze(JButton btn) {
        int row = table.getSelectedRow();
        if (row == -1) return;
        
        int id = (int) model.getValueAt(row, 0);
        String username = (String) model.getValueAt(row, 1);
        btn.setEnabled(false);

        worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                // Giữ nguyên logic xử lý dữ liệu của bạn ở đây...
                List<SubmissionCrawlResult> submissions = crawler.crawlSubmissions(id, username);
                for (SubmissionCrawlResult s : submissions) {
                    if (isCancelled()) break;
                    String srcCode = crawler.crawlSourceCode(s.getContestId(), s.getSubmissionId());
                    if (srcCode != null) {
                        AnalysisResult res = analyzer.analyzeCode(srcCode);
                        submissionRepository.add(new Submission(s.getUserId(), s.getContestId(), s.getSubmissionId(), res));
                        Thread.sleep(1000);
                    }
                }
                userRepository.updateRate(new User(id, username));
                return null;
            }
            @Override
            protected void done() {
                btn.setEnabled(true);
                loadUsers();
                JOptionPane.showMessageDialog(UserPanel.this, "Analysis finished for " + username);
            }
        };
        worker.execute();
    }

    private void loadUsers() {
        model.setRowCount(0);
        List<User> users = userRepository.getAll();
        for (User u : users) {
            model.addRow(new Object[]{u.getId(), u.getUserName(), u.getDataStructureRate(), u.getAlgorithmsRate(), u.getUsingAIRate()});
        }
    }
}