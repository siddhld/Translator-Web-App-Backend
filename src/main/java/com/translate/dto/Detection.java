package com.translate.dto;

import lombok.Data;

@Data
public class Detection {
     private double confidence;
     private boolean isReliable;
     private String language;
}
