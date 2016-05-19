package org.course.project.model.system;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "SITE")
public class Site extends AConfigurableComponent implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty(value = "id")
    private Long id;

    @NotNull
    @Column(name = "NAME")
    @JsonProperty(value = "name")
    private String name;

    @MapKey(name = "type")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "site", fetch = FetchType.LAZY)
    @JsonProperty(value = "pages")
    private Map<String,Page> pagesMap;

    @NotNull
    @JoinColumn(name = "OWNER_ID")
    @ManyToOne
    @JsonIgnore
    private ClientAdmin owner;

    public Site() {
        this.pagesMap = new HashMap<String,Page>();
    }

    public Site(final String name, final String configSchema, final String configOptions) {
        this();
        this.name = name;
        this.setConfigSchema(configSchema);
        this.setConfigOptions(configOptions);
    }

    public void addPage(final Page page) {
        this.pagesMap.put(page.getType(), page);
        page.setSite(this);
    }

    public Long getId() {
        return this.id;
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
        hash += this.getConfigSchema().hashCode();
        hash += this.getConfigOptions().hashCode();
        hash += this.name.hashCode();
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
        Site other = (Site) obj;
        if (this.getId() != other.getId() || !this.name.equals(other.name) ||
            !this.getConfigSchema().equals(other.getConfigSchema()) || !this.getConfigOptions().equals(other.getConfigOptions()))
            return false;
        return true;
    }


}

