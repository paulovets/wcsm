package org.course.project.dao.implementations;

import org.course.project.dao.interfaces.IClientAdminDao;
import org.course.project.model.system.ClientAdmin;
import org.course.project.model.system.ClientAdmin_;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository("clientAdminDao")
public final class ClientAdminDao extends BaseDao<ClientAdmin> implements IClientAdminDao {

    @Override
    public ClientAdmin getByCredentials(final String username, final String password) {

        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<ClientAdmin> cq = cb.createQuery(ClientAdmin.class);
        final Root<ClientAdmin> clientAdmin = cq.from(ClientAdmin.class);
        cq.where(cb.and(cb.equal(clientAdmin.get(ClientAdmin_.username), username),
                        cb.equal(clientAdmin.get(ClientAdmin_.password), password)));
        final TypedQuery<ClientAdmin> q = em.createQuery(cq);

        return q.getResultList().size() > 0 ? q.getResultList().get(0) : new ClientAdmin();

    }

}
