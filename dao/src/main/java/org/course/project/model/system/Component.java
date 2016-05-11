package org.course.project.model.system;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "COMPONENT")
public class Component extends AbstractEntity implements java.io.Serializable {

    @NotNull
    @ManyToOne
    private Template template;

    public Component() {
    }

    public Long getId() {
        return this.getId();
    }

    public Template getTemplate() {
        return this.template;
    }

    public void setTemplate(final Template template) {
        this.template = template;
    }

    public int hashCode() {
        int hash = 0;
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
        Component other = (Component) obj;
        if (this.getId() != other.getId())
            return false;
        return true;
    }


}

