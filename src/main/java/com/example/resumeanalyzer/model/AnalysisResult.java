package com.example.resumeanalyzer.model;

import java.util.List;

public class AnalysisResult {
    private double score;
    private int matchedKeywords;
    private int totalKeywords;
    private List<String> missingKeywords;

    public AnalysisResult(double score, int matchedKeywords, int totalKeywords, List<String> missingKeywords) {
        this.score = score;
        this.matchedKeywords = matchedKeywords;
        this.totalKeywords = totalKeywords;
        this.missingKeywords = missingKeywords;
    }

    public double getScore() { return score; }
    public int getMatchedKeywords() { return matchedKeywords; }
    public int getTotalKeywords() { return totalKeywords; }
    public List<String> getMissingKeywords() { return missingKeywords; }
}
