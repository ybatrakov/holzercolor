package com.yura.holzercolor.controller;

import com.yura.holzercolor.model.*;
import com.yura.holzercolor.repository.*;
import com.yura.holzercolor.utils.formulas.BatchedPersistProcessor;
import com.yura.holzercolor.utils.formulas.RegularFormulaConverter;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
@Slf4j
public class FormulaUploadController extends AbstractFormulaUploadController<ColorFormula> {
    private final ColorFormulaRepository formulaRepository;

    public FormulaUploadController(PaintRepository paintRepository, PaletteRepository paletteRepository,
                                   BaseRepository baseRepository, ColorFormulaRepository formulaRepository,
                                   @Value("${spring.jpa.properties.hibernate.jdbc.batch_size:1}") int batchSize)
    {
        super(paintRepository, new RegularFormulaConverter(paletteRepository, baseRepository), new BatchedPersistProcessor<>(batchSize, formulaRepository));
        this.formulaRepository = formulaRepository;
    }

    @PostMapping("/formula_upload")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file, @RequestParam("paintId") Integer paintId, @RequestParam("volume") Double volume) {
        return upload(file, paintId, volume);
    }

    @Override
    protected void cleanupFormulas(Integer paintId) {
        formulaRepository.deleteByPaintId(paintId);
    }
}
