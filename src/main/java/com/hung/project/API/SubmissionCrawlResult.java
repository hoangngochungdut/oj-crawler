package com.hung.project.API;

public class SubmissionCrawlResult {
    private int userId;
    private int contestId;
    private long submissionId;

    public SubmissionCrawlResult(int userId, int contestId, long submissionId) {
        this.userId = userId;
        this.contestId = contestId;
        this.submissionId = submissionId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getContestId() {
        return contestId;
    }

    public void setContestId(int contestId) {
        this.contestId = contestId;
    }

    public long getSubmissionId() {
        return submissionId;
    }

    public void setSubmissionId(long submissionId) {
        this.submissionId = submissionId;
    }

    @Override
    public String toString() {
        return "SubmissionCrawlResult{" +
                "userId=" + userId +
                ", contestId=" + contestId +
                ", submissionId=" + submissionId +
                '}';
    }
}
