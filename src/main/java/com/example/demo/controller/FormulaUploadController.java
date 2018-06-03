package com.example.demo.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.HashMap;
import java.util.Optional;

import com.example.demo.model.*;
import com.example.demo.repository.*;

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

    private final static Pattern HOLZER_PATTERN = Pattern.compile("^\\d{3}$");
    private final static Pattern HOLZER_PATTERN2 = Pattern.compile("^ELEMENTS (\\d{3})$");
    private final static Pattern NOVA_PATTERN = Pattern.compile("^(NOVA |)[A-Z]\\d{3}$");
    private final static Pattern NCS_PATTERN1 = Pattern.compile("^NCS ([A-Z])([^ ]+)$");
    
    @Autowired
    PaletteRepository paletteRepository;

    @Autowired
    BaseRepository baseRepository;

    @Autowired
    PaintRepository paintRepository;

    @Autowired
    ColorFormulaFacadeRepository colorFormulaFacadeRepository;

    @PostMapping("/formula_upload")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file, @RequestParam("paintId") Integer paintId, @RequestParam("volume") Integer volume) {
        if(file.isEmpty()) {
            return new ResponseEntity<String>("File is empty", HttpStatus.BAD_REQUEST);
        }

        if(volume == null || volume == 0) {
            return new ResponseEntity<String>("Invalid volume specified: " + volume, HttpStatus.BAD_REQUEST);
        }
        
        Optional<Paint> paint = paintRepository.findById(paintId);
        if(!paint.isPresent()) {
            return new ResponseEntity<String>("No paint for id " + paintId, HttpStatus.BAD_REQUEST);
        }
        
        int counter = 0;

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
            while(reader.ready()) {
                String line = reader.readLine();
                processFormula(paint.get(), volume, line);
                counter++;
            }
        }
        catch(IOException e) {
            return new ResponseEntity<String>("Error occured", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        return new ResponseEntity<String>("Uploaded " + counter + " lines", HttpStatus.OK);
    }

    private static String normalizePaletteName(String name) {
        name = name.replaceAll("[ ]{2,}", " ");
        if(HOLZER_PATTERN.matcher(name).matches()) {
            return name;
        }
        {
            // ELEMENTS 123 -> 123
            Matcher m = HOLZER_PATTERN2.matcher(name);
            if(m.matches()) {
                return m.group(1);
            }
        }
        if(NOVA_PATTERN.matcher(name).matches()) {
            if(name.startsWith("NOVA ")) {
                return name;
            }
            return "NOVA " + name;
        }
        if(name.startsWith("NCS ")) {
            Matcher m = NCS_PATTERN1.matcher(name);
            if(m.matches()) {
                // NCS S0300-N -> NCS S 0300-N
                return "NCS " + m.group(1) + " " + m.group(2);
            }
            return name;
        }

        if(name.startsWith("RAL ")) {
            return name;
        }
        return null;
    }

    private void processFormula(Paint paint, int volume, String line) {
        String[] tokens = line.split("\\t");

        String paletteName = normalizePaletteName(tokens[0]);
        if(paletteName == null) {
            log.warn("Unsupported palette {}", tokens[0]);
            return;
        }

        Palette palette = paletteRepository.findByName(paletteName);
        if(palette == null) {
            log.warn("Palette {} wasn't found in the database", paletteName);
            return;
        }

        Base base = baseRepository.findByName(tokens[1]);
        if(base == null) {
            log.warn("Base {} wasn't found in the database", tokens[1]);
            return;
        }

        HashMap<String, Double> params = new HashMap<>();
        for(int i = 2; i + 1 < tokens.length; i += 2) {
            if(tokens[i].length() == 0) {
                break;
            }

            // Normalize colorant names: WA.JO -> JO
            String[] col_toks = tokens[i].split("\\.");
            String col = ( col_toks.length == 2 ? col_toks[1] : tokens[i] );

            double price = Double.valueOf(tokens[i+1].replace(",", ".")); // DecimalFormat might be better

            params.put(col, price / volume);
        }

        ColorFormulaFacade formula = new ColorFormulaFacade();
        formula.setPaint(paint);
        formula.setPalette(palette);
        formula.setBase(base);
        formula.setColorants(params);
        colorFormulaFacadeRepository.save(formula);
    }
}
