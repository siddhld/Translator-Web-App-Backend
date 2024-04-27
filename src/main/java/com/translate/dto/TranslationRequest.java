package com.translate.dto;

import org.springframework.beans.factory.annotation.Value;

import lombok.Data;

@Data
public class TranslationRequest {
  private String text;
  @Value("en")
  private String sourceLang;
  @Value("hi")
  private String targetLang;
}
