package com.yura.holzercolor.utils.formulas;

import com.yura.holzercolor.model.Base;
import com.yura.holzercolor.model.Formula;
import com.yura.holzercolor.model.Paint;
import com.yura.holzercolor.model.Palette;
import com.yura.holzercolor.repository.BaseRepository;
import com.yura.holzercolor.repository.PaletteRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public abstract class AbstractFormulaConverter<FormulaType extends Formula> implements FormulaConverter<FormulaType> {
    private final static Pattern HOLZER_PATTERN2 = Pattern.compile("^ELE(MENTS|) (\\d{3})$", Pattern.CASE_INSENSITIVE);
    private final static Pattern NOVA_PATTERN = Pattern.compile("^(NOVA |)[A-Z]\\d{3}$", Pattern.CASE_INSENSITIVE);
    private final static Pattern NCS_PATTERN1 = Pattern.compile("^NCS ([A-Z])([^ ]+)$");
    private final static List<String> IGNORED_PALETTES = Arrays.asList("Caparol", "Dulux", "Tikkurila");

    private final PaletteRepository paletteRepository;
    private final BaseRepository baseRepository;

    protected AbstractFormulaConverter(PaletteRepository paletteRepository, BaseRepository baseRepository) {
        this.paletteRepository = paletteRepository;
        this.baseRepository = baseRepository;
    }

    @Override
    public List<FormulaType> convert(Paint paint, double volume, Stream<String> lines) {
        return lines
                .filter(StringUtils::isNotBlank)
                .map(line -> convertFormula(line, paint, volume))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    Optional<FormulaType> convertFormula(String line, Paint paint, double volume) {
        String[] tokens = line.split("\\t");

        for(String ignored : IGNORED_PALETTES) {
            if(StringUtils.startsWithIgnoreCase(tokens[0], ignored)) {
                return Optional.empty();
            }
        }

        String paletteName = normalizePaletteName(tokens[0]);
        Optional<Palette> palette = paletteRepository.findByName(paletteName);
        if(!palette.isPresent()) {
            log.warn("Palette '" + paletteName + "' is invalid for formula " + line);
            return Optional.empty();
        }

        String baseStr = normalizeBaseName(tokens[1]);
        Base base = baseRepository.findByName(baseStr)
                .orElseThrow(() -> new IllegalArgumentException("Base '" + baseStr + "' is invalid for formula " + line));

        HashMap<String, Double> params = new HashMap<>();
        for(int i = 2; i + 1 < tokens.length; i += 2) {
            if(tokens[i].isEmpty()) {
                break;
            }

            String col = normalizeColorant(tokens[i]);
            double price = Double.parseDouble(tokens[i+1].replace(",", ".")); // DecimalFormat might be better
            params.put(col, price / volume);
        }

        return Optional.of(createFormula(paint, palette.get(), base, params));
    }

    protected abstract FormulaType createFormula(Paint paint, Palette palette, Base base, Map<String, Double> params);

    private static String normalizePaletteName(String name) {
        name = name.replaceAll(" {2,}", " ");
        {
            // ELEMENTS 123 -> 123
            Matcher m = HOLZER_PATTERN2.matcher(name);
            if(m.matches()) {
                return m.group(2);
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
        }

        return name;
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

}
