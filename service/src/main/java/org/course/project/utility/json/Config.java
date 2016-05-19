package org.course.project.utility.json;

import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.ArrayList;
import java.util.List;

public final class Config {

    public static class BaseConfig {
        @JsonRawValue
        public String configOptions;

        @JsonRawValue
        public String configSchema;

        public Long id;

        public BaseConfig() {}

        public BaseConfig(final String configOptions, final String configSchema, final Long id) {
            this.configOptions = configOptions;
            this.configSchema = configSchema;
            this.id = id;
        }
    }

    @JsonRootName(value = "pageConfig")
    public static class PageConfig extends BaseConfig {
        public List<TemplateConfig> templatesList;

        public PageConfig(final String pageConfigOptions, final String pageConfigSchema, final Long id) {
            super(pageConfigOptions, pageConfigSchema, id);
            this.templatesList = new ArrayList<TemplateConfig>();
        }

    }

    public static class TemplateConfig extends PageConfig {
        public List<ComponentConfig> componentsList;

        @JsonRawValue
        public String additionalConfigOptions;

        @JsonRawValue
        public String addtionalCconfigSchema;

        public TemplateConfig(final String templateConfigOptions, final String templateConfigSchema,
                              final String additionalConfigOptions, final String addtionalCconfigSchema,
                              final Long id) {
            super(templateConfigOptions, templateConfigSchema, id);
            this.additionalConfigOptions = additionalConfigOptions;
            this.addtionalCconfigSchema = addtionalCconfigSchema;
            this.componentsList = new ArrayList<ComponentConfig>();
        }

    }

    public static class ComponentConfig extends BaseConfig {
        public List<String> datasList;

        @JsonRawValue
        public String alpacaOptions;

        @JsonRawValue
        public String alpacaSchema;

        @JsonRawValue
        public String additionalConfigOptions;

        @JsonRawValue
        public String addtionalCconfigSchema;

        public ComponentConfig(final String componentConfigOptions, final String componentConfigSchema,
                               final String additionalConfigOptions, final String addtionalCconfigSchema,
                               final String alpacaOptions, final String alpacaSchema, final Long id) {
            super(componentConfigOptions, componentConfigSchema, id);
            this.datasList = new ArrayList<String>();
            this.additionalConfigOptions = additionalConfigOptions;
            this.addtionalCconfigSchema = addtionalCconfigSchema;
            this.alpacaOptions = alpacaOptions;
            this.alpacaSchema = alpacaSchema;
        }
    }

}
