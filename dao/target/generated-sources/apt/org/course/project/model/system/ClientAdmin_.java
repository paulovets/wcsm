package org.course.project.model.system;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.course.project.model.system.Site;

@Generated(value="EclipseLink-2.6.3.v20160428-rNA", date="2016-05-20T01:00:52")
@StaticMetamodel(ClientAdmin.class)
public class ClientAdmin_ { 

    public static volatile SingularAttribute<ClientAdmin, String> password;
    public static volatile ListAttribute<ClientAdmin, Site> sitesList;
    public static volatile SingularAttribute<ClientAdmin, String> surname;
    public static volatile SingularAttribute<ClientAdmin, String> name;
    public static volatile SingularAttribute<ClientAdmin, Long> id;
    public static volatile SingularAttribute<ClientAdmin, String> username;

}