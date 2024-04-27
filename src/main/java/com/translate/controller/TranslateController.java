package com.translate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.translate.dto.DetectResponse;
import com.translate.dto.Text;
import com.translate.dto.TranslationRequest;
import com.translate.service.TranslateService;

@RestController

// 2nd Approach
// @CrossOrigin(origins = "http://localhost:3000")
public class TranslateController {

    @Autowired
    TranslateService translateService;

    @PostMapping("/translate")
    public ResponseEntity<String> translate(
            @RequestBody(required = true) TranslationRequest content) {
        return ResponseEntity.ok().body(translateService.translate(content));
    }

    @PostMapping("/detect")
    public ResponseEntity<DetectResponse> detect(@RequestBody Text text) {
        return ResponseEntity.ok().body(translateService.detect(text));
    }

}
