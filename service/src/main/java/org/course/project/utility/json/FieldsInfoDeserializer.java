package org.course.project.utility.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.Iterator;

public final class FieldsInfoDeserializer extends JsonDeserializer<Alpaca.FieldsInfo> {

    private final static String NESTED_PROPERTY = "properties";

    @Override
    public Alpaca.FieldsInfo deserialize(final JsonParser jp, final DeserializationContext ctxt) throws IOException {

        final Alpaca.FieldsInfo fieldsInfo = new Alpaca.FieldsInfo();

        final JsonNode root = jp.readValueAsTree();
        final JsonNode firstLvlFields = root.get(FieldsInfoDeserializer.NESTED_PROPERTY);
        this.doCollectFields(firstLvlFields, fieldsInfo);

        return fieldsInfo;

    }

    private void doCollectFields(final JsonNode root, final Alpaca.FieldsInfo target) {

        Iterator<String> fieldNames = root.fieldNames();
        while(fieldNames.hasNext()) {

            String fieldName = fieldNames.next();
            JsonNode child = root.get(fieldName);
            if (child.get(FieldsInfoDeserializer.NESTED_PROPERTY) != null) {
                this.doCollectFields(child, target);
                continue;
            }

            target.fieldsNamesList.add(fieldName);

        }

    }

}
