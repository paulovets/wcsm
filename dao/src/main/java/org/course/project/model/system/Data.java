package org.course.project.model.system;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "DATA")
public class Data implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty(value = "id")
    private Long id;

    @NotNull
    @JoinColumn(name = "COMPONENT_ID")
    @OneToOne
    @JsonIgnore
    private Component component;

    @NotNull
    @Column(name = "RELATED_ENTITY_NAME")
    @JsonProperty(value = "relatedEntityClassName")
    private String relatedEntityName;

    @NotNull
    @JoinColumn(name = "ALPACA_ID")
    @ManyToOne
    @JsonProperty(value = "alpacaEntity")
    private AlpacaEntity alpacaEntity;

    public Data() {
    }

    public Data(final Component component, final AlpacaEntity alpacaEntity, final String relatedEntityName) {
        this.component = component;
        this.alpacaEntity = alpacaEntity;
        this.relatedEntityName = relatedEntityName;
    }

    public Data(final AlpacaEntity alpacaEntity, final String relatedEntityName) {
        this.alpacaEntity = alpacaEntity;
        this.relatedEntityName = relatedEntityName;
    }

    public Long getId() {
        return this.id;
    }

    public Component getComponent() {
        return this.component;
    }

    public void setComponent(final Component component) {
        this.component = component;
    }

    public String getRelatedEntityName() {
        return this.relatedEntityName;
    }

    public void setRelatedEntityName(final String relatedEntityName) {
        this.relatedEntityName = relatedEntityName;
    }

    public AlpacaEntity getAlpacaEntity() {
        return this.alpacaEntity;
    }

    public void setAlpacaEntity(final AlpacaEntity alpacaEntity) {
        this.alpacaEntity = alpacaEntity;
    }

    public int hashCode() {
        int hash = 0;
        hash += this.relatedEntityName.hashCode();
        hash += (this.id != null) ? this.id.hashCode() : 0;
        return hash;
    }

    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Data other = (Data) obj;
        if (this.id != other.id || !this.relatedEntityName.equals(other.relatedEntityName))
            return false;
        return true;
    }


}

