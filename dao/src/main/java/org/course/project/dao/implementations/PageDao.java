package org.course.project.dao.implementations;

import org.course.project.dao.interfaces.IPageDao;
import org.course.project.dao.interfaces.ISiteDao;
import org.course.project.model.system.Page;
import org.course.project.model.system.Page_;
import org.course.project.model.system.Site;
import org.course.project.model.system.Site_;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository("pageDao")
public final class PageDao extends BaseDao<Page> implements IPageDao {

    @Autowired
    private ISiteDao siteDao;

    @Override
    public Page getPageByType(final Long siteId, final String pageType) {

        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<Page> cq = cb.createQuery(Page.class);
        final Root<Page> page = cq.from(Page.class);
        cq.where(cb.and(cb.equal(page.get(Page_.type), pageType),
                        cb.equal(page.get(Page_.site).get(Site_.id), siteId)));
        final TypedQuery<Page> q = em.createQuery(cq);
        final List<Page> pagesList = q.getResultList();
        return pagesList.size() > 0 ? pagesList.get(0) : new Page();

    }

    @Override
    public Page persist(final Long siteId, final Page page) {

        final List<Site> siteList = this.siteDao.get(siteId);
        siteList.get(0).addPage(page);
        return page;

    }

}
