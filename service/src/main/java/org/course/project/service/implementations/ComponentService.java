package org.course.project.service.implementations;

import org.course.project.dao.interfaces.IComponentDao;

import org.course.project.dao.interfaces.IDao;
import org.course.project.model.system.Component;
import org.course.project.utility.exceptions.ParametersException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("componentService")
public final class ComponentService extends BaseEntityService<Component> {

    private final static String CONTAINER_ID = "containerid";
    private final static String TEMPLATE_ID = "componenttemplateid";

    @Autowired
    private IComponentDao baseDao;

    @Transactional
    @Override
    public Component persist(final Map<String, String> entityDataMap) throws ParametersException {

        if(entityDataMap.values().size() < 4 || !entityDataMap.containsKey(ComponentService.CONFIG_OPTIONS) ||
           !entityDataMap.containsKey(ComponentService.CONFIG_SCHEMA) || !entityDataMap.containsKey(ComponentService.TEMPLATE_ID) ||
           !entityDataMap.containsKey(ComponentService.CONTAINER_ID)) {
            throw new ParametersException("SiteService, persist, incompatible types.");
        }

        final String configOptions = entityDataMap.get(ComponentService.CONFIG_OPTIONS);
        final String configSchema = entityDataMap.get(ComponentService.CONFIG_SCHEMA);

        final Component component = new Component(configSchema, configOptions);

        final Long containerId = Long.valueOf(entityDataMap.get(ComponentService.CONTAINER_ID));
        final Long templateId = Long.valueOf(entityDataMap.get(ComponentService.TEMPLATE_ID));

        return this.baseDao.persist(templateId, containerId, component);

    }

    @Override
    protected IDao<Component> getBaseDao() {
        return this.baseDao;
    }
}
