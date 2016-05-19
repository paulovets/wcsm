package org.course.project.service.implementations;

import org.course.project.dao.interfaces.IDao;
import org.course.project.service.interfaces.IEntityService;
import org.course.project.utility.exceptions.ParametersException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ReflectionUtils;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

public class BaseEntityService<T> implements IEntityService<T> {

    private final static String LONG_TYPE = "Long";
    public final static String CONFIG_OPTIONS = "configOptions";
    public final static String CONFIG_SCHEMA = "configSchema";

    protected IDao<T> baseDao;

    private Class<T> clazz;

    public BaseEntityService() {
        this.clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public BaseEntityService(final Class<T> clazz, final IDao<T> baseDao) {
        this.clazz = clazz;
        this.baseDao = baseDao;
    }

    @Override
    public List<T> get(final Map<String,String> paramsMap) throws ParametersException {

        if(paramsMap.containsKey("id")) {

            final Long id = Long.valueOf(paramsMap.get("id"));
            return this.getBaseDao().get(id);

        }

        return this.getBaseDao().get(null);
    }

    @Override
    public T remove(final Map<String,String> paramsMap) throws ParametersException {

        if(!paramsMap.containsKey("id")) {
            throw new ParametersException("BaseEntityService, update, incompatible types.");
        }

        final Long id = Long.valueOf(paramsMap.get("id"));

        final List<T> entitiesList = this.getBaseDao().get(id);
        return this.getBaseDao().remove(entitiesList.get(0));

    }

    @Override
    public T persist(final Map<String, String> entityDataMap) throws ParametersException, IOException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {

        final Constructor<T> constructor = this.clazz.getConstructor();
        final T entity = (T)constructor.newInstance();

        for(String fieldName : entityDataMap.keySet()) {

            Field field = ReflectionUtils.findField(this.clazz, fieldName);
            ReflectionUtils.makeAccessible(field);

            Class fieldClass = field.getType();
            Object newValue = entityDataMap.get(fieldName);
            if (fieldClass.getName().indexOf(BaseEntityService.LONG_TYPE) > -1) {
                newValue = Long.valueOf((String)newValue);
            }
            ReflectionUtils.setField(field, entity, newValue);

        }

        return this.getBaseDao().persist(entity);

    }

    @Override
    public T update(final Map<String,String> paramsMap) throws ParametersException {

        if(!paramsMap.containsKey("id")) {
            throw new ParametersException("BaseEntityService, update, incompatible types.");
        }

        final Long id = Long.valueOf(paramsMap.get("id"));
        paramsMap.remove("id");
        return this.getBaseDao().update(paramsMap, id);

    }

    protected IDao<T> getBaseDao() {
        return this.baseDao;
    }
}
