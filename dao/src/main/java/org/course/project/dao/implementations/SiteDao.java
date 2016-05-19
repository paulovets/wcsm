package org.course.project.dao.implementations;

import org.course.project.dao.interfaces.IClientAdminDao;
import org.course.project.dao.interfaces.ISiteDao;
import org.course.project.model.system.ClientAdmin;
import org.course.project.model.system.Site;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("siteDao")
public final class SiteDao extends BaseDao<Site> implements ISiteDao {

    @Autowired
    private IClientAdminDao clientAdminDao;

    @Override
    public Site persist(final Long clientAdminId, final Site site) {

        final List<ClientAdmin> clientAdminList = this.clientAdminDao.get(clientAdminId);
        clientAdminList.get(0).addSite(site);
        return site;

    }

}
