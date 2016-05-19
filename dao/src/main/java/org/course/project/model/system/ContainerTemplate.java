package org.course.project.model.system;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "CONTAINER_TEMPLATE")
public class ContainerTemplate extends AConfigurableComponent implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty(value = "id")
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "containerTemplate")
    private List<Container> containersList;

    public ContainerTemplate() {
        this.containersList = new ArrayList<Container>();
    }

    public ContainerTemplate(final String configSchema, final String configOptions) {
        this();
        this.setConfigOptions(configOptions);
        this.setConfigSchema(configSchema);
    }

    public void addContainer(final Container container) {
        this.containersList.add(container);
        container.setContainerTemplate(this);
    }

    public Long getId() {
        return this.id;
    }

    @JsonIgnore
    public List<Container> getContainers() {
        return this.containersList;
    }

    public void setContainers(final List<Container> containersList) {
        this.containersList = containersList;
    }

    public int hashCode() {
        int hash = 0;
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
        Container other = (Container) obj;
        if (this.getId() != other.getId() ||
            !this.getConfigSchema().equals(other.getConfigSchema()) || !this.getConfigOptions().equals(other.getConfigOptions()))
            return false;
        return true;
    }

}
