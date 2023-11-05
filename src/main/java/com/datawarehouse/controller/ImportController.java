package com.datawarehouse.controller;

import com.datawarehouse.service.ImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImportController {

    @Autowired
    private ImportService importService;

    @GetMapping("/import-all")
    public void importAllData(){
        importService.importTheater();
    }

}
