package org.course.project.model.system;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "ALPACA_ENTITY")
public class AlpacaEntity extends AConfigurableComponent implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty(value = "id")
    private Long id;

    @NotNull
    @Column(name = "CLASS_NAME")
    @JsonProperty(value = "className")
    private String className;

    public AlpacaEntity() {
    }

    public AlpacaEntity(final String configSchema, final String configOptions, final String className) {
        this.setConfigSchema(configSchema);
        this.setConfigOptions(configOptions);
        this.className = className;
    }

    public Long getId() {
        return this.id;
    }

    public String getClassName() {
        return this.className;
    }

    public void setClassName(final String className) {
        this.className = className;
    }

    public int hashCode() {
        int hash = 0;
        hash += this.className.hashCode();
        hash += this.getConfigSchema().hashCode();
        hash += this.getConfigOptions().hashCode();
        hash += (this.getId() != null) ? this.getId().hashCode() : 0;
        return hash;
    }

    public boolean equals( final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AlpacaEntity other = (AlpacaEntity) obj;
        if (this.getId() != other.getId() || !this.getConfigSchema().equals(other.getConfigSchema()) ||
            !this.getConfigOptions().equals(other.getConfigOptions()) || !this.className.equals(other.className))
            return false;
        return true;
    }


}

