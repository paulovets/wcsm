package org.course.project.service.interfaces;

import org.course.project.model.system.Page;
import org.course.project.utility.exceptions.ParametersException;

import java.io.IOException;
import java.util.Map;

public interface IPageService extends IEntityService<Page> {

    String getPageConfig(final Map<String, String> paramsMap) throws ParametersException, IOException;
    Page getPageByType(final Map<String, String> paramsMap) throws ParametersException;

}
