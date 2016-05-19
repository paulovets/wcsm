package org.course.project.model.system;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.course.project.model.system.ComponentTemplate;
import org.course.project.model.system.Container;
import org.course.project.model.system.Data;

@Generated(value="EclipseLink-2.6.3.v20160428-rNA", date="2016-05-20T01:00:52")
@StaticMetamodel(Component.class)
public class Component_ extends AConfigurableComponent_ {

    public static volatile SingularAttribute<Component, Container> container;
    public static volatile SingularAttribute<Component, Data> data;
    public static volatile SingularAttribute<Component, Long> id;
    public static volatile SingularAttribute<Component, ComponentTemplate> componentTemplate;

}