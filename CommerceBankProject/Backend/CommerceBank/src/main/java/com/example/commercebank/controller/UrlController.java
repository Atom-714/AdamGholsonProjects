package com.example.commercebank.controller;

import com.example.commercebank.domain.Url;
import com.example.commercebank.domain.UrlAnalysisResult;
import com.example.commercebank.domain.UrlRequest;
import com.example.commercebank.service.UrlService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;
import java.net.URL;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class UrlController {

    private final UrlService urlService;

    // Must be logged in to work
    @CrossOrigin
    @PostMapping("/url")
    public ResponseEntity<?> save(@RequestBody Url url){
        if (CustomerController.loggedInUserId == -1){
            System.out.println("Not logged in");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(urlService.create(url, CustomerController.loggedInUserId), HttpStatus.CREATED);
    }

    @CrossOrigin
    @GetMapping("/urls")
    public ResponseEntity<?> getAllUrls(){
        return new ResponseEntity<>(urlService.findAll(), HttpStatus.OK);
    }

    // id is passed through the url
    // If we wanted to delete the url with id 4, the mapping would be
    // /deleteUrl/4
    // Must be logged in to work
    @CrossOrigin
    @DeleteMapping("/deleteUrl/{id}")
    public ResponseEntity<?> deleteUrl(@PathVariable Long id){
        if (CustomerController.loggedInUserId == -1){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        urlService.deleteUrl(id);

        return new ResponseEntity<>(HttpStatus.OK);

    }

    // Input MUST provide the Id, name, and url of the Url object
    // Must be logged in to work
    @CrossOrigin
    @PostMapping("/updateUrl")
    private ResponseEntity<?> updateUrl(@RequestBody Url url){
        if (CustomerController.loggedInUserId == -1){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Url updatedUrl = urlService.updateUrl(url);

        if (updatedUrl == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(updatedUrl, HttpStatus.OK);

    }

    //Analyze the inputed URL
    @CrossOrigin
    @PostMapping("/analyze")
    public ResponseEntity<?> analyzeUrl(@RequestBody UrlRequest url) {
        try {
            System.out.println("Analyze Try " + url.getUrl());
            UrlAnalysisResult result = urlService.analyze(url.getUrl());
            System.out.println("Analyze Post Result");
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            System.out.println("Analyze Catch");
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
