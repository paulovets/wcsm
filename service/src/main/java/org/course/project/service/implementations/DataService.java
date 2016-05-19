package org.course.project.service.implementations;

import org.course.project.dao.interfaces.IDao;
import org.course.project.dao.interfaces.IDataDao;
import org.course.project.model.system.AlpacaEntity;
import org.course.project.model.system.Data;
import org.course.project.service.interfaces.IDataService;
import org.course.project.service.interfaces.IEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.course.project.utility.exceptions.ParametersException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("dataService")
public final class DataService extends BaseEntityService<Data> implements IDataService {

    private final static String ALPACA_ID = "alpacaid";
    private final static String COMPONENT_ID = "componentid";
    private final static String CLASS_NAME = "classname";

    @Autowired
    private IDataDao baseDao;

    @Autowired
    @Qualifier("alpacaService")
    private IEntityService<AlpacaEntity> alpacaService;

    @Override
    public List<Data> getByParents(final Map<String, String> paramsMap) throws ParametersException {

        if(paramsMap.values().size() < 1 ||
           !paramsMap.containsKey(DataService.COMPONENT_ID)) {
            throw new ParametersException("DataService, get, incompatible types.");
        }

        final Long componentId = Long.valueOf(paramsMap.get(DataService.COMPONENT_ID));

        return this.baseDao.getByParent(componentId);

    }

    @Transactional
    @Override
    public Data persist(final Map<String, String> entityDataMap) throws ParametersException {

        if(entityDataMap.values().size() < 3 || !entityDataMap.containsKey(DataService.ALPACA_ID) ||
           !entityDataMap.containsKey(DataService.COMPONENT_ID) ||
           !entityDataMap.containsKey(DataService.CLASS_NAME)) {
            throw new ParametersException("DataService, persist, incompatible types.");
        }

        final Long componentId = Long.valueOf(entityDataMap.get(DataService.COMPONENT_ID));

        final Map<String, String> paramsMap = new HashMap<String,String>();
        final String alpacaEntityId = entityDataMap.get(DataService.ALPACA_ID);
        paramsMap.put("id", alpacaEntityId);
        final List<AlpacaEntity> alpacaEntitiesList = this.alpacaService.get(paramsMap);

        final String relatedEntityName = entityDataMap.get(DataService.CLASS_NAME);

        final Data data = new Data(alpacaEntitiesList.get(0), relatedEntityName);

        return this.baseDao.persist(componentId, data);

    }

    @Override
    protected IDao<Data> getBaseDao() {
        return this.baseDao;
    }
}
