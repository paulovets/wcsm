package org.course.project.model.system;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.course.project.model.system.Container;
import org.course.project.model.system.Site;

@Generated(value="EclipseLink-2.6.3.v20160428-rNA", date="2016-05-20T01:00:52")
@StaticMetamodel(Page.class)
public class Page_ extends AConfigurableComponent_ {

    public static volatile SingularAttribute<Page, Site> site;
    public static volatile ListAttribute<Page, Container> containersList;
    public static volatile SingularAttribute<Page, Long> id;
    public static volatile SingularAttribute<Page, String> type;

}