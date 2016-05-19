package org.course.project.model.system;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "COMPONENT")
public class Component extends AConfigurableComponent implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty(value = "id")
    private Long id;

    @NotNull
    @JoinColumn(name = "TEMPLATE_ID")
    @ManyToOne
    @JsonProperty(value = "template")
    private ComponentTemplate componentTemplate;

    @NotNull
    @JoinColumn(name = "CONTAINER_ID")
    @ManyToOne
    @JsonIgnore
    private Container container;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "component")
    @JoinColumn(name = "COMPONENT_ID")
    @JsonProperty(value = "data")
    private Data data;

    public Component() {
    }

    public Component(final String configSchema, final String configOptions) {
        this.setConfigSchema(configSchema);
        this.setConfigOptions(configOptions);
    }

    public Long getId() {
        return this.id;
    }

    public Container getContainer() {
        return this.container;
    }

    public ComponentTemplate getComponentTemplate() {
        return componentTemplate;
    }

    public Data getData() {
        return data;
    }

    public void setContainer(final Container container) {
        this.container = container;
    }

    public void setComponentTemplate(ComponentTemplate componentTemplate) {
        this.componentTemplate = componentTemplate;
    }

    public void setData(final Data data) {
        this.data = data;
        data.setComponent(this);
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
        Component other = (Component) obj;
        if (this.getId() != other.getId() || !this.getConfigSchema().equals(other.getConfigSchema()) ||
            !this.getConfigOptions().equals(other.getConfigOptions()))
            return false;
        return true;
    }


}

