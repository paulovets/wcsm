package org.course.project.service.implementations;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.course.project.dao.interfaces.IDao;
import org.course.project.dao.interfaces.IPageDao;

import org.course.project.model.system.*;
import org.course.project.service.interfaces.IDynamicEntityService;
import org.course.project.service.interfaces.IPageService;
import org.course.project.utility.exceptions.ParametersException;
import org.course.project.utility.json.Alpaca;
import org.course.project.utility.json.Config;
import org.eclipse.persistence.dynamic.DynamicEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("pageService")
public final class PageService extends BaseEntityService<Page> implements IPageService {

    private final static String TYPE = "type";
    private final static String SITE_ID = "siteid";

    private final static String CLASS_NAME = "classname";
    private final static String PARENT_ID = "parentid";

    private final static String PAGE_ID = "pageid";

    @Autowired
    private IPageDao baseDao;

    @Autowired
    private IDynamicEntityService dynamicEntityService;

    @Transactional
    @Override
    public String getPageConfig(final Map<String,String> paramsMap) throws ParametersException, IOException {

        if(paramsMap.values().size() < 1 || !paramsMap.containsKey(PageService.PAGE_ID)) {
            throw new ParametersException("PageService, getPageConfig, incompatible types.");
        }

        final Long pageId = Long.valueOf(paramsMap.get(PageService.PAGE_ID));
        final List<Page> pageList = this.baseDao.get(pageId);
        if(pageList.size() == 0) {
            return "";
        }
        final Page page = pageList.get(0);

        final Config.PageConfig pageConfig = new Config.PageConfig(page.getConfigOptions(), page.getConfigSchema(), page.getId());
        for (Container container : page.getContainers()) {

            Config.TemplateConfig templateConfig = new Config.TemplateConfig(
                    container.getContainerTemplate().getConfigOptions(),
                    container.getContainerTemplate().getConfigSchema(),
                    container.getConfigOptions(),
                    container.getConfigSchema(),
                    container.getId()
            );
            this.checkNestedTemplates(page, container, templateConfig);
            pageConfig.templatesList.add(templateConfig);

        }

        return new ObjectMapper().writeValueAsString(pageConfig);

    }

    private void checkNestedTemplates(final Page page, final Container container, final Config.TemplateConfig templateConfig) throws ParametersException, IOException {

        if (container.getComponents().size() > 0) {

            for(Component component : container.getComponents()) {

                Data data = component.getData();
                if(data == null) {
                    Config.ComponentConfig componentConfig = new Config.ComponentConfig(
                            component.getComponentTemplate().getConfigOptions(),
                            component.getComponentTemplate().getConfigSchema(),
                            component.getConfigOptions(),
                            component.getConfigSchema(),
                            "",
                            "",
                            component.getId()
                    );
                    continue;
                }

                AlpacaEntity alpacaEntity = data.getAlpacaEntity();
                String alpacaOptions = alpacaEntity.getConfigOptions();
                String alpacaSchema = alpacaEntity.getConfigSchema();

                Config.ComponentConfig componentConfig = new Config.ComponentConfig(
                        component.getComponentTemplate().getConfigOptions(),
                        component.getComponentTemplate().getConfigSchema(),
                        component.getConfigOptions(),
                        component.getConfigSchema(),
                        alpacaOptions,
                        alpacaSchema,
                        component.getId()
                );
                templateConfig.componentsList.add(componentConfig);

                Alpaca.FieldsInfo fieldsInfo = new ObjectMapper().readerFor(Alpaca.FieldsInfo.class).readValue(alpacaEntity.getConfigSchema());

                String className = data.getRelatedEntityName();
                Long parentId = data.getId();

                Map<String,String> paramsMap = new HashMap<String,String>();
                paramsMap.put(PageService.CLASS_NAME, className);
                paramsMap.put(PageService.PARENT_ID, parentId.toString());

                List<DynamicEntity> dynamicEntitiesList = this.dynamicEntityService.getRecordsByParent(paramsMap);

                for (DynamicEntity dynamicEntity : dynamicEntitiesList) {

                    Map<String,String> nameToValueMap = new HashMap<String,String>();
                    nameToValueMap.put("id", String.valueOf(dynamicEntity.get("id")));
                    for(String fieldName : fieldsInfo.fieldsNamesList) {

                        String fieldValue = dynamicEntity.get(fieldName);
                        nameToValueMap.put(fieldName, fieldValue);

                    }

                    String jsonEntity = new ObjectMapper().writeValueAsString(nameToValueMap);
                    componentConfig.datasList.add(jsonEntity);

                }

            }

        }

        for(Container nestedContainer : container.getContainers()) {

            Config.TemplateConfig nestedTemplateConfig = new Config.TemplateConfig(
                    nestedContainer.getContainerTemplate().getConfigOptions(),
                    nestedContainer.getContainerTemplate().getConfigSchema(),
                    nestedContainer.getConfigOptions(),
                    nestedContainer.getConfigSchema(),
                    nestedContainer.getId()
            );
            templateConfig.templatesList.add(nestedTemplateConfig);
            this.checkNestedTemplates(page, nestedContainer, nestedTemplateConfig);

        }

    }

    @Transactional
    @Override
    public Page persist(final Map<String, String> entityDataMap) throws ParametersException {

        if(entityDataMap.values().size() < 4 || !entityDataMap.containsKey(PageService.CONFIG_OPTIONS) ||
           !entityDataMap.containsKey(PageService.CONFIG_SCHEMA) || !entityDataMap.containsKey(PageService.TYPE) ||
           !entityDataMap.containsKey(PageService.SITE_ID)) {
            throw new ParametersException("PageService, persist, incompatible types.");
        }

        final String pageType = entityDataMap.get(PageService.TYPE);
        final String configOptions = entityDataMap.get(PageService.CONFIG_OPTIONS);
        final String configSchema = entityDataMap.get(PageService.CONFIG_SCHEMA);
        final Long siteId = Long.valueOf(entityDataMap.get(PageService.SITE_ID));

        final Page page = new Page(pageType, configSchema, configOptions);

        return this.baseDao.persist(siteId, page);

    }

    @Override
    public Page getPageByType(final Map<String, String> paramsMap) throws ParametersException {

        if(paramsMap.values().size() < 2 || !paramsMap.containsKey(PageService.TYPE) ||
           !paramsMap.containsKey(PageService.SITE_ID)) {
            throw new ParametersException("SiteService, persist, incompatible types.");
        }

        final Long siteId = Long.valueOf(paramsMap.get(PageService.SITE_ID));
        final String pageType = paramsMap.get(PageService.TYPE);

        return this.baseDao.getPageByType(siteId, pageType);

    }

    @Override
    protected IDao<Page> getBaseDao() {
        return this.baseDao;
    }
}
