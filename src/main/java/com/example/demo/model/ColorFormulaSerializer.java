package com.example.demo.model;

import java.lang.reflect.Field;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.JsonSerializer;

public class ColorFormulaSerializer extends JsonSerializer<Object> {
    @Override
    public void serialize(Object formula, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
        throws java.io.IOException
    {
        Class<?> c = formula.getClass();

        jsonGenerator.writeStartObject();

        try {
            Field id = c.getDeclaredField("id");
            id.setAccessible(true);
            jsonGenerator.writeNumberField("id", (Integer)id.get(formula));

            Field base = c.getDeclaredField("base");
            base.setAccessible(true);
            jsonGenerator.writeStringField("base", ((Base)base.get(formula)).getName());

            jsonGenerator.writeObjectFieldStart("formula");

            for(Field f : c.getDeclaredFields()) {
                if(f.getType().equals(Double.TYPE)) {
                    f.setAccessible(true);
                    jsonGenerator.writeNumberField(f.getName(), f.getDouble(formula));
                }
            }
        } catch (NoSuchFieldException|IllegalAccessException e) {
            throw new RuntimeException("Unable to serialize", e);
        }

        jsonGenerator.writeEndObject();
        jsonGenerator.writeEndObject();
    }
}
