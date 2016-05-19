package org.course.project.dao.interfaces;

import org.course.project.model.system.Site;

public interface ISiteDao extends IDao<Site> {
    Site persist(final Long clientAdminId, final Site site);
}
