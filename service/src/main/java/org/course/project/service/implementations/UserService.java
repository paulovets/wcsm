package org.course.project.service.implementations;

import org.course.project.dao.interfaces.IClientAdminDao;
import org.course.project.dao.interfaces.IDao;
import org.course.project.model.system.ClientAdmin;
import org.course.project.service.interfaces.IUserService;
import org.course.project.utility.exceptions.ParametersException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("userService")
public final class UserService extends BaseEntityService<ClientAdmin> implements IUserService {

    private final static String USER_NAME = "username";
    private final static String PASSWORD = "password";

    @Autowired
    private IClientAdminDao baseDao;

    @Override
    public ClientAdmin doLogin(final Map<String, String> credentialsMap) throws ParametersException {

        if(credentialsMap.values().size() < 2 || !credentialsMap.containsKey(UserService.USER_NAME) ||
           !credentialsMap.containsKey(UserService.PASSWORD)) {
            throw new ParametersException("UserService, login, incompatible types.");
        }

        final String username = credentialsMap.get(UserService.USER_NAME);
        final String password = credentialsMap.get(UserService.PASSWORD);

        return this.baseDao.getByCredentials(username, password);

    }

    @Override
    protected IDao<ClientAdmin> getBaseDao() {
        return this.baseDao;
    }
}
