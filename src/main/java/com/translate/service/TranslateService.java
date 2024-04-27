package com.translate.service;

import java.net.URLEncoder;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.translate.config.TranslateConfigProperties;
import com.translate.dto.DetectResponse;
import com.translate.dto.Text;
import com.translate.dto.TranslationRequest;
import com.translate.dto.TranslationResponse;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TranslateService {

    Logger log = LoggerFactory.getLogger(TranslateService.class);

    private static final String TRANSLATE_API_URL = "https://google-translate1.p.rapidapi.com/language/translate/v2";
    private static final String DETECT_API_URL = "https://google-translate1.p.rapidapi.com/language/translate/v2/detect";
    // private static final String API_KEY = "f785ededa0msh2e742ca7199f16cp10f8a5jsn28319ff7a9f4";
    // private static final String API_KEY = "050bbf24bbmsh454a8e72f58614dp1d54cbjsn481e1df5f7aa";
    // private static final String API_KEY = System.getenv("API_KEY");
    private static final String API_HOST = "google-translate1.p.rapidapi.com";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private TranslateConfigProperties configProperties;

    public DetectResponse detect(Text text) {
        try {

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.setAccept(List.of(MediaType.APPLICATION_JSON));
            headers.add("X-RapidAPI-Key", configProperties.api());
            headers.add("X-RapidAPI-Host", API_HOST);

            String formData = String.format("q=%s", URLEncoder.encode(text.getText(),
                    "UTF-8"));

            HttpEntity<String> entity = new HttpEntity<String>(formData, headers);

            ResponseEntity<String> response = restTemplate.exchange(DETECT_API_URL, HttpMethod.POST, entity,
                    String.class);

            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            DetectResponse detectResponse = mapper.readValue(response.getBody(), DetectResponse.class);

            System.out.println(detectResponse);
            return detectResponse;
        } catch (Exception e) {
            log.error("Invalid message", text, e);
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Invalid message", e);
        }
    }

    public String translate(TranslationRequest content) {

        try {

            String lang = content.getSourceLang();
            if (content.getSourceLang().isEmpty() || content.getSourceLang().isBlank()) {
                log.info("Inside Cond. Text --------- " + content.getText());
                log.info("Inside Cond. Source --------- " + content.getSourceLang());
                log.info("Inside Cond. Target --------- " + content.getTargetLang());
                Text text = new Text();
                text.setText(content.getText());
                lang = detect(text).getData().getDetections().get(0).get(0).getLanguage();
            }

            log.info("Outside Text --------- " + content.getText());
            log.info("Outside Source --------- " + content.getSourceLang());
            log.info("Outside Target --------- " + content.getTargetLang());

            System.out.println(lang);

            // If the Input & Output Lang are same
            if (content.getTargetLang().equals(lang))
                return "Looks like you're already a master of this language! Text will remain unchanged.";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.add("X-RapidAPI-Key", configProperties.api());
            headers.add("X-RapidAPI-Host", API_HOST);

            String formData = String.format("q=%s&target=%s&source=%s",
                    URLEncoder.encode(content.getText(), "UTF-8"), content.getTargetLang(),
                    lang);

            HttpEntity<String> entity = new HttpEntity<>(formData, headers);

            ResponseEntity<String> response = restTemplate.exchange(TRANSLATE_API_URL, HttpMethod.POST, entity,
                    String.class);

            ObjectMapper mapper = new ObjectMapper();
            TranslationResponse translationResponse = mapper.readValue(response.getBody(), TranslationResponse.class);
            String translatedText = translationResponse.getData().getTranslations().get(0).getTranslatedText();
            System.out.println(translatedText);

            return translatedText;
        } catch (Exception e) {
            log.error("Failed to translate this text: {}", content.getText(), e);
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,
                    "Failed to translate this text: " + content.getText(),
                    e);
        }
    }
}
