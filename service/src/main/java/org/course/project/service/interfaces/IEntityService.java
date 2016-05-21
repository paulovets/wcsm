package org.course.project.service.interfaces;

import org.course.project.dao.interfaces.IDao;
import org.course.project.utility.exceptions.ParametersException;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

public interface IEntityService<T> {
    List<T> get(final Map<String,String> paramsMap) throws ParametersException;
    List<T> getByParents(final Map<String, String> paramsMap) throws ParametersException;
    T persist(final Map<String, String> entityData) throws ParametersException, IOException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException;
    T remove(final Map<String,String> paramsMap) throws ParametersException;
    T update(final Map<String,String> paramsMap) throws ParametersException;
}
