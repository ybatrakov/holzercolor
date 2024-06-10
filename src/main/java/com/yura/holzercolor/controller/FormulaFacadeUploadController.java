package com.yura.holzercolor.controller;

import com.yura.holzercolor.model.ColorFormula;
import com.yura.holzercolor.model.ColorFormulaFacade;
import com.yura.holzercolor.repository.*;
import com.yura.holzercolor.utils.formulas.BatchedPersistProcessor;
import com.yura.holzercolor.utils.formulas.FacadeFormulaConverter;
import com.yura.holzercolor.utils.formulas.RegularFormulaConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
@Slf4j
public class FormulaFacadeUploadController extends AbstractFormulaUploadController<ColorFormulaFacade> {
    private final ColorFormulaFacadeRepository formulaRepository;

    public FormulaFacadeUploadController(PaintRepository paintRepository, PaletteRepository paletteRepository,
                                         BaseRepository baseRepository, ColorFormulaFacadeRepository formulaRepository,
                                         @Value("${spring.jpa.properties.hibernate.jdbc.batch_size:1}") int batchSize)
    {
        super(paintRepository, new FacadeFormulaConverter(paletteRepository, baseRepository), new BatchedPersistProcessor<>(batchSize, formulaRepository));
        this.formulaRepository = formulaRepository;
    }

    @PostMapping("/formula_upload_facade")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file, @RequestParam("paintId") Integer paintId, @RequestParam("volume") Double volume) {
        return upload(file, paintId, volume);
    }

    @Override
    protected void cleanupFormulas(Integer paintId) {
        formulaRepository.deleteByPaintId(paintId);
    }
}
