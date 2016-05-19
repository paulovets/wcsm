package org.course.project.model.system;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "CONTAINER")
public class Container extends AConfigurableComponent implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty(value = "id")
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "PAGE_ID")
    @JsonIgnore
    private Page page;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "TEMPLATE_ID")
    @JsonProperty(value = "template")
    private ContainerTemplate containerTemplate;

    @ManyToOne
    @JoinColumn(name="CONTAINER_ID", referencedColumnName="id")
    @JsonIgnore
    private Container container;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "container")
    @JsonProperty(value = "containers")
    private List<Container> containersList;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy ="container")
    @JsonProperty(value = "components")
    private List<Component> componentsList;

    public Container() {
        this.containersList = new ArrayList<Container>();
        this.componentsList = new ArrayList<Component>();
    }

    public Container(final String configSchema, final String configOptions) {
        this();
        this.setConfigOptions(configOptions);
        this.setConfigSchema(configSchema);
    }

    public void addComponent(final Component component) {
        this.componentsList.add(component);
        component.setContainer(this);
    }

    public void addContainer(final Container container) {
        this.containersList.add(container);
        container.setContainer(this);
    }

    public Long getId() {
        return this.id;
    }

    public Page getPage() {
        return this.page;
    }

    public Container getContainer() {
        return this.container;
    }

    public List<Container> getContainers() {
        return this.containersList;
    }

    public List<Component> getComponents() {
        return this.componentsList;
    }

    public ContainerTemplate getContainerTemplate() {
        return containerTemplate;
    }

    public void setPage(final Page page) {
        this.page = page;
    }

    public void setContainer(final Container container) {
        this.container = container;
    }

    public void setContainers(final List<Container> containersList) {
        this.containersList = containersList;
    }

    public void setComponents(final List<Component> componentsList) {
        this.componentsList = componentsList;
    }

    public void setContainerTemplate(final ContainerTemplate containerTemplate) {
        this.containerTemplate = containerTemplate;
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

