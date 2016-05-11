package org.course.project.model.business;

import org.course.project.model.system.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class Event extends AbstractEntity implements java.io.Serializable {

    @NotNull
    @Column(name = "CONFIG")
    private String description;

    @NotNull
    @Column(name = "DATE")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date date;

    public Event(){}

    public Event(final Date date, final String description) {
        this.date = date;
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int hashCode() {
        int hash = 0;
        hash += this.description.hashCode();
        hash += this.date.hashCode();
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
        Event other = (Event) obj;
        if (this.getId() != other.getId() || !this.description.equals(other.description) || !this.date.equals(other.date))
            return false;
        return true;
    }

}
