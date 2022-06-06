package com.springer.quality.database.repository;


import com.springer.quality.database.config.MySampleDbConfig;
import com.springer.quality.database.entities.CaItemEntity;
import com.springer.quality.database.item.DatabaseItem;
import com.springer.quality.database.model.DatabaseConfig;

import javax.persistence.TypedQuery;

public class SampleRepository extends BaseRepository {

    public CaItemEntity findItemId(String contentId) {
        return execute(entityManager -> {
            TypedQuery<CaItemEntity> query = entityManager.createNamedQuery("CaItemEntity.findItem", CaItemEntity.class);
            query.setParameter("contentId", contentId);
            return query.getSingleResult();
        });
    }

    @Override
    protected DatabaseConfig getConfig() {
        return DatabaseItem.instance().getConfig(MySampleDbConfig.class).getSampleDatabase();
    }
}
