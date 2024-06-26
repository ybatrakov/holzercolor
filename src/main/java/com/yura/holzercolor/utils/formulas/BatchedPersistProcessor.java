package com.yura.holzercolor.utils.formulas;

import com.yura.holzercolor.model.Formula;
import org.apache.commons.collections4.ListUtils;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public class BatchedPersistProcessor<FormulaType extends Formula> implements FormulaProcessor<FormulaType> {
    private final int batchSize;
    private final CrudRepository<FormulaType, Integer> formulaRepository;

    public BatchedPersistProcessor(int insertBatchSize, CrudRepository<FormulaType, Integer> formulaRepository) {
        this.batchSize = insertBatchSize;
        this.formulaRepository = formulaRepository;
    }

    @Override
    public void process(List<FormulaType> formulas) {
        for(List<FormulaType> batch : ListUtils.partition(formulas, batchSize)) {
            formulaRepository.saveAll(batch);
        }
    }
}
