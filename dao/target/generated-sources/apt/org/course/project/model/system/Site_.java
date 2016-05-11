package org.course.project.model.system;

import javax.annotation.Generated;
import javax.persistence.metamodel.MapAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.course.project.model.system.ClientAdmin;
import org.course.project.model.system.Page;

@Generated(value="EclipseLink-2.6.3.v20160428-rNA", date="2016-05-11T11:06:32")
@StaticMetamodel(Site.class)
public class Site_ extends AbstractEntity_ {

    public static volatile SingularAttribute<Site, ClientAdmin> owner;
    public static volatile SingularAttribute<Site, String> name;
    public static volatile MapAttribute<Site, String, Page> pagesMap;

}