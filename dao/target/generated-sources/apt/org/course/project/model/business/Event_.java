package org.course.project.model.business;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.course.project.model.system.AbstractEntity_;

@Generated(value="EclipseLink-2.6.3.v20160428-rNA", date="2016-05-11T11:06:32")
@StaticMetamodel(Event.class)
public class Event_ extends AbstractEntity_ {

    public static volatile SingularAttribute<Event, Date> date;
    public static volatile SingularAttribute<Event, String> description;

}