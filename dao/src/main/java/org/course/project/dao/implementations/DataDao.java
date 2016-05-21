package org.course.project.dao.implementations;

import org.course.project.dao.interfaces.IComponentDao;
import org.course.project.dao.interfaces.IDataDao;
import org.course.project.model.system.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("dataDao")
public final class DataDao extends BaseDao<Data> implements IDataDao {

    @Autowired
    private IComponentDao componentDao;

    @Override
    public Data persist(final Long componentId, final Data data) {

        final List<Component> componentList = this.componentDao.get(componentId);
        componentList.get(0).setData(data);
        return data;

    }

}
