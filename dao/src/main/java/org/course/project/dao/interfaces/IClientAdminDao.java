package org.course.project.dao.interfaces;

import org.course.project.model.system.ClientAdmin;


public interface IClientAdminDao extends IDao<ClientAdmin> {
    ClientAdmin getByCredentials(final String username, final String password);
}
