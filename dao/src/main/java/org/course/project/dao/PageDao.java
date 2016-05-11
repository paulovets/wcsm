package org.course.project.dao;

import org.course.project.model.system.Page;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;

/**
 * Created by yauheni on 5/11/16.
 */
public class PageDao extends BaseDao {

    public Page getHomePage(final Long siteId) {
//        CriteriaBuilder cb = em.getCriteriaBuilder();
//        CriteriaQuery<Page> cq = cb.createQuery(Page.class);
//        Root<Page> page = cq.from(Page.class);
//        Metamodel m = em.getMetamodel();
//        EntityType<Page> Page_ = m.entity(Page.class);
//        cq.where(page.get(Page_.).in("HOME"));
//        TypedQuery q = em.createQuery(cq);
//        return (Page)q.getSingleResult();
        return null;
    }

}
