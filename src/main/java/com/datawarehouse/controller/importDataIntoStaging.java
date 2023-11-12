package com.datawarehouse.controller;

import com.datawarehouse.service.LoadDataIntoStagingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class importDataIntoStaging {

    @Autowired
    LoadDataIntoStagingService loadDataIntoStagingService;

    @GetMapping("/importDataIntoStaging")
    public void importDataIntoStaging() throws IOException {
        loadDataIntoStagingService.importDataIntoStaging();
    }
}
