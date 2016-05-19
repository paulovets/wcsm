package org.course.project.utility.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.Iterator;

public final class FieldsDataDeserializer extends JsonDeserializer<Alpaca.FieldsData> {

    @Override
    public Alpaca.FieldsData deserialize(final JsonParser jp, final DeserializationContext ctxt) throws IOException {

        final Alpaca.FieldsData fieldsData = new Alpaca.FieldsData();

        final JsonNode root = jp.readValueAsTree();
        this.doCollectData(root, fieldsData);

        return fieldsData;

    }

    private void doCollectData(final JsonNode root, final Alpaca.FieldsData target) {

        Iterator<String> fieldNames = root.fieldNames();
        while(fieldNames.hasNext()) {

            String fieldName = fieldNames.next();
            JsonNode child = root.get(fieldName);
            if (child.fieldNames().hasNext()) {
                this.doCollectData(child, target);
                continue;
            }

            String fieldValue = root.get(fieldName).asText();
            target.fieldToValueMap.put(fieldName, fieldValue);

        }

    }

}
