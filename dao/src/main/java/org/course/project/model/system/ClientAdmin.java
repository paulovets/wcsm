package org.course.project.model.system;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "CLIENT_ADMIN")
public class ClientAdmin implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty(value = "id")
    private Long id;

    @NotNull
    @Column(name = "NAME")
    @JsonProperty(value = "name")
    private String name;

    @NotNull
    @Column(name = "SURNAME")
    @JsonProperty(value = "surname")
    private String surname;

    @NotNull
    @Column(name = "USERNAME")
    @JsonProperty(value = "username")
    private String username;

    @NotNull
    @Column(name = "PASSWORD")
    @JsonProperty(value = "password")
    private String password;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner", fetch = FetchType.LAZY)
    private List<Site> sitesList;

    public ClientAdmin() {
        this.sitesList = new ArrayList<Site>();
    }

    public ClientAdmin(final String name, final String surname, final String username, final String password) {
        this();
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.password = password;
    }

    public void addSite(final Site site) {
        this.sitesList.add(site);
        site.setOwner(this);
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
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

    public void setUsername(final String username) {
        this.username = username;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public void setSurname(final String surname) {
        this.surname = surname;
    }

    @JsonIgnore
    public void setSites(final List<Site> sitesList){
        this.sitesList = sitesList;
    }

    public int hashCode() {
        int hash = 0;
        hash += this.name.hashCode() + this.surname.hashCode() + this.username.hashCode() + this.password.hashCode();
        hash += (this.getId() != null) ? this.getId().hashCode() : 0;
        return hash;
    }

    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ClientAdmin other = (ClientAdmin) obj;
        if (this.getId() != other.getId() || !this.name.equals(other.name) || !this.surname.equals(other.surname) ||
            !this.username.equals(other.username) || !this.password.equals(other.password))
            return false;
        return true;
    }


}

