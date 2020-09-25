package com.yura.holzercolor.model;

import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.JsonSerializer;

public class ColorFormulaSerializer extends JsonSerializer<Formula> {
    @Override
    public void serialize(Formula formula, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
        throws java.io.IOException
    {
        jsonGenerator.writeStartObject();

        jsonGenerator.writeNumberField("id", formula.getId());
        jsonGenerator.writeStringField("base", formula.getBase().getName());

        jsonGenerator.writeObjectFieldStart("formula");
        for(Map.Entry<String, Double> e : formula.getValues().entrySet()) {
            jsonGenerator.writeNumberField(e.getKey(), e.getValue());
        }

        jsonGenerator.writeEndObject();
        jsonGenerator.writeEndObject();
    }
}
