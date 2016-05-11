package org.course.project.model.system;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "CLIENT_ADMIN")
public class ClientAdmin extends AbstractEntity implements java.io.Serializable {

    @NotNull
    @Column(name = "NAME")
    private String name;

    @NotNull
    @Column(name = "SURNAME")
    private String surname;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner", fetch = FetchType.LAZY)
    private List<Site> sitesList;

    public ClientAdmin() {
    }

    public ClientAdmin(final String name, final String surname) {
        this.name = name;
        this.surname = surname;
    }

    public String getName() {
        return this.name;
    }

    public String getSurname() {
        return this.surname;
    }

    public List<Site> getSites(){
        return this.sitesList;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setSurname(final String surname) {
        this.surname = surname;
    }

    public void setSites(final List<Site> sitesList){
        this.sitesList = sitesList;
    }

    public int hashCode() {
        int hash = 0;
        hash += this.name.hashCode() + this.surname.hashCode();
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
        ClientAdmin other = (ClientAdmin) obj;
        if (this.getId() != other.getId() || !this.name.equals(other.name) || !this.surname.equals(other.surname))
            return false;
        return true;
    }


}

