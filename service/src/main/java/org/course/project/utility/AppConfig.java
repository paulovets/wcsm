package org.course.project.utility;

import org.course.project.dao.implementations.BaseDao;
import org.course.project.dao.interfaces.IDao;
import org.course.project.model.system.AlpacaEntity;
import org.course.project.model.system.ComponentTemplate;
import org.course.project.model.system.ContainerTemplate;
import org.course.project.service.implementations.BaseEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Autowired
    private ApplicationContext appContext;

    @Bean(name = "componentTemplateDao")
    public BaseDao<ComponentTemplate> componentTemplateDao() {
        return new BaseDao<ComponentTemplate>(ComponentTemplate.class);
    }

    @Bean(name = "containerTemplateDao")
    public BaseDao<ContainerTemplate> containerTemplateDao() {
        return new BaseDao<ContainerTemplate>(ContainerTemplate.class);
    }

    @Bean(name = "alpacaDao")
    public BaseDao<AlpacaEntity> alpacaEntityDao() {
        return new BaseDao<AlpacaEntity>(AlpacaEntity.class);
    }

    @Bean(name = "componentTemplateService")
    public BaseEntityService<ComponentTemplate> componentTemplateService() {
        final IDao<ComponentTemplate> componentTemplateDao = (IDao<ComponentTemplate>)appContext.getBean("componentTemplateDao");
        return new BaseEntityService<ComponentTemplate>(ComponentTemplate.class, componentTemplateDao);
    }

    @Bean(name = "containerTemplateService")
    public BaseEntityService<ContainerTemplate> containerTemplateService() {
        final IDao<ContainerTemplate> containerTemplateDao = (IDao<ContainerTemplate>)appContext.getBean("containerTemplateDao");
        return new BaseEntityService<ContainerTemplate>(ContainerTemplate.class, containerTemplateDao);
    }
}
