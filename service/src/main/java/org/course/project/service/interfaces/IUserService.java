package org.course.project.service.interfaces;

import org.course.project.model.system.ClientAdmin;
import org.course.project.utility.exceptions.ParametersException;

import java.util.Map;

public interface IUserService extends IEntityService<ClientAdmin> {
    ClientAdmin doLogin(final Map<String,String> credentialsMap) throws ParametersException;
}
