package org.course.project.service.interfaces;

import org.course.project.utility.exceptions.ParametersException;
import org.eclipse.persistence.dynamic.DynamicEntity;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface IDynamicEntityService{
    void initDynamicClasses() throws IOException, ParametersException;
    List<DynamicEntity> getRecordsByParent(final Map<String, String> paramsMap) throws ParametersException;
    DynamicEntity persist(final Map<String, String> entityDataMap) throws ParametersException, IOException;
    DynamicEntity get(final Map<String, String> paramsMap) throws ParametersException;
    DynamicEntity remove(final Map<String,String> paramsMap) throws ParametersException;
    DynamicEntity update(final Map<String,String> entityDataMap) throws ParametersException;
    void addType(final String className, final String configSchema) throws IOException;
}
