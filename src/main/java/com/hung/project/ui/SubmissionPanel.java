package com.hung.project.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import com.hung.project.models.*;
import com.hung.project.repositories.SubmissionRepository;

public class SubmissionPanel extends JPanel {

    private JTable table;
    private DefaultTableModel model;
    private SubmissionRepository submissionRepository;

    // Định nghĩa bảng màu đồng bộ với SingleSubmissionPanel
    private final Color COLOR_BG = new Color(242, 245, 249);
    private final Color COLOR_CARD = Color.WHITE;
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
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(COLOR_TEXT_MAIN);
        titleLabel.setBorder(new EmptyBorder(0, 0, 20, 0));
        add(titleLabel, BorderLayout.NORTH);

        // 2. Khởi tạo Model (Không cho sửa ô trực tiếp)
        model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Thứ tự các cột được định nghĩa rõ ràng trong Model
        model.addColumn("Contest ID");      // Index 0
        model.addColumn("Submission ID");   // Index 1
        model.addColumn("Source Code");     // Index 2
        model.addColumn("DS Rate");         // Index 3
        model.addColumn("DS Analyze");      // Index 4
        model.addColumn("Algo Rate");       // Index 5
        model.addColumn("Algo Analyze");    // Index 6
        model.addColumn("AI Rate");         // Index 7
        model.addColumn("AI Analyze");      // Index 8

        // 3. Cấu hình JTable hiện đại
        table = new JTable(model);
        setupModernTable();

        // Sự kiện click chuột (Double click để xem chi tiết)
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

        // Tải dữ liệu từ database/repository lên bảng
        loadSubmissions(userId);
    }

    private void setupModernTable() {
        table.setRowHeight(45); // Tăng chiều cao dòng để dễ nhìn
        table.setShowGrid(false); // Ẩn lưới thô cứng cũ
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setSelectionBackground(new Color(232, 240, 254)); // Màu xanh nhẹ khi chọn dòng
        table.setSelectionForeground(COLOR_TEXT_MAIN);
        table.setFont(new Font("Arial", Font.PLAIN, 15));
        table.setBackground(COLOR_CARD);

        // Tùy chỉnh thanh tiêu đề (Header) của bảng
        JTableHeader header = table.getTableHeader();
        header.setPreferredSize(new Dimension(0, 50));
        header.setBackground(COLOR_CARD);
        header.setForeground(COLOR_TEXT_MAIN);
        header.setFont(new Font("Arial", Font.BOLD, 15));
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, COLOR_BORDER));

        // Renderer để tạo hiệu ứng Zebra stripes (dòng chẵn lẻ) và căn giữa chữ
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setBorder(noFocusBorder); // Bỏ viền nét đứt khi click chọn ô
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? COLOR_CARD : new Color(250, 251, 253));
                }
                return c;
            }
        };
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // --- MẸO UI: Ẩn bớt các cột chứa văn bản dài (Source Code, Các cột Analyze) ---
        // Việc ẩn ở View giúp bảng gọn gàng hơn, tuy nhiên dữ liệu ngầm trong Model vẫn được giữ nguyên để lấy ra.
        // Thực hiện ẩn từ vị trí index lớn nhất lùi về để không bị sai lệch index dịch chuyển.
        table.removeColumn(table.getColumnModel().getColumn(8)); // Ẩn cột AI Analyze
        table.removeColumn(table.getColumnModel().getColumn(6)); // Ẩn cột Algo Analyze
        table.removeColumn(table.getColumnModel().getColumn(4)); // Ẩn cột DS Analyze
        table.removeColumn(table.getColumnModel().getColumn(2)); // Ẩn cột Source Code
    }

    private void openDetailFrame() {
        int viewRow = table.getSelectedRow();
        if (viewRow == -1) return;

        // CỰC KỲ QUAN TRỌNG: Chuyển đổi index từ View sang Model vì chúng ta đã ẩn bớt một số cột hiển thị
        int row = table.convertRowIndexToModel(viewRow);

        // Truy xuất chính xác tuyệt đối theo cấu trúc cột của Model
        int contestId = ((Number) model.getValueAt(row, 0)).intValue();
        long submissionId = ((Number) model.getValueAt(row, 1)).longValue();
        String sourceCode = (String) model.getValueAt(row, 2);
        
        double dsRate = ((Number) model.getValueAt(row, 3)).doubleValue();
        String dsAnal = (String) model.getValueAt(row, 4);
        
        double algoRate = ((Number) model.getValueAt(row, 5)).doubleValue();
        String algoAnal = (String) model.getValueAt(row, 6);
        
        double AIRate = ((Number) model.getValueAt(row, 7)).doubleValue();
        String AIAnal = (String) model.getValueAt(row, 8);

        // Khởi tạo đối tượng Submission đồng bộ với constructor chứa Source Code
        Submission sub = new Submission(0, contestId, submissionId, sourceCode, dsRate, dsAnal, algoRate, algoAnal, AIRate, AIAnal);

        // Khởi tạo Frame chi tiết
        JFrame frame = new JFrame("Details - Submission #" + submissionId);
        frame.setSize(1000, 750);
        frame.setLocationRelativeTo(null);
        frame.setContentPane(new SingleSubmissionPanel(sub));
        frame.setVisible(true);
    }

    private void loadSubmissions(int userId) {
        model.setRowCount(0); // Xóa dữ liệu cũ trước khi nạp mới
        List<Submission> submissions = submissionRepository.getByUserId(userId);
        
        for (Submission s : submissions) {
            // Thêm đầy đủ 9 phần tử tương ứng hoàn toàn với 9 cột đã định nghĩa ở Model
            model.addRow(new Object[] {
                s.getContestId(),           // Index 0
                s.getSubmissionId(),        // Index 1
                s.getSourceCode(),          // Index 2 -> ĐÃ BỔ SUNG KHỚP VỚI MODEL
                s.getDataStructureRate(),   // Index 3
                s.getDataStructureAnalyse(),// Index 4
                s.getAlgoRate(),            // Index 5
                s.getAlgoAnalyse(),         // Index 6
                s.getUsingAIRate(),         // Index 7
                s.getUsingAIAnalyse()       // Index 8
            });
        }
    }
}