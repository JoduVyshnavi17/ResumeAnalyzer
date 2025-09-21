package com.example.resumeanalyzer.controller;

import com.example.resumeanalyzer.model.AnalysisResult;
import com.example.resumeanalyzer.service.ResumeService;
import org.apache.tika.exception.TikaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class ResumeController {

    @Autowired
    private ResumeService resumeService;

    // Serve index.html
    @GetMapping("/")
    public String index() {
        return "index.html";
    }

    @PostMapping("/api/resume/upload")
    @ResponseBody
    public ResponseEntity<?> uploadResume(@RequestParam("file") MultipartFile file,
                                          @RequestParam("jobDescription") String jobDescription) {
        try {
            AnalysisResult result = resumeService.analyzeResume(file, jobDescription);
            return ResponseEntity.ok(result);
        } catch (IOException | TikaException | SAXException e) {
            e.printStackTrace();
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to analyze resume: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}
