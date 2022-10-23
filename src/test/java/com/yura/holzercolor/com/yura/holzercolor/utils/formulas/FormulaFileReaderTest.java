package com.yura.holzercolor.com.yura.holzercolor.utils.formulas;

import com.yura.holzercolor.utils.formulas.Formula;
import com.yura.holzercolor.utils.formulas.FormulaFileListener;
import com.yura.holzercolor.utils.formulas.FormulaFileReader;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.mockito.stubbing.OngoingStubbing;

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
    public void testRegular() throws IOException {
        BufferedReader fileReader = getReader("NOVA F010\tAA\tMT\t8,00\tTT\t7,27",
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t",
                "NOVA F011\tAA\tMT\t9,45\tRT\t2,18\tTT\t3,64\t\t");
        reader = new FormulaFileReader(fileReader, 10, listener);
        reader.read();

        verify(listener).onFormulaInfoResolved(Formula.Type.REGULAR);
        verify(listener).onFormulas(any());

        Assert.assertEquals(2, captured.size());

        Formula f10 = captured.get(0);
        Assert.assertEquals("A", f10.base);
        Assert.assertEquals("NOVA F010", f10.palette);
        Assert.assertEquals(8.0, f10.componentPrices.get("mt"), 0.0);
        Assert.assertEquals(7.27, f10.componentPrices.get("tt"), 0.0);

        Formula f11 = captured.get(1);
        Assert.assertEquals("A", f11.base);
        Assert.assertEquals("NOVA F011", f11.palette);
        Assert.assertEquals(9.45, f11.componentPrices.get("mt"), 0.0);
        Assert.assertEquals(2.18, f11.componentPrices.get("rt"), 0.0);
        Assert.assertEquals(3.64, f11.componentPrices.get("tt"), 0.0);
    }

    @Test
    public void testFacade() throws IOException {
        BufferedReader fileReader = getReader(
                "Elements 001\tB\tMC.TT\t0,8\tWA.JE\t151,3\tWA.JO\t30,9\t\t\t\t\t\t",
                "Elements 002\tB\tWA.JE\t64,3\tWA.JO\t17,1\t\t\t\t\t\t\t\t");

        reader = new FormulaFileReader(fileReader, 10, listener);
        reader.read();

        verify(listener).onFormulaInfoResolved(Formula.Type.FACADE);
        verify(listener).onFormulas(any());

        Assert.assertEquals(2, captured.size());
        Formula e1 = captured.get(0);
        Assert.assertEquals("001", e1.palette);
        Assert.assertEquals("B", e1.base);
        Assert.assertEquals(0.8, e1.componentPrices.get("tt"), 0.0);
        Assert.assertEquals(151.3, e1.componentPrices.get("je"), 0.0);
        Assert.assertEquals(30.9, e1.componentPrices.get("jo"), 0.0);

        Formula e2 = captured.get(1);
        Assert.assertEquals("002", e2.palette);
        Assert.assertEquals("B", e2.base);
        Assert.assertEquals(64.3, e2.componentPrices.get("je"), 0.0);
        Assert.assertEquals(17.1, e2.componentPrices.get("jo"), 0.0);
    }

    private static BufferedReader getReader(String... lines) throws IOException {
        BufferedReader reader = Mockito.mock(BufferedReader.class);
        OngoingStubbing<Boolean> ready = when(reader.ready());
        for(String ignored : lines) {
            ready = ready.thenReturn(true);
        }
        ready.thenReturn(false);

        OngoingStubbing<String> readLine = when(reader.readLine());
        for(String line : lines) {
            readLine = readLine.thenReturn(line);
        }
        return reader;
    }
}
