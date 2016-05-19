package org.course.project.model.system;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.course.project.model.system.AlpacaEntity;
import org.course.project.model.system.Component;

@Generated(value="EclipseLink-2.6.3.v20160428-rNA", date="2016-05-20T01:00:52")
@StaticMetamodel(Data.class)
public class Data_ { 

    public static volatile SingularAttribute<Data, Component> component;
    public static volatile SingularAttribute<Data, AlpacaEntity> alpacaEntity;
    public static volatile SingularAttribute<Data, String> relatedEntityName;
    public static volatile SingularAttribute<Data, Long> id;

}