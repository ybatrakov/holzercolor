package com.example.demo.model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.JsonSerializer;

// Better doing it with reflection?
public class ColorFormulaFacadeSerializer extends JsonSerializer<ColorFormulaFacade> {
    @Override
    public void serialize(ColorFormulaFacade formula, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
        throws java.io.IOException
    {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", formula.getId());
        jsonGenerator.writeStringField("base", formula.getBase().getName());
        jsonGenerator.writeObjectFieldStart("formula");

        jsonGenerator.writeNumberField("ba", formula.getBa());
        jsonGenerator.writeNumberField("be", formula.getBe());
        jsonGenerator.writeNumberField("je", formula.getJe());
        jsonGenerator.writeNumberField("jo", formula.getJo());
        jsonGenerator.writeNumberField("jp", formula.getJp());
        jsonGenerator.writeNumberField("lt", formula.getLt());
        jsonGenerator.writeNumberField("ma", formula.getMa());
        jsonGenerator.writeNumberField("na", formula.getNa());
        jsonGenerator.writeNumberField("op", formula.getOp());
        jsonGenerator.writeNumberField("ov", formula.getOv());
        jsonGenerator.writeNumberField("ra", formula.getRa());
        jsonGenerator.writeNumberField("rp", formula.getRp());
        jsonGenerator.writeNumberField("rv", formula.getRv());
        jsonGenerator.writeNumberField("tt", formula.getTt());
        jsonGenerator.writeNumberField("vi", formula.getVi());
        jsonGenerator.writeNumberField("vr", formula.getVr());

        jsonGenerator.writeEndObject();
        jsonGenerator.writeEndObject();
    }
}
