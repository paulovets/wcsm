package org.course.project.model.system;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.course.project.model.system.Component;
import org.course.project.model.system.Container;
import org.course.project.model.system.ContainerTemplate;
import org.course.project.model.system.Page;

@Generated(value="EclipseLink-2.6.3.v20160428-rNA", date="2016-05-20T01:00:52")
@StaticMetamodel(Container.class)
public class Container_ extends AConfigurableComponent_ {

    public static volatile SingularAttribute<Container, Container> container;
    public static volatile ListAttribute<Container, Component> componentsList;
    public static volatile ListAttribute<Container, Container> containersList;
    public static volatile SingularAttribute<Container, Long> id;
    public static volatile SingularAttribute<Container, Page> page;
    public static volatile SingularAttribute<Container, ContainerTemplate> containerTemplate;

}