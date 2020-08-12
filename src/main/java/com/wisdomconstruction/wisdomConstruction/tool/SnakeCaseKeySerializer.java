package com.wisdomconstruction.wisdomConstruction.tool;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class SnakeCaseKeySerializer extends JsonSerializer<String> {

    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        String key = StringTool.convertToSnakeCase(value);
        gen.writeFieldName(key);
    }
}