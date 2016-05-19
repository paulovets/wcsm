package org.course.project.model.system;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "PAGE")
public class Page extends AConfigurableComponent implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty(value = "id")
    private Long id;

    @NotNull
    @Column(name = "TYPE")
    @JsonProperty(value = "type")
    private String type;

    @NotNull
    @JoinColumn(name = "SITE_ID")
    @ManyToOne
    @JsonIgnore
    private Site site;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "page", fetch = FetchType.LAZY)
    @JsonProperty(value = "containers")
    private List<Container> containersList;

    public Page() {
        this.containersList = new ArrayList<Container>();
    }

    public Page(final String type, final String configSchema, final String configOptions) {
        this();
        this.type = type;
        this.setConfigSchema(configSchema);
        this.setConfigOptions(configOptions);
    }

    public void addContainer(final Container container) {
        this.containersList.add(container);
        container.setPage(this);
    }

    public Long getId() {
        return this.id;
    }

    public Site getSite() {
        return this.site;
    }

    public List<Container> getContainers(){
        return this.containersList;
    }

    public String getType(){
        return this.type;
    }

    public void setSite(final Site site) {
        this.site = site;
    }

    public void setContainers(final List<Container> templatesList){
        this.containersList = templatesList;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public int hashCode() {
        int hash = 0;
        hash += this.type.hashCode();
        hash += this.getConfigSchema().hashCode();
        hash += this.getConfigOptions().hashCode();
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
        Page other = (Page) obj;
        if (this.getId() != other.getId() || !this.type.equals(other.type) ||
            !this.getConfigSchema().equals(other.getConfigSchema()) || !this.getConfigOptions().equals(other.getConfigOptions()))
            return false;
        return true;
    }


}

