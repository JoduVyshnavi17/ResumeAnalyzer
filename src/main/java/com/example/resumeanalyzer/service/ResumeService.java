package com.example.resumeanalyzer.service;

import com.example.resumeanalyzer.model.AnalysisResult;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ResumeService {

    private final Tika tika = new Tika();

    public AnalysisResult analyzeResume(MultipartFile file, String jobDescription)
            throws IOException, TikaException, SAXException {

        String resumeText = tika.parseToString(file.getInputStream()).toLowerCase();

        List<String> jobKeywords = Arrays.stream(jobDescription.toLowerCase().split("\\W+"))
                .filter(word -> word.length() > 3)
                .distinct()
                .collect(Collectors.toList());

        int matched = 0;
        List<String> missing = new ArrayList<>();

        for (String keyword : jobKeywords) {
            if (resumeText.contains(keyword)) matched++;
            else missing.add(keyword);
        }

        double score = jobKeywords.isEmpty() ? 0 : ((double) matched / jobKeywords.size()) * 100;
        return new AnalysisResult(score, matched, jobKeywords.size(), missing);
    }
}
