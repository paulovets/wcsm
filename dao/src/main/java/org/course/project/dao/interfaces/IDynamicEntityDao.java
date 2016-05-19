package org.course.project.dao.interfaces;

import org.eclipse.persistence.dynamic.DynamicEntity;

import java.util.List;
import java.util.Map;

public interface IDynamicEntityDao {

    List<DynamicEntity> getRecordsByParent(final String className, final String parentId);
    DynamicEntity get(final String className, final Long id);
    DynamicEntity update(final String className, final Map<String,String> fieldsToValuesMap, final Long id);
    DynamicEntity remove(final DynamicEntity record);
    DynamicEntity persist(final DynamicEntity record);

}
