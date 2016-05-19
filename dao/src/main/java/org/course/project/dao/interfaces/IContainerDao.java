package org.course.project.dao.interfaces;

import org.course.project.model.system.Container;

public interface IContainerDao extends IDao<Container> {
    Container persist(final Long containerTemplateId, final Long pageId, final Long optionalContainerId, final Container container);
}
