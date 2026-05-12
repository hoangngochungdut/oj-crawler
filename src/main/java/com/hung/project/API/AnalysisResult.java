package com.hung.project.API;

public class AnalysisResult {

    private double data_structure_rate;

    private String data_structure_analyse;

    private double algorithm_rate;

    private String algorithm_analyse;

    private double using_ai_rate;

    private String using_ai_analyse;

    public double getData_structure_rate() {
        return data_structure_rate;
    }

    public void setData_structure_rate(
        double data_structure_rate
    ) {
        this.data_structure_rate =
            data_structure_rate;
    }

    public String getData_structure_analyse() {
        return data_structure_analyse;
    }

    public void setData_structure_analyse(
        String data_structure_analyse
    ) {
        this.data_structure_analyse =
            data_structure_analyse;
    }

    public double getAlgorithm_rate() {
        return algorithm_rate;
    }

    public void setAlgorithm_rate(
        double algorithm_rate
    ) {
        this.algorithm_rate =
            algorithm_rate;
    }

    public String getAlgorithm_analyse() {
        return algorithm_analyse;
    }

    public void setAlgorithm_analyse(
        String algorithm_analyse
    ) {
        this.algorithm_analyse =
            algorithm_analyse;
    }

    public double getUsing_ai_rate() {
        return using_ai_rate;
    }

    public void setUsing_ai_rate(
        double using_ai_rate
    ) {
        this.using_ai_rate =
            using_ai_rate;
    }

    public String getUsing_ai_analyse() {
        return using_ai_analyse;
    }

    public void setUsing_ai_analyse(
        String using_ai_analyse
    ) {
        this.using_ai_analyse =
            using_ai_analyse;
    }

    @Override
    public String toString() {

        return "AnalysisResult{" +
            "data_structure_rate=" +
            data_structure_rate +

            ", data_structure_analyse='" +
            data_structure_analyse + '\'' +

            ", algorithm_rate=" +
            algorithm_rate +

            ", algorithm_analyse='" +
            algorithm_analyse + '\'' +

            ", using_ai_rate=" +
            using_ai_rate +

            ", using_ai_analyse='" +
            using_ai_analyse + '\'' +

            '}';
    }
}