package org.course.project.service.implementations;

import org.course.project.dao.interfaces.IDao;
import org.course.project.dao.interfaces.ISiteDao;
import org.course.project.model.system.Site;
import org.course.project.utility.exceptions.ParametersException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("siteService")
public final class SiteService extends BaseEntityService<Site> {

    private final static String NAME = "name";
    private final static String ADMIN_ID = "adminid";

    @Autowired
    private ISiteDao baseDao;

    @Transactional
    @Override
    public Site persist(final Map<String, String> entityDataMap) throws ParametersException {

        if(entityDataMap.values().size() < 4 || !entityDataMap.containsKey(SiteService.CONFIG_OPTIONS) ||
           !entityDataMap.containsKey(SiteService.CONFIG_SCHEMA) || !entityDataMap.containsKey(SiteService.NAME) ||
           !entityDataMap.containsKey(SiteService.ADMIN_ID)) {
            throw new ParametersException("SiteService, persist, incompatible types.");
        }

        final String name = entityDataMap.get(SiteService.NAME);
        final String configOptions = entityDataMap.get(SiteService.CONFIG_OPTIONS);
        final String configSchema = entityDataMap.get(SiteService.CONFIG_SCHEMA);
        final Long adminId = Long.valueOf(entityDataMap.get(SiteService.ADMIN_ID));

        final Site site = new Site(name, configSchema, configOptions);

        return this.baseDao.persist(adminId, site);

    }

    @Override
    protected IDao<Site> getBaseDao() {
        return this.baseDao;
    }
}
