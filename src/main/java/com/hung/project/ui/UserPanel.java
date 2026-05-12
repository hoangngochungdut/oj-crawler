package com.hung.project.ui;

import com.hung.project.models.Submission;

import com.hung.project.models.User;
import com.hung.project.repositories.*;
import com.hung.project.crawlers.*;
import com.hung.project.API.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.util.List;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

public class UserPanel extends JPanel {

    private JTable table;

    private DefaultTableModel model;

    private UserRepository userRepository;
    private SubmissionRepository submissionRepository;

    private CodeforcesCrawler crawler;
    private GeminiAnalyzer analyzer;

    private SwingWorker<Void, Void> worker;
    public UserPanel() {
    	crawler = new CodeforcesCrawler();
        userRepository = new UserRepository();
        submissionRepository = new SubmissionRepository();
        analyzer = new GeminiAnalyzer();
        setLayout(new BorderLayout());

        // TABLE MODEL
        model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {return false;}
        };

        model.addColumn("id");
        model.addColumn("username");
        model.addColumn("Data Structure Rate");
        model.addColumn("Algorithms Rate");
        model.addColumn("Using AI Rate");
        
        // TABLE
        table = new JTable(model);
        table.removeColumn(
        	    table.getColumnModel().getColumn(0)
        	);
        
        table.addMouseListener(
        	    new MouseAdapter() {
        	        @Override
        	        public void mouseClicked(MouseEvent e) {
        	            if (e.getClickCount() == 2) {
        	                int row = table.getSelectedRow();
        	                int userId = (int) model.getValueAt(row, 0);
        	                String username = (String) model.getValueAt(row, 1);
        	                JFrame frame = new JFrame("Submissions - " + username);
        	                frame.setSize(1400, 700);
        	                frame.setLocationRelativeTo(null);
        	                frame.setContentPane(new SubmissionPanel(userId));
        	                frame.setVisible(true);
        	            }
        	        }
        	    }
        	);
	     // FONT TABLE
	     table.setFont(
	         new Font("Arial", Font.PLAIN, 20)
	     );
	
	     // ROW HEIGHT
	     table.setRowHeight(30);
	
	     // HEADER FONT
	     table.getTableHeader().setFont(
	         new Font("Arial", Font.BOLD, 22)
	     );

        JScrollPane scrollPane =
            new JScrollPane(table);

        add(scrollPane, BorderLayout.CENTER);

        // BUTTON PANEL
        JPanel buttonPanel = new JPanel();

        JButton addButton = new JButton("Add User");
        JButton deleteButton = new JButton("Delete User");
        JButton analyzeButton = new JButton("Analyze");
        JButton stopButton = new JButton("Stop Analyze");
        JButton refreshButton = new JButton("Refresh");
        addButton.setFont(new Font("Arial", Font.BOLD, 18));
        deleteButton.setFont(new Font("Arial", Font.BOLD, 18));
        analyzeButton.setFont(new Font("Arial", Font.BOLD, 18));
        stopButton.setFont(new Font("Arial", Font.BOLD, 18));
        refreshButton.setFont(new Font("Arial", Font.BOLD, 18));
        
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(analyzeButton);
        buttonPanel.add(stopButton);
        buttonPanel.add(refreshButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // LOAD DATA
        loadUsers();

        // ADD USER
        addButton.addActionListener(e -> {

            String username = JOptionPane.showInputDialog("Enter username:");

            if (username != null && !username.isBlank()) {

                User user = new User(0, username);
                boolean success = userRepository.add(user);

                	if (!success) {
                	    JOptionPane.showMessageDialog(null, "Username already exists");
                	    return;
                	}

                	loadUsers();
            }
        });

        // DELETE USER
        deleteButton.addActionListener(e -> {

            int selectedRow =
                table.getSelectedRow();

            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(null, "Please select a row");
                return;
                }

            int id = (int) model.getValueAt(selectedRow, 0);
            userRepository.deleteById(id);
            loadUsers();
        });
        
        analyzeButton.addActionListener(e -> {

            int selectedRow = table.getSelectedRow();

            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(null, "Please select a row");
                return;
            }

            int id = (int) model.getValueAt(selectedRow, 0);
            String username = (String) model.getValueAt(selectedRow, 1);

            analyzeButton.setEnabled(false);

            worker = new SwingWorker<>() {

                @Override
                protected Void doInBackground() throws Exception {

                    List<SubmissionCrawlResult> submissions =
                            crawler.crawlSubmissions(
                                    userRepository.findByUserName(username).getId(),
                                    username
                            );

                    for (SubmissionCrawlResult s : submissions) {

                        if (isCancelled()) {
                        	userRepository.updateRate(new User(id, username));
                        	System.out.println("UPDATE RATE");
                        	break;
                        };

                        String srcCode = crawler.crawlSourceCode(
                                s.getContestId(),
                                s.getSubmissionId()
                        );

                        if (srcCode != null) {

                            AnalysisResult res = analyzer.analyzeCode(srcCode);

                            Submission sub = new Submission(
                                    s.getUserId(),
                                    s.getContestId(),
                                    s.getSubmissionId(),
                                    res
                            );

                            submissionRepository.add(sub);

                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException ex) {
                                if (isCancelled()) break;
                                userRepository.updateRate(new User(id, username));
                                System.out.println("UPDATE RATE");
                                Thread.currentThread().interrupt();
                            }
                        }
                    }
                    userRepository.updateRate(new User(id, username));
                    System.out.println("UPDATE RATE");

                    return null;
                }

                @Override
                protected void done() {
                    analyzeButton.setEnabled(true);
                    JOptionPane.showMessageDialog(null, "Analyze completed!");
                }
            };

            worker.execute();
        });
        
        stopButton.addActionListener(e -> {
            if (worker != null && !worker.isDone()) {
                worker.cancel(true);
                System.out.println("Stopped");
            }
        });
        
        refreshButton.addActionListener(e -> {
            loadUsers();
        });
        
        
    }

    private void loadUsers() {

        model.setRowCount(0);

        List<User> users =
            userRepository.getAll();

        for (User user : users) {

            model.addRow(
                new Object[] {
                		user.getId(),
                    user.getUserName(),
                    user.getDataStructureRate(),
                    user.getAlgorithmsRate(),
                    user.getUsingAIRate()
                }
            );
        }
    }
}