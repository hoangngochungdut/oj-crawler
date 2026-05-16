package com.hung.project.ui;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import com.hung.project.models.*;

public class SingleSubmissionPanel extends JPanel {

    public SingleSubmissionPanel(Submission s) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE); // Đặt nền trắng cho panel chính

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(Color.WHITE);
        content.setBorder(new EmptyBorder(20, 20, 20, 20)); // Padding cho toàn bộ nội dung

        // --- Hệ thống Font Chữ Modern ---
        Font contestFont = new Font("Arial", Font.BOLD, 22); // Cỡ chữ lớn hơn cho ID
        Font sectionHeaderFont = new Font("Arial", Font.BOLD, 18);
        Font textFont = new Font("Arial", Font.PLAIN, 16);

        // --- 1. Contest ID và Submission ID ---
        JPanel idPanel = new JPanel();
        idPanel.setLayout(new BoxLayout(idPanel, BoxLayout.X_AXIS));
        idPanel.setBackground(Color.WHITE);
        idPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Đóng gói ID để dễ dàng quản lý margin
        JPanel idsContainer = new JPanel();
        idsContainer.setLayout(new BoxLayout(idsContainer, BoxLayout.Y_AXIS));
        idsContainer.setBackground(Color.WHITE);
        idsContainer.setAlignmentX(Component.LEFT_ALIGNMENT);
        idsContainer.setBorder(new EmptyBorder(10, 0, 20, 0)); // Margin bottom cho khối ID

        idsContainer.add(createLabel("Contest ID: " + s.getContestId(), contestFont, Color.DARK_GRAY));
        idsContainer.add(Box.createVerticalStrut(5));
        idsContainer.add(createLabel("Submission ID: " + s.getSubmissionId(), textFont, Color.GRAY));

        idPanel.add(idsContainer);
        idPanel.add(Box.createHorizontalGlue()); // Đẩy các nút về phía bên phải
        
        content.add(idPanel);

        // --- 2. Các Card Phân Tích (Analysis Cards) ---
        Color cardBackgroundColor = new Color(245, 248, 250); // Màu nền nhẹ cho card
        Color borderColor = new Color(200, 215, 230); // Màu viền mỏng

        content.add(createAnalysisCard("Data Structure", s.getDataStructureRate(), s.getDataStructureAnalyse(), 
                sectionHeaderFont, textFont, cardBackgroundColor, borderColor));
        content.add(Box.createVerticalStrut(20)); // Margin giữa các card

        content.add(createAnalysisCard("Algorithm Performance", s.getAlgoRate(), s.getAlgoAnalyse(), 
                sectionHeaderFont, textFont, cardBackgroundColor, borderColor));
        content.add(Box.createVerticalStrut(20));

        content.add(createAnalysisCard("AI Assistance", s.getUsingAIRate(), s.getUsingAIAnalyse(), 
                sectionHeaderFont, textFont, cardBackgroundColor, borderColor));
        content.add(Box.createVerticalStrut(25)); // Khoảng cách tới vùng chứa Code

        // --- 3. Vùng chứa Source Code Mới ---
        content.add(createLabel("Source Code", sectionHeaderFont, Color.BLACK));
        content.add(Box.createVerticalStrut(10));
        content.add(createSourceCodeCard(s.getSourceCode())); // Giả định class Submission có phương thức getSourceCode()

        // Thêm Glue ở cuối để các phần tử không bị giãn quá mức
        content.add(Box.createVerticalGlue());

        JScrollPane mainScrollPane = new JScrollPane(content);
        mainScrollPane.setBorder(null); // Bỏ viền của JScrollPane chính để trông mượt mà hơn
        mainScrollPane.getVerticalScrollBar().setUnitIncrement(16); // Cuộn mượt hơn
        add(mainScrollPane, BorderLayout.CENTER);
    }

    // --- Phương thức hỗ trợ: Tạo Label với Màu và Căn Chỉnh ---
    private JLabel createLabel(String text, Font font, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(color);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    // --- Phương thức hỗ trợ: Tạo TextArea Modern cho phần text nhận xét ---
    private JTextArea createTextArea(String text, Font font) {
        JTextArea area = new JTextArea(text);
        area.setFont(font);
        area.setForeground(Color.DARK_GRAY);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setEditable(false);
        area.setOpaque(false); // Làm cho nền trong suốt
        area.setAlignmentX(Component.LEFT_ALIGNMENT);
        area.setMargin(new Insets(5, 0, 5, 0)); // Padding bên trong text area
        return area;
    }

    // --- Phương thức: Tạo Card Phân Tích ---
    private JPanel createAnalysisCard(String title, double rating, String text, 
                                        Font headerFont, Font textFont, 
                                        Color bgColor, Color borderColor) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(bgColor);
        card.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Tạo viền card: Bo góc mỏng và màu nhẹ
        Border compoundBorder = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borderColor, 1, true), // Viền mỏng, bo góc
                new EmptyBorder(15, 15, 15, 15) // Padding bên trong card
        );
        card.setBorder(compoundBorder);

        // Header Panel: Title + Rating
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.X_AXIS));
        headerPanel.setBackground(bgColor);
        headerPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(headerFont);
        titleLabel.setForeground(Color.BLACK);

        JLabel scoreLabel = new JLabel(" " + rating);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 18));
        scoreLabel.setForeground(Color.DARK_GRAY);

        headerPanel.add(titleLabel);
        headerPanel.add(Box.createHorizontalStrut(10)); // Khoảng cách
        headerPanel.add(scoreLabel);
        headerPanel.add(Box.createHorizontalGlue()); // Đẩy mọi thứ về bên trái

        card.add(headerPanel);
        card.add(Box.createVerticalStrut(15)); // Margin giữa header và text

        // Text Area Panel
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BorderLayout());
        textPanel.setBackground(bgColor);
        textPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        card.setMaximumSize(new Dimension(800, 300));

        JTextArea textArea = createTextArea(text, textFont);
        textPanel.add(textArea, BorderLayout.CENTER);

        card.add(textPanel);

        return card;
    }

    // --- Phương thức MỚI: Tạo vùng hiển thị Source Code chuyên nghiệp ---
    private JComponent createSourceCodeCard(String sourceCode) {
        JTextArea codeArea = new JTextArea(sourceCode != null ? sourceCode : "// No source code available");
        
        // Sử dụng font chữ Monospaced chuyên dụng cho Code
        codeArea.setFont(new Font("Consolas", Font.PLAIN, 15)); 
        codeArea.setForeground(new Color(212, 212, 212)); // Chữ màu xám trắng nhẹ phong cách Dark Mode
        codeArea.setBackground(new Color(30, 30, 30));    // Màu nền tối kiểu VS Code (Dark Theme)
        
        codeArea.setEditable(false);
        codeArea.setTabSize(4); // Định dạng khoảng cách Tab chuẩn thụt lề code
        codeArea.setMargin(new Insets(15, 15, 15, 15)); // Khoảng đệm cho text bên trong editor

        // Đặt TextArea vào cấu trúc ScrollPane riêng biệt để cuộn độc lập khi code quá dài
        JScrollPane codeScrollPane = new JScrollPane(codeArea);
        codeScrollPane.setBorder(BorderFactory.createLineBorder(new Color(60, 60, 60), 1, true)); // Viền tối bo góc nhẹ
        codeScrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Cố định kích thước khung chứa Code (Rộng 800px giống các card trên, cao 350px để cuộn nội dung)
        codeScrollPane.setMaximumSize(new Dimension(800, 350));
        codeScrollPane.setPreferredSize(new Dimension(800, 350));

        return codeScrollPane;
    }
}