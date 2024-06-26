package com.yura.holzercolor.controller;

import com.yura.holzercolor.model.*;
import com.yura.holzercolor.repository.*;
import com.yura.holzercolor.utils.formulas.FormulaConverter;
import com.yura.holzercolor.utils.formulas.FormulaProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

@Slf4j
public abstract class AbstractFormulaUploadController<ColorFormulaType extends Formula> {
    private final PaintRepository paintRepository;
    private final FormulaConverter<ColorFormulaType> formulaConverter;
    private final FormulaProcessor<ColorFormulaType> formulaProcessor;

    public AbstractFormulaUploadController(PaintRepository paintRepository, FormulaConverter<ColorFormulaType> formulaConverter, FormulaProcessor<ColorFormulaType> formulaProcessor) {
        this.paintRepository = paintRepository;
        this.formulaConverter = formulaConverter;
        this.formulaProcessor = formulaProcessor;
    }

    public ResponseEntity<String> upload(MultipartFile file, Integer paintId, Double volume) {
        if(file.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File is empty");
        }

        if(volume == null || volume == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid volume specified: " + volume);
        }

        Paint paint = paintRepository.findById(paintId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "No paint for id " + paintId));

        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            List<ColorFormulaType> formulas = formulaConverter.convert(paint, volume, br.lines());
            if(!formulas.isEmpty()) {
                log.info("Destroying formulas for paint {}", paint.getName());
                cleanupFormulas(paintId);

                formulaProcessor.process(formulas);
            }
            log.info("Uploaded {} formulas for paint {}", formulas.size(), paint.getName());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.error("Exception when processing formula file", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    protected abstract void cleanupFormulas(Integer paintId);
}
