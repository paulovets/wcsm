package org.course.project.model.system;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TEMPLATE")
public class Template extends AbstractEntity implements java.io.Serializable {

    @Column(name = "TEMPLATE_ID")
    private Long templateId;

    @ManyToOne
    private Page page;

    @ManyToOne
    @JoinColumn(name="TEMPLATE_ID", referencedColumnName="Id")
    private Template template;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy="template")
    private List<Template> templatesList;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy="template")
    private List<Component> componentsList;

    public Template() {
        this.templatesList = new ArrayList<Template>();
        this.componentsList = new ArrayList<Component>();
    }

    public Long getTemplateId() {
        return this.templateId;
    }

    public Page getPage() {
        return this.page;
    }

    public Template getTemplate() {
        return this.template;
    }

    public List<Template> getTemplates() {
        return this.templatesList;
    }

    public List<Component> getComponents() {
        return this.componentsList;
    }

    public void setTemplateId(final Long templateId) {
        this.templateId = templateId;
    }

    public void setPage(final Page page) {
        this.page = page;
    }

    public void setTemplate(final Template template) {
        this.template = template;
    }

    public void setTemplates(final List<Template> templatesList) {
        this.templatesList = templatesList;
    }

    public void setComponents(final List<Component> componentsList) {
        this.componentsList = componentsList;
    }

    public int hashCode() {
        int hash = 0;
        hash += (this.templateId != null) ? this.templateId.hashCode() : 0;
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
        Template other = (Template) obj;
        if (this.templateId != other.templateId || this.getId() != other.getId())
            return false;
        return true;
    }


}

