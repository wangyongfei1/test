package com.wisdomconstruction.wisdomConstruction.tool;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class SnakeCaseDeserializer extends JsonDeserializer<Map<String, String>> {

    @Override
    public Map<String, String> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        Map<String, String> map = new HashMap<>();
        JsonNode jsonNode = p.getCodec()
                .readTree(p);
        Iterator<String> iterator = jsonNode.fieldNames();
        while (iterator.hasNext()) {
            String key = iterator.next();
            map.put(StringTool.convertToCamelCase(key), JacksonTool.getStringValueFromNode(jsonNode.get(key)));
        }
        return map;
    }
}