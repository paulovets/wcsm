package org.course.project.model.system;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.course.project.model.system.AbstractEntity;
import org.course.project.model.system.AlpacaEntity;
import org.course.project.model.system.Component;
import org.course.project.model.system.Page;

@Generated(value="EclipseLink-2.6.3.v20160428-rNA", date="2016-05-11T11:06:32")
@StaticMetamodel(Data.class)
public class Data_ { 

    public static volatile SingularAttribute<Data, Component> component;
    public static volatile SingularAttribute<Data, AlpacaEntity> alpacaEntity;
    public static volatile SingularAttribute<Data, AbstractEntity> abstractEntity;
    public static volatile SingularAttribute<Data, Long> id;
    public static volatile SingularAttribute<Data, Page> page;

}