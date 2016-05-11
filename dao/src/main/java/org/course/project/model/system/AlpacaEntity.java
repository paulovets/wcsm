package org.course.project.model.system;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "ALPACA_ENTITY")
public class AlpacaEntity extends AbstractEntity implements java.io.Serializable {

    @NotNull
    @Column(name = "CONFIG")
    private String config;

    public AlpacaEntity() {
    }

    public AlpacaEntity(final String config) {
        this.config = config;
    }

    public String getConfig() {
        return this.config;
    }

    public void setConfig(final String config) {
        this.config = config;
    }

    public int hashCode() {
        int hash = 0;
        hash += this.config.hashCode();
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
        if (this.getId() != other.getId() || !this.config.equals(other.config))
            return false;
        return true;
    }


}

