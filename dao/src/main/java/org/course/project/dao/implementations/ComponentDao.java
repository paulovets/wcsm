package org.course.project.dao.implementations;

import org.course.project.dao.interfaces.IComponentDao;
import org.course.project.dao.interfaces.IContainerDao;
import org.course.project.dao.interfaces.IDao;
import org.course.project.model.system.Component;
import org.course.project.model.system.ComponentTemplate;
import org.course.project.model.system.Container;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("componentDao")
public final class ComponentDao extends BaseDao<Component> implements IComponentDao {

    @Autowired
    @Qualifier("componentTemplateDao")
    private IDao<ComponentTemplate> componentTemplateDao;

    @Autowired
    private IContainerDao containerDao;

    @Override
    public Component persist(final Long componentTemplateId, final Long containerId, final Component component) {

        final List<ComponentTemplate> componentTemplateList = this.componentTemplateDao.get(componentTemplateId);
        final List<Container> containerList = this.containerDao.get(containerId);

        componentTemplateList.get(0).addComponent(component);
        containerList.get(0).addComponent(component);

        return component;

    }
}
