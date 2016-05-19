package org.course.project.utility;

import org.course.project.dao.implementations.BaseDao;
import org.course.project.model.system.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
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
}
