package org.course.project.dao.interfaces;

import java.util.List;
import java.util.Map;

public interface IDao<T> {
    List<T> get(final Long optionalId);
    List<T> getByParent(final String parentFieldName, final Long parentId);
    T persist(final T record);
    T remove(final T obj);
    T update(final Map<String,String> fieldsToValuesMap, final Long id);
}
