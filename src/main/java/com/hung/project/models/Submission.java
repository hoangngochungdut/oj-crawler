package com.hung.project.models;
import com.hung.project.API.*;
public class Submission {

    private int userId;
    private int contestId;
    private long submissionId;
    
    private double dataStructureRate;
    private String dataStructureAnalyse;
    private double algoRate;
    private String algoAnalyse;
    private double usingAIRate;
    private String usingAIAnalyse;
    public Submission(
        int userId,
        int contestId,
        long submissionId,
        AnalysisResult result
    ) {
        this.userId = userId;
        this.contestId = contestId;
        this.submissionId = submissionId;
        this.dataStructureRate = result.getData_structure_rate();
        this.dataStructureAnalyse = result.getData_structure_analyse();
        this.algoRate = result.getAlgorithm_rate();
        this.algoAnalyse = result.getAlgorithm_analyse();
        this.usingAIRate = result.getUsing_ai_rate();
        this.usingAIAnalyse = result.getUsing_ai_analyse();
      
    }
    
    public Submission(
            int userId,
            int contestId,
            long submissionId,
            double dataStructureRate,
            String dataStructureAnalyse,
            double algoRate,
            String algoAnalyse,
            double usingAIRate,
            String usingAIAnalyse
    ) {
        this.userId = userId;
        this.contestId = contestId;
        this.submissionId = submissionId;

        this.dataStructureRate = dataStructureRate;
        this.dataStructureAnalyse = dataStructureAnalyse;

        this.algoRate = algoRate;
        this.algoAnalyse = algoAnalyse;

        this.usingAIRate = usingAIRate;
        this.usingAIAnalyse = usingAIAnalyse;
    }

    public int getUserId() {
        return userId;
    }
    
    public int getContestId() {
        return contestId;
    }

    public long getSubmissionId() {
        return submissionId;
    }
    
    public double getDataStructureRate() {
        return dataStructureRate;
    }
    
    public String getDataStructureAnalyse() {
        return dataStructureAnalyse;
    }
    
    public double getAlgoRate() {
        return algoRate;
    }
    
    public String getAlgoAnalyse() {
        return algoAnalyse;
    }

    public double getUsingAIRate() {
        return usingAIRate;
    }
    
    public String getUsingAIAnalyse() {
        return usingAIAnalyse;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public void setContestId(int contestId) {
        this.contestId = contestId;
    }

    public void setSubmissionId(long submissionId) {
        this.submissionId = submissionId;
    }
    
    public void setDataStructureRate(double dataStructureRate) {
        this.dataStructureRate = dataStructureRate;
    }

    public void setAlgoRate(double algoRate) {
        this.algoRate = algoRate;
    }
    
    public void setUsingAIRate(double usingAIRate) {
        this.usingAIRate = usingAIRate;
    }

    @Override
    public String toString() {

        return "Submission{" +
                "userId=" + userId +
                ", contestId=" + contestId +
                ", submissionId=" + submissionId +
                '}';
    }
}