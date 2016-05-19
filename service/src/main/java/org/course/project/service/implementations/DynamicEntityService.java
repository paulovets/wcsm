package org.course.project.service.implementations;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.course.project.dao.interfaces.IDynamicEntityDao;
import org.course.project.model.system.AlpacaEntity;
import org.course.project.service.interfaces.IDynamicEntityService;
import org.course.project.service.interfaces.IEntityService;
import org.course.project.utility.DynamicHelper;
import org.course.project.utility.exceptions.ParametersException;
import org.course.project.utility.json.Alpaca;
import org.eclipse.persistence.dynamic.DynamicClassLoader;
import org.eclipse.persistence.dynamic.DynamicEntity;
import org.eclipse.persistence.jpa.dynamic.JPADynamicHelper;
import org.eclipse.persistence.jpa.dynamic.JPADynamicTypeBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("dynamicService")
public final class DynamicEntityService implements IDynamicEntityService {

    private final static String ALPACA_DATA = "alpacadata";
    private final static String CLASS_NAME = "classname";
    private final static String PARENT_ID = "parentid";
    private final static String ID = "id";

    private final static String SEQUENCE = "DYNAMIC_SEQ";

    @Autowired
    private IDynamicEntityDao baseDao;

    @Autowired
    @Qualifier("alpacaService")
    private IEntityService<AlpacaEntity> alpacaService;

    @Autowired
    private DynamicHelper dynamicHelper;

    @Transactional
    @Override
    public void initDynamicClasses() throws IOException, ParametersException {

        final List<AlpacaEntity> alpacaEntitiesList = this.alpacaService.get(new HashMap<String, String>());
        for(AlpacaEntity alpacaEntity : alpacaEntitiesList) {

            final String className = alpacaEntity.getClassName();

            this.addType(className, alpacaEntity.getConfigSchema());

        }

    }

    @Override
    public void addType(final String className, final String configSchema) throws IOException {

        DynamicClassLoader dClassLoader = this.dynamicHelper.getDCL();
        JPADynamicHelper dHelper = this.dynamicHelper.getJDH();

        Alpaca.FieldsInfo fieldsInfo = new ObjectMapper().readerFor(Alpaca.FieldsInfo.class).readValue(configSchema);

        Class<?> dynamicEntityClass = dClassLoader.createDynamicClass("org.course.project.model.dynamic." + className);
        JPADynamicTypeBuilder newType = new JPADynamicTypeBuilder(dynamicEntityClass, null, className);
        newType.setPrimaryKeyFields("ID");
        newType.addDirectMapping("id", Long.class, "ID");
        newType.configureSequencing(DynamicEntityService.SEQUENCE, "ID");
        for (String fieldName : fieldsInfo.fieldsNamesList) {
            newType.addDirectMapping(fieldName, String.class, fieldName);
        }
        dHelper.addTypes(true, true, newType.getType());

    }

    @Override
    public List<DynamicEntity> getRecordsByParent(final Map<String, String> paramsMap) throws ParametersException {

        if(paramsMap.values().size() < 2 || !paramsMap.containsKey(DynamicEntityService.PARENT_ID) ||
           !paramsMap.containsKey(DynamicEntityService.CLASS_NAME)) {
            throw new ParametersException("DynamicEntityService, getRecordsByParent, incompatible types.");
        }

        final String className = paramsMap.get(DynamicEntityService.CLASS_NAME);
        final String parentId = paramsMap.get(DynamicEntityService.PARENT_ID);

        return this.baseDao.getRecordsByParent(className, parentId);

    }

    @Override
    public DynamicEntity persist(final Map<String, String> entityDataMap) throws ParametersException, IOException {

        if(entityDataMap.values().size() < 2 || !entityDataMap.containsKey(DynamicEntityService.ALPACA_DATA) ||
           !entityDataMap.containsKey(DynamicEntityService.CLASS_NAME)) {
            throw new ParametersException("DynamicEntityService, persist, incompatible types.");
        }

        final String className = entityDataMap.get(DynamicEntityService.CLASS_NAME);
        final String entityJsonData = entityDataMap.get(DynamicEntityService.ALPACA_DATA);

        final JPADynamicHelper dHelper = this.dynamicHelper.getJDH();

        final Alpaca.FieldsData fieldsData = new ObjectMapper().readerFor(Alpaca.FieldsData.class).readValue(entityJsonData);

        final DynamicEntity newDynamicEntity = dHelper.newDynamicEntity("org.course.project.model.dynamic." + className);
        for (String fieldName : fieldsData.fieldToValueMap.keySet()) {
            newDynamicEntity.set(fieldName, fieldsData.fieldToValueMap.get(fieldName));
        }
        return this.baseDao.persist(newDynamicEntity);

    }

    @Override
    public DynamicEntity get(final Map<String, String> paramsMap) throws ParametersException {

        if(paramsMap.values().size() < 2 || !paramsMap.containsKey(DynamicEntityService.ID) ||
           !paramsMap.containsKey(DynamicEntityService.CLASS_NAME)) {
            throw new ParametersException("DynamicEntityService, get, incompatible types.");
        }

        final String className = paramsMap.get(DynamicEntityService.CLASS_NAME);
        final Long id = Long.valueOf(paramsMap.get(DynamicEntityService.ID));

        return this.baseDao.get(className, id);

    }

    @Override
    public DynamicEntity remove(final Map<String,String> paramsMap) throws ParametersException {

        if(paramsMap.values().size() < 2 || !paramsMap.containsKey(DynamicEntityService.ID) ||
           !paramsMap.containsKey(DynamicEntityService.CLASS_NAME)) {
            throw new ParametersException("DynamicEntityService, get, incompatible types.");
        }

        final String className = paramsMap.get(DynamicEntityService.CLASS_NAME);
        final Long id = Long.valueOf(paramsMap.get(DynamicEntityService.ID));

        final DynamicEntity dynamicEntity = this.baseDao.get(className, id);
        return this.baseDao.remove(dynamicEntity);

    }

    @Override
    public DynamicEntity update(final Map<String, String> paramsMap) throws ParametersException {

        if(paramsMap.values().size() < 3 || !paramsMap.containsKey(DynamicEntityService.ID) ||
           !paramsMap.containsKey(DynamicEntityService.CLASS_NAME)) {
            throw new ParametersException("DynamicEntityService, update, incompatible types.");
        }

        final String className = paramsMap.get(DynamicEntityService.CLASS_NAME);
        paramsMap.remove(DynamicEntityService.CLASS_NAME);
        final Long id = Long.valueOf(paramsMap.get(DynamicEntityService.ID));
        paramsMap.remove(DynamicEntityService.ID);

        return this.baseDao.update(className, paramsMap, id);

    }

}
