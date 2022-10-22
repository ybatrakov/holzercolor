package com.yura.holzercolor.com.yura.holzercolor.utils.formulas;

import com.yura.holzercolor.utils.formulas.Formula;
import com.yura.holzercolor.utils.formulas.FormulaFileListener;
import com.yura.holzercolor.utils.formulas.FormulaFileReader;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FormulaFileReaderTest {
    @Mock
    private FormulaFileListener listener;
    private List<Formula> captured;
    private FormulaFileReader reader;

    @Before
    public void setUp() {
        captured = new ArrayList<>();
        MockitoAnnotations.initMocks(this);
        doAnswer(inv -> {
            captured.addAll(inv.getArgument(0));
            return null;}).when(listener).onFormulas(any());
    }

    @Test
    public void testFormulas() throws IOException {
        BufferedReader fileReader = Mockito.mock(BufferedReader.class);
        when(fileReader.ready()).thenReturn(true, true, true, false);
        when(fileReader.readLine()).thenReturn("NOVA F010\tAA\tMT\t8,00\tTT\t7,27",
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t",
                "NOVA F011\tAA\tMT\t9,45\tRT\t2,18\tTT\t3,64\t\t");
        reader = new FormulaFileReader(fileReader, 10, listener);
        reader.read();

        verify(listener).onFormulaInfoResolved(Formula.Type.REGULAR);
        verify(listener).onFormulas(any());

        Assert.assertEquals(2, captured.size());
        Assert.assertEquals("A", captured.get(0).base);
    }
}
