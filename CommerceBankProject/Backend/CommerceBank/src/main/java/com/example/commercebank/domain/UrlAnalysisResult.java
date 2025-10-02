package com.example.commercebank.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UrlAnalysisResult {

    private String responseCode;
    private String cipherSuite;
    private Map<String, String> headers;
    private List<CertificateInfoDTO> certificates;


}