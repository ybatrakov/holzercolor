package com.example.demo.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

import com.example.demo.model.Palette;
import com.example.demo.repository.PaletteRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
@RequestMapping("/api")
public class FormulaUploadController {
    private final static Logger log = LoggerFactory.getLogger(FormulaUploadController.class);
    
    @Autowired
    PaletteRepository paletteRepository;

    @PostMapping("/formula_upload")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
        if(file.isEmpty()) {
            return new ResponseEntity<String>("File is empty", HttpStatus.BAD_REQUEST);
        }

        int counter = 0;

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
            while(reader.ready()) {
                String line = reader.readLine();
                processFormula(line);
                counter++;
            }
        }
        catch(IOException e) {
            return new ResponseEntity<String>("Error occured", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        return new ResponseEntity<String>("Uploaded " + counter + " lines", HttpStatus.OK);
    }


    private void processFormula(String line) {
        String[] tokens = line.split("\\t");
        Palette p = paletteRepository.findByName(tokens[0]);
        if(p != null) {
            log.info("Palette id {}", p.getId());
        }
        else {
            log.info("Unable to find palette by name {}", tokens[0]);
        }
    }
}
