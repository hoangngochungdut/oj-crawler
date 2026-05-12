package com.hung.project.repositories;

import com.hung.project.config.DbConnection;
import com.hung.project.models.Submission;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

public class SubmissionRepository {

    // INSERT submission
    public void add(Submission submission) {

        String sql =
            "INSERT IGNORE INTO submissions " +
            "(user_id, contest_id, submission_id, "
            + "data_structure_rate, data_structure_analyse, "
            + "algo_rate, algo_analyse, "
            + "using_AI_rate, using_AI_analyse) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (
            Connection conn =
                DbConnection.getConnection();

            PreparedStatement ps =
                conn.prepareStatement(sql);
        ) {

            ps.setInt(
                1,
                submission.getUserId()
            );
            
            ps.setInt(
                2,
                submission.getContestId()
                );

            ps.setLong(
                3,
                submission.getSubmissionId()
            );
            
            ps.setDouble(4, submission.getDataStructureRate());
            ps.setString(5, submission.getDataStructureAnalyse());
            ps.setDouble(6, submission.getAlgoRate());
            ps.setString(7, submission.getAlgoAnalyse());
            ps.setDouble(8, submission.getUsingAIRate());
            ps.setString(9, submission.getUsingAIAnalyse());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void add(List<Submission> submissions) {
    	for (Submission s : submissions) {
    		this.add(s);
    	}
    }

    // GET ALL submissions
    public List<Submission> getAll() {

        List<Submission> submissions =
            new ArrayList<>();

        String sql =
            "SELECT user_id, submission_id " +
            "FROM submissions";

        try (
            Connection conn =
                DbConnection.getConnection();

            PreparedStatement ps =
                conn.prepareStatement(sql);

            ResultSet rs =
                ps.executeQuery();
        ) {

            while (rs.next()) {
                int userId = rs.getInt("user_id");
                int contestId = rs.getInt("contest_id");
                long submissionId = rs.getLong("submission_id");
                double dataStructureRate = rs.getDouble("data_structure_rate");
                String dataStructureAnalyse = rs.getString("data_structure_analyse");
                double algoRate = rs.getDouble("algo_rate");
                String algoAnalyse = rs.getString("algo_analyse");
                double usingAIRate = rs.getDouble("using_AI_rate");
                String usingAIAnalyse = rs.getString("using_AI_analyse");
                
                Submission submission =
                    new Submission(
                        userId,
                        contestId,
                        submissionId,
                        dataStructureRate,
                        dataStructureAnalyse,
                        algoRate, 
                        algoAnalyse,
                        usingAIRate,
                        usingAIAnalyse
                    );

                submissions.add(submission);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return submissions;
    }

    // GET submissions by user_id
    public List<Submission> getByUserId(int userId) {
//    	System.out.println("HERE");

        List<Submission> submissions =
            new ArrayList<>();

        String sql =
        	    "SELECT user_id, contest_id, submission_id, " +
        	    "data_structure_rate, data_structure_analyse, " +
        	    "algo_rate, algo_analyse, " +
        	    "using_AI_rate, using_AI_analyse " +
        	    "FROM submissions " +
        	    "WHERE user_id = ?";

        try (
            Connection conn =
                DbConnection.getConnection();

            PreparedStatement ps =
                conn.prepareStatement(sql);
        ) {

            ps.setInt(1, userId);
            
//            System.out.println("BEFORE EXECUTING QUERY");
            ResultSet rs = ps.executeQuery();
//            System.out.println("executed query");

            while (rs.next()) {
            		
            	int contestId = rs.getInt("contest_id");
                long submissionId = rs.getLong("submission_id");
                double dataStructureRate = rs.getDouble("data_structure_rate");
                String dataStructureAnalyse = rs.getString("data_structure_analyse");
                double algoRate = rs.getDouble("algo_rate");
                String algoAnalyse = rs.getString("algo_analyse");
                double usingAIRate = rs.getDouble("using_AI_rate");
                String usingAIAnalyse = rs.getString("using_AI_analyse");

                Submission submission =
                        new Submission(
                            userId,
                            contestId,
                            submissionId,
                            dataStructureRate,
                            dataStructureAnalyse,
                            algoRate, 
                            algoAnalyse,
                            usingAIRate,
                            usingAIAnalyse
                        );
                submissions.add(submission);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return submissions;
    }
    
    
}