package org.course.project.model.system;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.course.project.model.system.Component;
import org.course.project.model.system.Page;
import org.course.project.model.system.Template;

@Generated(value="EclipseLink-2.6.3.v20160428-rNA", date="2016-05-11T11:06:32")
@StaticMetamodel(Template.class)
public class Template_ extends AbstractEntity_ {

    public static volatile SingularAttribute<Template, Template> template;
    public static volatile ListAttribute<Template, Component> componentsList;
    public static volatile SingularAttribute<Template, Page> page;
    public static volatile ListAttribute<Template, Template> templatesList;
    public static volatile SingularAttribute<Template, Long> templateId;

}