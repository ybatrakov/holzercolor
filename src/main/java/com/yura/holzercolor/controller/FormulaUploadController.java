package com.yura.holzercolor.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.yura.holzercolor.model.*;
import com.yura.holzercolor.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class FormulaUploadController {
    private final static Logger log = LoggerFactory.getLogger(FormulaUploadController.class);

    private final static Pattern HOLZER_PATTERN = Pattern.compile("^\\d{3}$");
    private final static Pattern HOLZER_PATTERN2 = Pattern.compile("^(?)ELEMENTS (\\d{3})$", Pattern.CASE_INSENSITIVE);
    private final static Pattern NOVA_PATTERN = Pattern.compile("^(NOVA |)[A-Z]\\d{3}$", Pattern.CASE_INSENSITIVE);
    private final static Pattern NCS_PATTERN1 = Pattern.compile("^NCS ([A-Z])([^ ]+)$");

    private final static List<String> IGNORED_PALETTES = Arrays.asList("Caparol", "Dulux", "Tikkurila");

    @Autowired
    PaletteRepository paletteRepository;

    @Autowired
    BaseRepository baseRepository;

    @Autowired
    PaintRepository paintRepository;

    @Autowired
    ColorFormulaRepository colorFormulaRepository;

    @Autowired
    ColorFormulaFacadeRepository colorFormulaFacadeRepository;

    private static class FileContext {
        int lineNum;
        Formula.Type formulaType;
    }

    @PostMapping("/formula_upload")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file, @RequestParam("paintId") Integer paintId, @RequestParam("volume") Double volume) {
        if(file.isEmpty()) {
            return new ResponseEntity<>("File is empty", HttpStatus.BAD_REQUEST);
        }

        if(volume == null || volume == 0) {
            return new ResponseEntity<>("Invalid volume specified: " + volume, HttpStatus.BAD_REQUEST);
        }
        
        Optional<Paint> paint = paintRepository.findById(paintId);
        if(!paint.isPresent()) {
            return new ResponseEntity<>("No paint for id " + paintId, HttpStatus.BAD_REQUEST);
        }
        
        FileContext ctx = new FileContext();
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            while(reader.ready()) {
                String line = reader.readLine();
                ctx.lineNum++;
                processFormula(paint.get(), volume, line, ctx);
            }
        }
        catch(IOException e) {
            return new ResponseEntity<>("Error occured", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        return new ResponseEntity<>("Uploaded " + ctx.lineNum + " lines", HttpStatus.OK);
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
            if(name.toUpperCase().startsWith("NOVA ")) {
                return name.toUpperCase();
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

    private static final Set<String> FACADE_ONLY_FIELDS = getFacadeOnlyFields();

    private static Set<String> getFacadeOnlyFields() {
        Set<String> ret = new HashSet<>(ColorFormulaFacade.FIELDS);
        ret.removeAll(ColorFormula.FIELDS);
        return ret;
    }

    private static Formula.Type getFormulaType(Set<String> fields) {
        // If 'fields' contain any of facade fields, it is facade formula
        HashSet<String> tmp = new HashSet<>(ColorFormulaFacade.FIELDS);
        tmp.removeAll(ColorFormula.FIELDS);
        tmp.retainAll(fields);
        return tmp.isEmpty() ? Formula.Type.REGULAR : Formula.Type.FACADE;
    }

    void updateFormula(Formula formula,
                       Paint paint, Palette palette, Base base, HashMap<String, Double> strmap)
    {
        formula.setPaint(paint);
        formula.setPalette(palette);
        formula.setBase(base);

        for(Map.Entry<String, Double> e : strmap.entrySet()) {
            formula.setValue(e.getKey(), e.getValue());
        }
    }

    private void processFormula(Paint paint, double volume, String line, FileContext ctx) {
        String[] tokens = line.split("\\t");

        for(String ignored : IGNORED_PALETTES) {
            if(tokens[0].startsWith(ignored)) {
                return;
            }
        }

        String paletteName = normalizePaletteName(tokens[0]);
        if(paletteName == null) {
            log.warn("Line #{}, Unsupported palette {}", ctx.lineNum, tokens[0]);
            return;
        }

        Palette palette = paletteRepository.findByName(paletteName);
        if(palette == null) {
            log.warn("Line #{}, Palette {} wasn't found in the database", ctx.lineNum, paletteName);
            return;
        }

        String baseStr = tokens[1].length() == 1 ?
                tokens[1] : tokens[1].substring(0,1);
        Base base = baseRepository.findByName(baseStr);
        if(base == null) {
            log.warn("Line #{}, Base {} wasn't found in the database", ctx.lineNum, baseStr);
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
            col = col.toLowerCase();

            double price = Double.parseDouble(tokens[i+1].replace(",", ".")); // DecimalFormat might be better

            params.put(col, price / volume);
        }

        if(ctx.formulaType == null) {
            // Trying to guess if it is Facade/Interior color formula
            ctx.formulaType = getFormulaType(params.keySet());
        }
        if(ctx.formulaType == Formula.Type.FACADE) {
            ColorFormulaFacade formula = new ColorFormulaFacade();
            updateFormula(formula, paint, palette, base, params);
            colorFormulaFacadeRepository.save(formula);
        }
        else {
            ColorFormula formula = new ColorFormula();
            updateFormula(formula, paint, palette, base, params);
            colorFormulaRepository.save(formula);
        }
    }
}
