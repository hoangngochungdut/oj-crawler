package com.hung.project.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.hung.project.models.Submission;
import com.hung.project.config.DbConnection; // Giả định đúng package DbConnection của bạn

public class SubmissionRepository {

    // 1. INSERT một submission (Đã sửa lỗi đảo lộn tham số)
    public void add(Submission submission) {
        String sql = "INSERT IGNORE INTO submissions " 
                   + "(user_id, contest_id, submission_id, source_code, " // Chuyển submission_id lên trước source_code
                   + "data_structure_rate, data_structure_analyse, "
                   + "algo_rate, algo_analyse, "
                   + "using_AI_rate, using_AI_analyse) " 
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (
            Connection conn = DbConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, submission.getUserId());
            ps.setInt(2, submission.getContestId());
            ps.setLong(3, submission.getSubmissionId());      // Khớp vị trí số 3: submission_id
            ps.setString(4, submission.getSourceCode());       // Khớp vị trí số 4: source_code
            
            ps.setDouble(5, submission.getDataStructureRate());
            ps.setString(6, submission.getDataStructureAnalyse());
            ps.setDouble(7, submission.getAlgoRate());
            ps.setString(8, submission.getAlgoAnalyse());
            ps.setDouble(9, submission.getUsingAIRate());
            ps.setString(10, submission.getUsingAIAnalyse());

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // 2. INSERT hàng loạt danh sách Submissions bằng BATCH PROCESSING (Tối ưu hiệu năng cao)
    public void add(List<Submission> submissions) {
        if (submissions == null || submissions.isEmpty()) return;

        String sql = "INSERT IGNORE INTO submissions " 
                   + "(user_id, contest_id, submission_id, source_code, "
                   + "data_structure_rate, data_structure_analyse, "
                   + "algo_rate, algo_analyse, "
                   + "using_AI_rate, using_AI_analyse) " 
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (
            Connection conn = DbConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            conn.setAutoCommit(false); // Tắt auto-commit để gom cụm giao dịch

            for (Submission s : submissions) {
                ps.setInt(1, s.getUserId());
                ps.setInt(2, s.getContestId());
                ps.setLong(3, s.getSubmissionId());
                ps.setString(4, s.getSourceCode());
                ps.setDouble(5, s.getDataStructureRate());
                ps.setString(6, s.getDataStructureAnalyse());
                ps.setDouble(7, s.getAlgoRate());
                ps.setString(8, s.getAlgoAnalyse());
                ps.setDouble(9, s.getUsingAIRate());
                ps.setString(10, s.getUsingAIAnalyse());

                ps.addBatch(); // Thêm vào hàng đợi xử lý chung
            }

            ps.executeBatch(); // Thực thi toàn bộ danh sách cùng lúc
            conn.commit();     // Xác nhận lưu dữ liệu
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 3. GET ALL submissions (Đã bổ sung đầy đủ cột trong câu SQL SELECT)
    public List<Submission> getAll() {
        List<Submission> submissions = new ArrayList<>();
        
        String sql = "SELECT user_id, contest_id, submission_id, source_code, "
                   + "data_structure_rate, data_structure_analyse, "
                   + "algo_rate, algo_analyse, "
                   + "using_AI_rate, using_AI_analyse FROM submissions";

        try (
            Connection conn = DbConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ) {
            while (rs.next()) {
                Submission submission = mapResultSetToSubmission(rs);
                submissions.add(submission);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return submissions;
    }

    // 4. GET submissions by user_id (Đã bổ sung cột source_code vào câu lệnh SELECT)
    public List<Submission> getByUserId(int userId) {
        List<Submission> submissions = new ArrayList<>();

        String sql = "SELECT user_id, contest_id, submission_id, source_code, " // Đã sửa: Bổ sung thêm trường source_code
                   + "data_structure_rate, data_structure_analyse, " 
                   + "algo_rate, algo_analyse, " 
                   + "using_AI_rate, using_AI_analyse " 
                   + "FROM submissions " 
                   + "WHERE user_id = ?";

        try (
            Connection conn = DbConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setInt(1, userId);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Submission submission = mapResultSetToSubmission(rs);
                    submissions.add(submission);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return submissions;
    }

    // Phương thức bổ trợ tách biệt: Giúp chuyển đổi dòng dữ liệu ResultSet thành đối tượng mẫu (Clean Code)
    private Submission mapResultSetToSubmission(ResultSet rs) throws Exception {
        int userId = rs.getInt("user_id");
        int contestId = rs.getInt("contest_id");
        long submissionId = rs.getLong("submission_id");
        String sourceCode = rs.getString("source_code");
        double dataStructureRate = rs.getDouble("data_structure_rate");
        String dataStructureAnalyse = rs.getString("data_structure_analyse");
        double algoRate = rs.getDouble("algo_rate");
        String algoAnalyse = rs.getString("algo_analyse");
        double usingAIRate = rs.getDouble("using_AI_rate");
        String usingAIAnalyse = rs.getString("using_AI_analyse");

        return new Submission(
            userId, contestId, submissionId, sourceCode,
            dataStructureRate, dataStructureAnalyse,
            algoRate, algoAnalyse,
            usingAIRate, usingAIAnalyse
        );
    }
}