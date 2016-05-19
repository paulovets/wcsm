package org.course.project.model.system;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

@MappedSuperclass
public abstract class AConfigurableComponent {

    @NotNull
    @Column(columnDefinition = "TEXT")
    @JsonProperty(value = "configSchema")
    private String configSchema;

    @NotNull
    @Column(columnDefinition = "TEXT")
    @JsonProperty(value = "configOptions")
    private String configOptions;

    public String getConfigSchema() {
        return this.configSchema;
    }

    public void setConfigSchema(final String configSchema) {
        this.configSchema = configSchema;
    }

    public String getConfigOptions() {
        return this.configOptions;
    }

    public void setConfigOptions(final String configOptions) {
        this.configOptions = configOptions;
    }

}
