package org.course.project.dao.interfaces;

import org.course.project.model.system.Component;

public interface IComponentDao extends IDao<Component> {
    Component persist(final Long componentTemplateId, final Long containerId, final Component component);
}
