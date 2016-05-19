package org.course.project.service.implementations;

import org.course.project.dao.interfaces.IDao;
import org.course.project.model.system.AlpacaEntity;
import org.course.project.service.interfaces.IDynamicEntityService;
import org.course.project.utility.exceptions.ParametersException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Map;

@Service("alpacaService")
public final class AlpacaEntityService extends BaseEntityService<AlpacaEntity> {

    private final static String CLASS_NAME = "className";

    @Autowired
    private IDynamicEntityService dynamicEntityService;

    @Autowired
    private IDao<AlpacaEntity> baseDao;

    @Transactional
    @Override
    public AlpacaEntity persist(final Map<String, String> entityDataMap) throws ParametersException, IOException {

        if(entityDataMap.values().size() < 3 || !entityDataMap.containsKey(AlpacaEntityService.CONFIG_OPTIONS) || !entityDataMap.containsKey(AlpacaEntityService.CONFIG_SCHEMA) ||
           !entityDataMap.containsKey(AlpacaEntityService.CLASS_NAME)) {
            throw new ParametersException("AlpacaEntityService, get, incompatible types.");
        }

        final String className = entityDataMap.get(AlpacaEntityService.CLASS_NAME);
        final String configOptions = entityDataMap.get(AlpacaEntityService.CONFIG_OPTIONS);
        final String configSchema = entityDataMap.get(AlpacaEntityService.CONFIG_SCHEMA);

        dynamicEntityService.addType(className, configSchema);

        final AlpacaEntity alpacaEntity = new AlpacaEntity(configSchema, configOptions, className);

        return this.baseDao.persist(alpacaEntity);

    }

    @Override
    protected IDao<AlpacaEntity> getBaseDao() {
        return this.baseDao;
    }
}
