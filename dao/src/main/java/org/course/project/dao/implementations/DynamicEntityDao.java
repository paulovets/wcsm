package org.course.project.dao.implementations;

import org.course.project.dao.interfaces.IDynamicEntityDao;
import org.course.project.utility.DynamicHelper;
import org.eclipse.persistence.dynamic.DynamicEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Map;

@Repository("dynamicDao")
public final class DynamicEntityDao implements IDynamicEntityDao {

    @Autowired
    private DynamicHelper dynamicHelper;

    @PersistenceContext
    protected EntityManager em;

    @Override
    public List<DynamicEntity> getRecordsByParent(final String className, final String parentId) {

        final String queryStr = new String("SELECT e FROM " + className + " e WHERE e.parentid = " + parentId);
        final Query query = em.createQuery(queryStr.toString());
        return (List<DynamicEntity>)query.getResultList();

    }

    @Override
    public DynamicEntity get(final String className, final Long id) {

        final DynamicEntity blankEntity = dynamicHelper.getJDH().newDynamicEntity("org.course.project.model.dynamic." + className);

        final String queryStr = new String("SELECT e FROM " + className + " e WHERE e.id = " + id);
        final Query query = em.createQuery(queryStr.toString());
        final List<DynamicEntity> dynamicsList = query.getResultList();
        return dynamicsList.size() > 0 ? dynamicsList.get(0) : blankEntity;

    }

    @Override
    public DynamicEntity update(final String className, final Map<String,String> fieldsToValuesMap, final Long id) {

        final DynamicEntity record = this.get(className, id);
        for(String fieldName : fieldsToValuesMap.keySet()) {
            String fieldValue = fieldsToValuesMap.get(fieldName);
            record.set(fieldName, fieldValue);
        }
        return record;

    }

    @Override
    public DynamicEntity remove(final DynamicEntity record) {
        em.remove(record);
        return record;
    }

    @Override
    public DynamicEntity persist(final DynamicEntity record) {
        em.persist(record);
        return record;
    }
}
