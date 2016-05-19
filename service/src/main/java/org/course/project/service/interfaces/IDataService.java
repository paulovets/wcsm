package org.course.project.service.interfaces;

import org.course.project.model.system.Data;
import org.course.project.utility.exceptions.ParametersException;

import java.util.List;
import java.util.Map;

public interface IDataService extends IEntityService<Data> {
    List<Data> getByParents(final Map<String, String> paramsMap) throws ParametersException;
}
