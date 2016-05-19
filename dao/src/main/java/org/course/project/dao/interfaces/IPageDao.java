package org.course.project.dao.interfaces;

import org.course.project.model.system.Page;

public interface IPageDao extends IDao<Page> {

    Page getPageByType(final Long siteId, final String pageType);
    Page persist(final Long siteId, final Page page);

}
