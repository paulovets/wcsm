package org.course.project.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by yauheni on 5/11/16.
 */
public class BaseDao implements IDAO {

    @PersistenceContext
    protected EntityManager em;

    @Override
    public Object findRecordById(Class clazz, Long id) {
        return em.find(clazz, id);
    }

    @Override
    public List<Object> getAllRecords(Class clazz) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(clazz);
        Root entity = cq.from(clazz);
        cq.select(entity);
        TypedQuery q = em.createQuery(cq);
        return q.getResultList();
    }

    @Override
    public void doPersistRecord(Object obj) {
        em.persist(obj);
    }

    @Override
    public void doRemoveRecord(Object obj) {
        em.remove(obj);
    }
}
