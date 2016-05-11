package org.course.project.model.system;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Map;

@Entity
@Table(name = "SITE")
public class Site extends AbstractEntity implements java.io.Serializable {

    @NotNull
    @Column(name = "NAME")
    private String name;

    @MapKeyColumn(name = "TYPE")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "site", fetch = FetchType.LAZY)
    private Map<String,Page> pagesMap;

    @NotNull
    @ManyToOne
    private ClientAdmin owner;

    public Site() {
    }

    public Site(final String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public ClientAdmin getOwner() {
        return this.owner;
    }

    public Map<String,Page> getPages(){
        return this.pagesMap;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setOwner(final ClientAdmin owner) {
        this.owner = owner;
    }

    public void setPages(final Map<String,Page> pagesMap){
        this.pagesMap = pagesMap;
    }

    public int hashCode() {
        int hash = 0;
        hash += this.name.hashCode();
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
        Site other = (Site) obj;
        if (this.getId() != other.getId() || !this.name.equals(other.name))
            return false;
        return true;
    }


}

