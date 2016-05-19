package org.course.project.service.implementations;

import org.course.project.dao.interfaces.IContainerDao;
import org.course.project.dao.interfaces.IDao;
import org.course.project.model.system.Container;
import org.course.project.utility.exceptions.ParametersException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("containerService")
public final class TemplateService extends BaseEntityService<Container> {

    private final static String PAGE_ID = "pageid";
    private final static String CONTAINER_ID = "containerid";
    private final static String TEMPLATE_ID = "containertemplateid";

    @Autowired
    private IContainerDao baseDao;

    @Transactional
    @Override
    public Container persist(final Map<String, String> entityDataMap) throws ParametersException {

        if(entityDataMap.values().size() < 3 || !entityDataMap.containsKey(TemplateService.CONFIG_OPTIONS) ||
           !entityDataMap.containsKey(TemplateService.CONFIG_SCHEMA) || !entityDataMap.containsKey(TemplateService.PAGE_ID) ||
           !entityDataMap.containsKey(TemplateService.TEMPLATE_ID)) {
            throw new ParametersException("TemplateService, persist, incompatible types.");
        }

        final String configOptions = entityDataMap.get(TemplateService.CONFIG_OPTIONS);
        final String configSchema = entityDataMap.get(TemplateService.CONFIG_SCHEMA);

        final Container container = new Container(configSchema, configOptions);

        final Long pageId = Long.valueOf(entityDataMap.get(TemplateService.PAGE_ID));
        final Long templateId = Long.valueOf(entityDataMap.get(TemplateService.TEMPLATE_ID));

        Long containerId = null;
        if (entityDataMap.containsKey(TemplateService.CONTAINER_ID)) {
            containerId = Long.valueOf(entityDataMap.get(TemplateService.CONTAINER_ID));
        }

        return this.baseDao.persist(templateId, pageId, containerId, container);

    }

    @Override
    protected IDao<Container> getBaseDao() {
        return this.baseDao;
    }
}
