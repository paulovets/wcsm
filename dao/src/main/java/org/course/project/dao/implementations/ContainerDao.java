package org.course.project.dao.implementations;

import org.course.project.dao.interfaces.IContainerDao;
import org.course.project.dao.interfaces.IDao;
import org.course.project.dao.interfaces.IPageDao;
import org.course.project.model.system.Container;
import org.course.project.model.system.ContainerTemplate;
import org.course.project.model.system.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("containerDao")
public final class ContainerDao extends BaseDao<Container> implements IContainerDao {

    @Autowired
    @Qualifier("containerTemplateDao")
    private IDao<ContainerTemplate> containerTemplateDao;

    @Autowired
    private IPageDao pageDao;

    @Override
    public Container persist(final Long containerTemplateId, final Long pageId, final Long optionalContainerId, final Container container) {

        final List<ContainerTemplate> containerTemplateList = this.containerTemplateDao.get(containerTemplateId);
        final List<Page> pageList = this.pageDao.get(pageId);

        if(optionalContainerId != null) {
            final List<Container> containerList = this.get(optionalContainerId);
            containerList.get(0).addContainer(container);
        }

        containerTemplateList.get(0).addContainer(container);
        pageList.get(0).addContainer(container);

        return container;

    }

}
