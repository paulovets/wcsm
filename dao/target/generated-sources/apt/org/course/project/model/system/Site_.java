package org.course.project.model.system;

import javax.annotation.Generated;
import javax.persistence.metamodel.MapAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.course.project.model.system.ClientAdmin;
import org.course.project.model.system.Page;

@Generated(value="EclipseLink-2.6.3.v20160428-rNA", date="2016-05-20T01:00:52")
@StaticMetamodel(Site.class)
public class Site_ extends AConfigurableComponent_ {

    public static volatile SingularAttribute<Site, ClientAdmin> owner;
    public static volatile SingularAttribute<Site, String> name;
    public static volatile SingularAttribute<Site, Long> id;
    public static volatile MapAttribute<Site, String, Page> pagesMap;

}