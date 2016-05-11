package org.course.project.model.system;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "DATA")
public class Data implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    private Component component;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    private AbstractEntity abstractEntity;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    private AlpacaEntity alpacaEntity;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    private Page page;

    public Data() {
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

    public AbstractEntity getAbstractEntity() {
        return this.abstractEntity;
    }

    public void setAbstractEntity(final AbstractEntity abstractEntity) {
        this.abstractEntity = abstractEntity;
    }

    public AlpacaEntity getAlpacaEntity() {
        return this.alpacaEntity;
    }

    public void setAlpacaEntity(final AlpacaEntity alpacaEntity) {
        this.alpacaEntity = alpacaEntity;
    }

    public Page getPage() {
        return this.page;
    }

    public void setPage(final Page page) {
        this.page = page;
    }

    public int hashCode() {
        int hash = 0;
        hash += (this.id != null) ? this.id.hashCode() : 0;
        return hash;
    }

    public boolean equals( final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Data other = (Data) obj;
        if (this.id != other.id)
            return false;
        return true;
    }


}

