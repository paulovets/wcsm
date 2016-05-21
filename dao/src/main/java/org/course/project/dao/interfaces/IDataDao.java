package org.course.project.dao.interfaces;

import org.course.project.model.system.Data;

import java.util.List;

public interface IDataDao extends IDao<Data> {
    Data persist(final Long componentId, final Data data);
}
