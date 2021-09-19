package com.yura.holzercolor.utils.formulas;

import com.yura.holzercolor.model.ColorFormula;
import com.yura.holzercolor.model.ColorFormulaFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormulaFileReader {
    private final static Logger log = LoggerFactory.getLogger(FormulaFileReader.class);

    private final static Pattern HOLZER_PATTERN = Pattern.compile("^\\d{3}$");
    private final static Pattern HOLZER_PATTERN2 = Pattern.compile("^(?)ELEMENTS (\\d{3})$", Pattern.CASE_INSENSITIVE);
    private final static Pattern NOVA_PATTERN = Pattern.compile("^(NOVA |)[A-Z]\\d{3}$", Pattern.CASE_INSENSITIVE);
    private final static Pattern NCS_PATTERN1 = Pattern.compile("^NCS ([A-Z])([^ ]+)$");
    private final static List<String> IGNORED_PALETTES = Arrays.asList("Caparol", "Dulux", "Tikkurila");

    private final Reader reader;
    private final List<Formula> formulaBuffer = new ArrayList<>();
    private final int batchSize;
    private final FormulaFileListener listener;
    private Formula.Type formulaType;

    private int lineNo = 1;

    public FormulaFileReader(Reader reader, int batchSize, FormulaFileListener listener) {
        Objects.requireNonNull(reader, "Invalid reader supplied");
        Objects.requireNonNull(reader, "Invalid listener supplied");

        if(batchSize < 1) {
            batchSize = 1;
        }

        this.reader = reader;
        this.batchSize = batchSize;
        this.listener = listener;
    }

    public void read() throws IOException {
        try(BufferedReader r = new BufferedReader(reader)) {
            while(r.ready()) {
                String line = r.readLine();
                processFormula(line, lineNo++);
            }
            sendBuffer(true);
        }
    }

    private void processFormula(String line, int lineNum) {
        String[] tokens = line.split("\\t");

        for(String ignored : IGNORED_PALETTES) {
            if(tokens[0].startsWith(ignored)) {
                return;
            }
        }

        String paletteName = normalizePaletteName(tokens[0]);
        if(paletteName == null) {
            log.warn("Line #{}, Unsupported palette {}", lineNum, tokens[0]);
            return;
        }

        String baseStr = normalizeBaseName(tokens[1]);

        HashMap<String, Double> params = new HashMap<>();
        for(int i = 2; i + 1 < tokens.length; i += 2) {
            if(tokens[i].length() == 0) {
                break;
            }

            String col = normalizeColorant(tokens[i]);
            double price = Double.parseDouble(tokens[i+1].replace(",", ".")); // DecimalFormat might be better
            params.put(col, price);
        }

        Formula formula = new Formula();
        formula.fileLine = lineNum;
        formula.palette = paletteName;
        formula.base = baseStr;
        formula.componentPrices = params;
        formulaBuffer.add(formula);

        if(formulaType == null) {
            // Trying to guess if it is Facade/Interior color formula. If OK, notify listener, otherwise buffer until OK.
            formulaType = getFormulaType(params.keySet());
            if(formulaType != null) {
                listener.onFormulaInfoResolved(formulaType);
                sendBuffer(false);
            } else {
                return;
            }
        }

        if(formulaBuffer.size() == batchSize) {
            sendBuffer(true);
        }
    }

    private void sendBuffer(boolean force) {
        if(formulaBuffer.isEmpty()) {
            return;
        }

        int size = formulaBuffer.size();
        if(size < batchSize && !force) {
            return;
        }

        if(size <= batchSize) {
            sendBufferImpl(formulaBuffer);
        } else {
        // size > batchSize: rare (or impossible) case when too much unidentified formulas has been buffered
        for (int i = 0; i <= size; i += batchSize) {
            int toIdx = Math.min(i + batchSize, size);
            List<Formula> sub = formulaBuffer.subList(i, toIdx);
            sendBufferImpl(sub);
        }
    }
        formulaBuffer.clear();
    }

    private void sendBufferImpl(List<Formula> formulas) {
        listener.onFormulas(formulas);
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

    private static String normalizeBaseName(String name) {
        // AA -> A
        return name.length() > 1 ?
                name.substring(0,1) :
                name;
    }

    private static String normalizeColorant(String name) {
        // Normalize colorant names: WA.JO -> jo
        String[] col_toks = name.split("\\.");
        String col = col_toks.length == 2 ? col_toks[1] : name;
        return col.toLowerCase();
    }

    private static Formula.Type getFormulaType(Set<String> fields) {
        // If 'fields' contain any of facade fields, it is facade formula
        HashSet<String> tmp = new HashSet<>(ColorFormulaFacade.FIELDS);
        tmp.removeAll(ColorFormula.FIELDS);
        tmp.retainAll(fields);
        return tmp.isEmpty() ? Formula.Type.REGULAR : Formula.Type.FACADE;
    }

    public int getLine() {
        return lineNo;
    }
}
