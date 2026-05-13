package com.hung.project.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import com.hung.project.models.Submission;
import com.hung.project.repositories.SubmissionRepository;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;


public class SubmissionPanel extends JPanel {

    private JTable table;
    private DefaultTableModel model;
    private SubmissionRepository submissionRepository;

    // Định nghĩa bảng màu đồng bộ với SingleSubmissionPanel
    private final Color COLOR_BG = new Color(242, 245, 249);
    private final Color COLOR_CARD = Color.WHITE;
    private final Color COLOR_ACCENT = new Color(0, 123, 255);
    private final Color COLOR_TEXT_MAIN = new Color(40, 44, 52);
    private final Color COLOR_BORDER = new Color(230, 233, 237);

    public SubmissionPanel(int userId) {
        submissionRepository = new SubmissionRepository();

        // Thiết lập layout và màu nền tổng thể
        setLayout(new BorderLayout());
        setBackground(COLOR_BG);
        setBorder(new EmptyBorder(30, 30, 30, 30));

        // 1. Tiêu đề bảng
        JLabel titleLabel = new JLabel("My Submissions");
        titleLabel.setFont(new Font("Inter", Font.BOLD, 24));
        titleLabel.setForeground(COLOR_TEXT_MAIN);
        titleLabel.setBorder(new EmptyBorder(0, 0, 20, 0));
        add(titleLabel, BorderLayout.NORTH);

        // 2. Khởi tạo Model (Không cho sửa ô)
        model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        model.addColumn("Contest ID");
        model.addColumn("Submission ID");
        model.addColumn("DS Rate");
        model.addColumn("DS Analyze");
        model.addColumn("Algo Rate");
        model.addColumn("Algo Analyze");
        model.addColumn("AI Rate");
        model.addColumn("AI Analyze");

        // 3. Cấu hình JTable hiện đại
        table = new JTable(model);
        setupModernTable();

        // Sự kiện click chuột
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    openDetailFrame();
                }
            }
        });

        // 4. Bọc bảng trong JScrollPane và làm sạch Border
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(COLOR_BORDER, 1));
        scrollPane.getViewport().setBackground(COLOR_CARD); // Nền trắng cho vùng trống của bảng
        
        add(scrollPane, BorderLayout.CENTER);

        loadSubmissions(userId);
    }

    private void setupModernTable() {
        table.setRowHeight(45); // Tăng chiều cao dòng để dễ nhìn
        table.setShowGrid(false); // Ẩn lưới thô
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setSelectionBackground(new Color(232, 240, 254)); // Màu xanh nhẹ khi chọn dòng
        table.setSelectionForeground(COLOR_TEXT_MAIN);
        table.setFont(new Font("Inter", Font.PLAIN, 15));
        table.setBackground(COLOR_CARD);

        // Custom Header
        JTableHeader header = table.getTableHeader();
        header.setPreferredSize(new Dimension(0, 50));
        header.setBackground(COLOR_CARD);
        header.setForeground(COLOR_TEXT_MAIN);
        header.setFont(new Font("Inter", Font.BOLD, 15));
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, COLOR_BORDER));

        // Renderer để căn giữa hoặc tạo Padding cho dữ liệu
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setBorder(noFocusBorder); // Bỏ viền xanh khi focus ô
                if (!isSelected) {
                    // Hiệu ứng dòng chẵn lẻ (Zebra stripes) nhẹ
                    c.setBackground(row % 2 == 0 ? COLOR_CARD : new Color(250, 251, 253));
                }
                return c;
            }
        };
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Ẩn bớt các cột analyze nếu muốn bảng gọn hơn (người dùng sẽ xem ở detail)
        // table.removeColumn(table.getColumnModel().getColumn(7)); 
    }

    private void openDetailFrame() {
        int row = table.getSelectedRow();
        if (row == -1) return;

        // Lấy dữ liệu từ model
        int contestId = ((Number) model.getValueAt(row, 0)).intValue();
        long submissionId = ((Number) model.getValueAt(row, 1)).longValue();
        double dsRate = ((Number) model.getValueAt(row, 2)).doubleValue();
        String dsAnal = (String) model.getValueAt(row, 3);
        double algoRate = ((Number) model.getValueAt(row, 4)).doubleValue();
        String algoAnal = (String) model.getValueAt(row, 5);
        double AIRate = ((Number) model.getValueAt(row, 6)).doubleValue();
        String AIAnal = (String) model.getValueAt(row, 7);

        Submission sub = new Submission(0, contestId, submissionId, dsRate, dsAnal, algoRate, algoAnal, AIRate, AIAnal);

        // Frame chi tiết đồng bộ
        JFrame frame = new JFrame("Details - Submission #" + submissionId);
        frame.setSize(1000, 750); // Kích thước hợp lý hơn
        frame.setLocationRelativeTo(null);
        frame.setContentPane(new SingleSubmissionPanel(sub));
        frame.setVisible(true);
    }

    private void loadSubmissions(int userId) {
        model.setRowCount(0);
        List<Submission> submissions = submissionRepository.getByUserId(userId);
        for (Submission s : submissions) {
            model.addRow(new Object[] {
                s.getContestId(),
                s.getSubmissionId(),
                s.getDataStructureRate(),
                s.getDataStructureAnalyse(),
                s.getAlgoRate(),
                s.getAlgoAnalyse(),
                s.getUsingAIRate(),
                s.getUsingAIAnalyse()
            });
        }
    }
}