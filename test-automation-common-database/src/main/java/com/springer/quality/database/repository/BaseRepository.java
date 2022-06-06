package com.springer.quality.database.repository;

import com.springer.quality.database.model.DatabaseConfig;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Slf4j
public abstract class BaseRepository {
    private static final Map<DatabaseConfig, EntityManagerFactory> ENTITY_MANAGER_FACTORY_MAP = new HashMap<>();

    protected <U> U execute(Function<EntityManager, U> function) {
        EntityManagerFactory emf = ENTITY_MANAGER_FACTORY_MAP.computeIfAbsent(getConfig(),
                k -> EntityManagerFactoryProvider.getEntityManager(getConfig()));
        EntityManager manager = null;
        try {
            manager = emf.createEntityManager();
            return function.apply(manager);
        } finally {
            if (manager != null) {
                if (manager.getTransaction().isActive()) {
                    log.error("Database rollback due to some active transaction, commit your transaction");
                    manager.getTransaction().rollback();
                }
                manager.close();
            }
        }

    }

    protected abstract DatabaseConfig getConfig();
}
