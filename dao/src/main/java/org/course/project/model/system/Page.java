package org.course.project.model.system;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "PAGE")
public class Page extends AbstractEntity implements java.io.Serializable {

    @NotNull
    @Column(name = "TYPE")
    private String type;

    @NotNull
    @ManyToOne
    private Site site;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "page", fetch = FetchType.LAZY)
    private List<Template> templatesList;

    public Page() {
        this.templatesList = new ArrayList<Template>();
    }

    public Page(final String type) {
        this();
        this.type = type;
    }

    public Site getSite() {
        return this.site;
    }

    public List<Template> getTemplates(){
        return this.templatesList;
    }

    public String getType(){
        return this.type;
    }

    public void setSite(final Site site) {
        this.site = site;
    }

    public void setTemplates(final List<Template> templatesList){
        this.templatesList = templatesList;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public int hashCode() {
        int hash = 0;
        hash += this.type.hashCode();
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
        Page other = (Page) obj;
        if (this.getId() != other.getId() || !this.type.equals(other.type))
            return false;
        return true;
    }


}

