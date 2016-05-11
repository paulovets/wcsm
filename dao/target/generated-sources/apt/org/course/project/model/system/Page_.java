package org.course.project.model.system;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.course.project.model.system.Site;
import org.course.project.model.system.Template;

@Generated(value="EclipseLink-2.6.3.v20160428-rNA", date="2016-05-11T11:06:32")
@StaticMetamodel(Page.class)
public class Page_ extends AbstractEntity_ {

    public static volatile SingularAttribute<Page, Site> site;
    public static volatile ListAttribute<Page, Template> templatesList;
    public static volatile SingularAttribute<Page, String> type;

}