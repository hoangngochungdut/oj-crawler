package com.hung.project.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.hung.project.config.DbConnection;
import com.hung.project.models.*;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {
	private SubmissionRepository submissionRepo;
	public UserRepository () {
		submissionRepo = new SubmissionRepository();
	}
    // INSERT user
	public boolean add(User user) {

	    if (existsByUserName(user.getUserName())) {
	        return false;
	    }

	    String sql =
	        "INSERT INTO users(user_name) " +
	        "VALUES (?)";

	    try (
	        Connection conn =
	            DbConnection.getConnection();

	        PreparedStatement ps =
	            conn.prepareStatement(sql);
	    ) {

	        ps.setString(
	            1,
	            user.getUserName()
	        );

	        ps.executeUpdate();

	        return true;

	    } catch (Exception e) {

	        e.printStackTrace();
	    }

	    return false;
	}

    // GET ALL users
    public List<User> getAll() {

        List<User> users =
            new ArrayList<>();

        String sql =
            "SELECT id, user_name, data_structure_rate, algo_rate, using_AI_rate " +
            "FROM users";

        try (
            Connection conn =
                DbConnection.getConnection();

            PreparedStatement ps = conn.prepareStatement(sql);
//        		System.out.println("BEFORE EXECUTING");
            ResultSet rs = ps.executeQuery();
//        		System.out.println("AFTER EXECUTING");
        ) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String userName = rs.getString("user_name");
                double data_structure_rate = rs.getDouble("data_structure_rate");
                double algoRate = rs.getDouble("algo_rate");
                double usingAIRate = rs.getDouble("using_AI_rate");
                User user =
                    new User(
                        id,
                        userName,
                        data_structure_rate,
                        algoRate,
                        usingAIRate
                    );

                users.add(user);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return users;
    }

    // FIND user by id
    public User findById(int id) {

        String sql =
            "SELECT id, user_name " +
            "FROM users " +
            "WHERE id = ?";

        try (
            Connection conn =
                DbConnection.getConnection();

            PreparedStatement ps =
                conn.prepareStatement(sql);
        ) {

            ps.setInt(1, id);

            ResultSet rs =
                ps.executeQuery();

            if (rs.next()) {

                String userName =
                    rs.getString("user_name");

                return new User(
                    id,
                    userName
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // FIND user by username
    public User findByUserName(
        String userName
    ) {

        String sql =
            "SELECT id, user_name " +
            "FROM users " +
            "WHERE user_name = ?";

        try (
            Connection conn =
                DbConnection.getConnection();

            PreparedStatement ps =
                conn.prepareStatement(sql);
        ) {

            ps.setString(1, userName);

            ResultSet rs =
                ps.executeQuery();

            if (rs.next()) {

                int id =
                    rs.getInt("id");

                return new User(
                    id,
                    userName
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // DELETE user
    public void deleteById(int id) {

        String sql =
            "DELETE FROM users " +
            "WHERE id = ?";

        try (
            Connection conn =
                DbConnection.getConnection();

            PreparedStatement ps =
                conn.prepareStatement(sql);
        ) {

            ps.setInt(1, id);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void update(User user) {

        String sql =
            "UPDATE users SET " +
            "user_name = ?, " +
            "data_structure_rate = ?, " +
            "algo_rate = ?, " +
            "using_AI_rate = ? " +
            "WHERE id = ?";

        try (
            Connection conn = DbConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
        ) {
            ps.setString(1, user.getUserName());
            ps.setDouble(2, user.getDataStructureRate());
            ps.setDouble(3, user.getAlgorithmsRate());
            ps.setDouble(4, user.getUsingAIRate());
            ps.setInt(5, user.getId());

            int rows = ps.executeUpdate();

            if (rows == 0) {
                System.out.println("No user found with id = " + user.getId());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void updateRate(User user) {
    	List<Submission> submissions = submissionRepo.getByUserId(user.getId());
    	double dataStructureRateSum = 0;
    	double algoRateSum = 0;
    	double usingRateSum = 0;
    	for(Submission s : submissions) {
    		dataStructureRateSum += s.getDataStructureRate();
    		algoRateSum += s.getAlgoRate();
    		usingRateSum += s.getUsingAIRate();
    	}
    	double dataStructureRate = dataStructureRateSum / submissions.size();
    	double algoRate = algoRateSum / submissions.size();
    	double usingRate = usingRateSum / submissions.size();
    	
    	user.setDataStructureRate(dataStructureRate);
    	user.setAlgorithmsRate(algoRate);
    	user.setUsingAIRate(usingRate);
    	
    	update(user);
    }
    
    public boolean existsByUserName(
    	    String userName
    	) {

    	    String sql =
    	        "SELECT COUNT(*) " +
    	        "FROM users " +
    	        "WHERE user_name = ?";

    	    try (
    	        Connection conn =
    	            DbConnection.getConnection();

    	        PreparedStatement ps =
    	            conn.prepareStatement(sql);
    	    ) {

    	        ps.setString(1, userName);

    	        ResultSet rs =
    	            ps.executeQuery();

    	        if (rs.next()) {
    	        	return rs.getInt(1) > 0;
    	        }

    	    } catch (Exception e) {
    	        e.printStackTrace();
    	    }

    	    return false;
    	}
}
