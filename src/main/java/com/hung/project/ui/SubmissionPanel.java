package com.hung.project.ui;

import java.awt.BorderLayout;
import java.awt.Font;

import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import javax.swing.table.DefaultTableModel;

import com.hung.project.models.Submission;
import com.hung.project.repositories.SubmissionRepository;

public class SubmissionPanel extends JPanel {

    private JTable table;

    private DefaultTableModel model;

    private SubmissionRepository submissionRepository;

    public SubmissionPanel(int userId) {

        submissionRepository = new SubmissionRepository();

        setLayout(new BorderLayout());

        model = new DefaultTableModel() {
        	        @Override
        	        public boolean isCellEditable(int row, int column) {
        	            return false;
        	        }
        	    };

        model.addColumn("Contest id");
        model.addColumn("Submission id");
        model.addColumn("Data structure rate");
        model.addColumn("Data structure analyse");
        model.addColumn("Algorithm rate");
        model.addColumn("Algorithm analyse");
        model.addColumn("Using AI rate");
        model.addColumn("Using AI analyse");
        table =
            new JTable(model);

        // FONT
        table.setFont(
            new Font("Arial", Font.PLAIN, 18)
        );

        table.setRowHeight(28);

        table.getTableHeader().setFont(
            new Font("Arial", Font.BOLD, 20)
        );

        JScrollPane scrollPane =
            new JScrollPane(table);

        add(scrollPane, BorderLayout.CENTER);

        loadSubmissions(userId);
    }

    private void loadSubmissions(int userId) {

        model.setRowCount(0);

        List<Submission> submissions =
            submissionRepository
                .getByUserId(userId);

        for (Submission s : submissions) {

            model.addRow(
                new Object[] {
                    s.getContestId(),
                    s.getSubmissionId(),
                    s.getDataStructureRate(),
                    s.getDataStructureAnalyse(),
                    s.getAlgoRate(),
                    s.getAlgoAnalyse(),
                    s.getUsingAIRate(),
                    s.getUsingAIAnalyse()
                }
            );
        }
    }
}
