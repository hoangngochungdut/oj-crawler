package com.hung.project.ui;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import com.hung.project.models.*;

public class SingleSubmissionPanel extends JPanel {

    public SingleSubmissionPanel(Submission s) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE); // Đặt nền trắng cho panel chính

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(Color.WHITE);
        content.setBorder(new EmptyBorder(10, 20, 10, 20)); // Padding cho toàn bộ nội dung

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

//        // Nút Edit và Share
//        JPanel buttonsPanel = new JPanel();
//        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
//        buttonsPanel.setBackground(Color.WHITE);
//        buttonsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
//
//        JButton editButton = new JButton("Edit");
//        JButton shareButton = new JButton("Share");
        
//        // Style cho nút (để hiện đại hơn)
//        editButton.setFont(textFont);
//        shareButton.setFont(textFont);
//        editButton.setBackground(new Color(240, 240, 240));
//        shareButton.setBackground(new Color(240, 240, 240));
//        editButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
//        shareButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
//
//        buttonsPanel.add(editButton);
//        buttonsPanel.add(Box.createHorizontalStrut(10)); // Khoảng cách giữa các nút
//        buttonsPanel.add(shareButton);

        idPanel.add(idsContainer);
        idPanel.add(Box.createHorizontalGlue()); // Đẩy các nút về phía bên phải
//        idPanel.add(buttonsPanel);
        
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

        // Thêm Glue ở cuối để các phần tử không bị giãn quá mức
        content.add(Box.createVerticalGlue());

        JScrollPane scrollPane = new JScrollPane(content);
        scrollPane.setBorder(null); // Bỏ viền của JScrollPane để trông mượt mà hơn
        add(scrollPane, BorderLayout.CENTER);
    }

    // --- Phương thức hỗ trợ: Tạo Label với Màu và Căn Chỉnh ---
    private JLabel createLabel(String text, Font font, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(color);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    // --- Phương thức hỗ trợ: Tạo TextArea Modern ---
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

    // --- Phương thức quan trọng nhất: Tạo Card Phân Tích ---
    private JPanel createAnalysisCard(String title, double rating, String text, 
                                        Font headerFont, Font textFont, 
                                        Color bgColor, Color borderColor) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(bgColor);
        card.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Tạo viền card: Bo góc mỏng và màu nhẹ (khớp với ảnh)
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

//        JLabel ratingLabel = new JLabel(generateRatingStars(rating));
//        ratingLabel.setFont(new Font("Arial", Font.PLAIN, 18));
//        ratingLabel.setForeground(new Color(255, 193, 7)); // Màu vàng cho sao

        JLabel scoreLabel = new JLabel(" " + rating);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 18));
        scoreLabel.setForeground(Color.DARK_GRAY);

        headerPanel.add(titleLabel);
        headerPanel.add(Box.createHorizontalStrut(10)); // Khoảng cách
//        headerPanel.add(ratingLabel);
        headerPanel.add(scoreLabel);
        headerPanel.add(Box.createHorizontalGlue()); // Đẩy mọi thứ về bên trái

        card.add(headerPanel);
        card.add(Box.createVerticalStrut(15)); // Margin giữa header và text

        // Text Area Panel (để chứa JTextArea và scroll nếu cần)
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BorderLayout());
        textPanel.setBackground(bgColor);
        textPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Giới hạn chiều rộng của card để đảm bảo layout hợp lý (Y-AXIS trong main content panel)
        card.setMaximumSize(new Dimension(800, 300)); // Ví dụ: Giới hạn chiều rộng và chiều cao tối đa

        JTextArea textArea = createTextArea(text, textFont);
        textPanel.add(textArea, BorderLayout.CENTER);

        card.add(textPanel);

        return card;
    }

//    // --- Phương thức hỗ trợ: Tạo chuỗi ký tự Sao Rating ---
//    private String generateRatingStars(int rating) {
//        StringBuilder stars = new StringBuilder();
//        for (int i = 0; i < 5; i++) {
//            if (i < rating) {
//                stars.append("*"); // Sao đầy
//            } else {
//                stars.append(""); // Sao rỗng
//            }
//        }
//        return stars.toString();
//    }
}