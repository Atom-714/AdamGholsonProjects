package com.example.commercebank.service;

import com.example.commercebank.domain.CertificateInfoDTO;
import com.example.commercebank.domain.Url;
import com.example.commercebank.domain.UrlAnalysisResult;
import com.example.commercebank.repository.CustomerRepository;
import com.example.commercebank.repository.UrlRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.net.ssl.HttpsURLConnection;
import java.net.URL;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Service
public class UrlService {
    private final UrlRepository urlRepository;
    private final CustomerRepository customerRepository;

    public Url create(Url url, Long userId) {
        url.setCustomer(customerRepository.findById(userId).orElse(null));
        return urlRepository.save(url);
    }

    public List<Url> findAll() {
        return urlRepository.findAll();
    }

    public void deleteUrl(Long urlId) {
        urlRepository.deleteById(urlId);
    }

    public Url updateUrl(Url url) {
        System.out.println("Before get");
        Url old = urlRepository.findById(url.getId()).orElse(null);

        if (old == null) {return null;}

        old.setName(url.getName());
        old.setUrl(url.getUrl());
        urlRepository.save(old);

        return old;
    }

    public UrlAnalysisResult analyze(String urlString) {
        try {
            //Connect URL
            URL target = new URL(urlString);
            HttpsURLConnection connection = (HttpsURLConnection) target.openConnection();
            connection.connect();

            //Get results
            int responseCode = connection.getResponseCode();
            String cipherSuite = connection.getCipherSuite();

            //Get Headers
            Map<String, String> headers = new LinkedHashMap<>();
            for (Map.Entry<String, java.util.List<String>> entry : connection.getHeaderFields().entrySet()) {
                if (entry.getKey() != null && entry.getValue() != null && !entry.getValue().isEmpty()) {
                    headers.put(entry.getKey(), entry.getValue().get(0));
                }
            }

            //Get Certificates
            List<CertificateInfoDTO> certificates = new ArrayList<>();
            Certificate[] certs = connection.getServerCertificates();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            for (Certificate cert : certs) {
                if (cert instanceof X509Certificate x509) {
                    CertificateInfoDTO certDTO = new CertificateInfoDTO(
                            x509.getSubjectDN().getName(),
                            x509.getIssuerDN().getName(),
                            sdf.format(x509.getNotBefore()),
                            sdf.format(x509.getNotAfter()),
                            x509.getSerialNumber().toString(),
                            x509.getSigAlgName()
                    );
                    certificates.add(certDTO);
                }
            }


            //close connection and return results
            connection.disconnect();

            return new UrlAnalysisResult(
                    String.valueOf(responseCode),
                    cipherSuite,
                    headers,
                    certificates
            );

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
