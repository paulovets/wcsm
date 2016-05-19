package org.course.project.utility.json;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Alpaca {

    @JsonDeserialize(using = FieldsInfoDeserializer.class)
    public static class FieldsInfo {
        public List<String> fieldsNamesList;

        public FieldsInfo() {
            this.fieldsNamesList = new ArrayList<String>();
        }

    }

    @JsonDeserialize(using = FieldsDataDeserializer.class)
    public static class FieldsData {
        public Map<String,String> fieldToValueMap;

        public FieldsData() {
            this.fieldToValueMap = new HashMap<String, String>();
        }

    }
}
