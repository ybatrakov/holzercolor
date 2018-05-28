package com.example.demo.model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.JsonSerializer;

public class ColorFormulaSerializer extends JsonSerializer<ColorFormula> {
    @Override
    public void serialize(ColorFormula formula, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
        throws java.io.IOException
    {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", formula.getId());
        jsonGenerator.writeStringField("base", formula.getBase().getName());
        jsonGenerator.writeObjectFieldStart("formula");

        jsonGenerator.writeNumberField("ft", formula.getFt());
        jsonGenerator.writeNumberField("hs", formula.getHs());
        jsonGenerator.writeNumberField("ks", formula.getKs());
        jsonGenerator.writeNumberField("ls", formula.getLs());
        jsonGenerator.writeNumberField("lt", formula.getLt());
        jsonGenerator.writeNumberField("ms", formula.getMs());
        jsonGenerator.writeNumberField("mt", formula.getMt());
        jsonGenerator.writeNumberField("pt", formula.getPt());
        jsonGenerator.writeNumberField("rs", formula.getRs());
        jsonGenerator.writeNumberField("rt", formula.getRt());
        jsonGenerator.writeNumberField("st", formula.getSt());
        jsonGenerator.writeNumberField("tt", formula.getTt());
        jsonGenerator.writeNumberField("us", formula.getUs());
        jsonGenerator.writeNumberField("vt", formula.getVt());
        jsonGenerator.writeNumberField("xt", formula.getXt());
        jsonGenerator.writeNumberField("zt", formula.getZt());

        jsonGenerator.writeEndObject();
        jsonGenerator.writeEndObject();
    }
}
