package com.yura.holzercolor.utils.formulas;

import com.yura.holzercolor.model.Base;
import com.yura.holzercolor.model.Paint;
import com.yura.holzercolor.model.Palette;
import com.yura.holzercolor.repository.BaseRepository;
import com.yura.holzercolor.repository.PaletteRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class FormulaConverterTest {
    @Mock private PaletteRepository paletteRepository;
    @Mock private BaseRepository baseRepository;

    private RegularFormulaConverter converter;

    @Before
    public void setUp() throws Exception {
        when(paletteRepository.findByName(anyString())).thenAnswer(i -> {
            var palette = new Palette();
            palette.setName(i.getArgument(0));
            return Optional.of(palette);
        });

        when(baseRepository.findByName(matches("[A-C]"))).thenAnswer(i -> {
            var base = new Base();
            base.setName(i.getArgument(0));
            return Optional.of(base);
        });

        converter = new RegularFormulaConverter(paletteRepository, baseRepository);
    }

    @Test
    public void testTikkurila() {
        var result = converter.convertFormula("TIKKURILA Y494\tC\tKS\t14,60\tLS\t15,40\tTT\t91,00\tXT\t1,70", new Paint(), 20)
                .orElseThrow(AssertionError::new);
        assertEquals("SYMPHONY Y494", result.getPalette().getName());
        assertEquals("C", result.getBase().getName());
        assertEquals(0.73, result.getValues().get("ks"), 0.001);
    }

    @Test
    public void testCaparolLong() {
        var result = converter.convertFormula("CAPAROL VIOLA 85\tA\tFT\t120,20\tPT\t16,30\tVT\t6,20\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t", new Paint(), 10)
                .orElseThrow(AssertionError::new);
        assertEquals("CAPAROL VIOLA 85", result.getPalette().getName());
        assertEquals("A", result.getBase().getName());
        assertEquals(12.02, result.getValues().get("ft"), 0.001);
    }

    @Test
    public void testCaparolShort() {
        var result = converter.convertFormula("CAP. VERONA 35\tC\tLS\t12,60\tRT\t11,30\tTT\t130,60\tXT\t84,50\t\t\t\t\t\t\t\t\t\t\t", new Paint(), 10)
                .orElseThrow(AssertionError::new);
        assertEquals("CAPAROL VERONA 35", result.getPalette().getName());
        assertEquals("C", result.getBase().getName());
        assertEquals(13.06, result.getValues().get("tt"), 0.001);
    }
}
