package com.translate.dto;

import java.util.List;

import lombok.Data;

@Data
public class TDData {
     private List<List<Detection>> detections;
}