package com.datawarehouse.controller;

import com.datawarehouse.service.ExtractDataFromSourceService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class ExtractDataController {

    @Autowired
    ExtractDataFromSourceService extractDataFromSource;

    @GetMapping("/extract-data-from-source")
    public void ExtractData() throws IOException {
        extractDataFromSource.extractData();
    }
}
