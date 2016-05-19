package org.course.project.utility;

import org.eclipse.persistence.dynamic.DynamicClassLoader;
import org.eclipse.persistence.jpa.JpaHelper;
import org.eclipse.persistence.jpa.dynamic.JPADynamicHelper;
import org.eclipse.persistence.sessions.Session;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component("dynamicHelper")
public final class DynamicHelper {

    @PersistenceContext
    private EntityManager em;
    private DynamicClassLoader dcl;
    private JPADynamicHelper jdh;

    private DynamicHelper() {}

    public synchronized JPADynamicHelper getJDH() {
        if (this.jdh == null){
            this.jdh = new JPADynamicHelper(this.em);
        }
        return this.jdh;
    }

    public synchronized DynamicClassLoader getDCL() {
        if (this.dcl == null) {
            final Session session = JpaHelper.getEntityManager(this.em).getServerSession();
            this.dcl = DynamicClassLoader.lookup(session);
        }
        return this.dcl;
    }

}
