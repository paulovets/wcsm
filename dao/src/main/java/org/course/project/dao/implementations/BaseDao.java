package org.course.project.dao.implementations;

import org.course.project.dao.interfaces.IDao;
import org.springframework.util.ReflectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BaseDao<T> implements IDao<T> {

    private static final String SETTER = "set";

    @PersistenceContext
    protected EntityManager em;

    private Class<T> clazz;

    public BaseDao() {
        this.clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public BaseDao(final Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public List<T> get(final Long optionalId) {

        if ( optionalId != null ) {

            final List<T> resultList = new ArrayList<T>();
            final T entity = em.find(this.clazz, optionalId);
            if (entity != null) {
                resultList.add(entity);
            }
            return resultList;

        } else {

            final CriteriaBuilder cb = em.getCriteriaBuilder();
            final CriteriaQuery<T> cq = cb.createQuery(this.clazz);
            final Root<T> entity = cq.from(this.clazz);
            cq.select(entity);
            final TypedQuery<T> q = em.createQuery(cq);
            return q.getResultList();

        }

    }

    @Override
    public T persist(final T record) {
        em.persist(record);
        return record;
    }

    @Override
    public T remove(final T record) {
        em.remove(record);
        return record;
    }

    @Override
    public T update(final Map<String,String> fieldsToValuesMap, final Long id) {

        final Method[] methodsArray = ReflectionUtils.getAllDeclaredMethods(this.clazz);

        List<T> recordsList = this.get(id);
        T item = recordsList.get(0);
        for(String fieldName : fieldsToValuesMap.keySet()) {

            String newValue = fieldsToValuesMap.get(fieldName);
            for(Method method : methodsArray) {

                if(method.getName().toLowerCase().indexOf(BaseDao.SETTER) > -1 && method.getName().toLowerCase().indexOf(fieldName.toLowerCase()) > -1) {
                    ReflectionUtils.invokeMethod(method, item, newValue);
                    break;
                }

            }

        }

        return item;
    }

}
