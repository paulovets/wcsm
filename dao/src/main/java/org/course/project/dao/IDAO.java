package org.course.project.dao;

import org.course.project.model.system.AbstractEntity;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by yauheni on 5/11/16.
 */
public interface IDAO {

    Object findRecordById(final Class clazz, final Long id);
    List<Object> getAllRecords(final Class clazz);
    void doPersistRecord(final Object obj);
    void doRemoveRecord(final Object obj);

}
