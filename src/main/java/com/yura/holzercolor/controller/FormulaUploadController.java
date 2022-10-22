package com.yura.holzercolor.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.*;

import com.yura.holzercolor.model.*;
import com.yura.holzercolor.repository.*;
import com.yura.holzercolor.utils.formulas.FormulaFileListener;
import com.yura.holzercolor.utils.formulas.FormulaFileReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class FormulaUploadController {
    private final static Logger log = LoggerFactory.getLogger(FormulaUploadController.class);

    @Value("${spring.jpa.properties.hibernate.jdbc.batch_size:1}")
    private int INSERT_BATCH_SIZE;

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

        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            FormulaFileReader reader = new FormulaFileReader(br, INSERT_BATCH_SIZE, new FileListener(paint.get(), volume));
            reader.read();

            return new ResponseEntity<>("Uploaded " + reader.getLine() + " formulas", HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("Error occured", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    private class FileListener implements FormulaFileListener {
        final Paint paint;
        final double volume;
        Formula.Type type;

        private FileListener(Paint paint, double volume) {
            this.paint = paint;
            this.volume = volume;
        }

        @Override
        public void onFormulaInfoResolved(com.yura.holzercolor.utils.formulas.Formula.Type formulaType) {
            log.info("Detected formula type {}", formulaType);
            if(formulaType == com.yura.holzercolor.utils.formulas.Formula.Type.REGULAR) {
                colorFormulaRepository.deleteByPaintId(paint.getId());
                type = Formula.Type.REGULAR;
            } else {
                colorFormulaFacadeRepository.deleteByPaintId(paint.getId());
                type = Formula.Type.FACADE;
            }
        }

        @Override
        public void onFormulas(List<com.yura.holzercolor.utils.formulas.Formula> batch) {
            if(type == Formula.Type.REGULAR) {
                colorFormulaRepository.saveAll(convertFormula(ColorFormula.class, batch, paint, volume));
            }
            else {
                colorFormulaFacadeRepository.saveAll(convertFormula(ColorFormulaFacade.class, batch, paint, volume));
            }
        }
    }

    private static <FormulaType extends Formula>
    FormulaType createFormula(Class<FormulaType> clazz) {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private <FormulaType extends Formula>
    List<FormulaType> convertFormula(Class<FormulaType> clazz, List<com.yura.holzercolor.utils.formulas.Formula> batch, Paint paint, double volume) {
        List<FormulaType> ret = new ArrayList<>(batch.size());
        for(com.yura.holzercolor.utils.formulas.Formula strFormula : batch) {
            Palette palette = paletteRepository.findByName(strFormula.palette);
            if(palette == null) {
                log.warn("Line #{}, Palette {} wasn't found in the database", strFormula.fileLine, strFormula.palette);
                continue;
            }

            Base base = baseRepository.findByName(strFormula.base);
            if(base == null) {
                log.warn("Line #{}, Base {} wasn't found in the database", strFormula.fileLine, strFormula.palette);
                continue;
            }

            FormulaType formula = createFormula(clazz);
            formula.setPaint(paint);
            formula.setPalette(palette);
            formula.setBase(base);
            strFormula.componentPrices.forEach((colorant, price) ->
                    formula.setValue(colorant, price / volume));
            ret.add(formula);
        }

        return ret;
    }
}
