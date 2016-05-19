package org.course.project.dao.implementations;

import org.course.project.dao.interfaces.IComponentDao;
import org.course.project.dao.interfaces.IDataDao;
import org.course.project.model.system.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository("dataDao")
public final class DataDao extends BaseDao<Data> implements IDataDao {

    @Autowired
    private IComponentDao componentDao;

    @Override
    public List<Data> getByParent(final Long componentId) {

        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<Data> cq = cb.createQuery(Data.class);
        final Root<Data> data = cq.from(Data.class);
        cq.where(cb.and(cb.equal(data.get(Data_.component).get(Component_.id), componentId)));
        final TypedQuery<Data> q = em.createQuery(cq);
        return q.getResultList();

    }

    @Override
    public Data persist(final Long componentId, final Data data) {

        final List<Component> componentList = this.componentDao.get(componentId);
        componentList.get(0).setData(data);
        return data;

    }

}
