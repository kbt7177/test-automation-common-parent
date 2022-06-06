package com.springer.quality.database.repository;

import com.microsoft.sqlserver.jdbc.SQLServerDriver;
import com.springer.quality.database.model.DatabaseConfig;
import org.hibernate.dialect.SQLServer2012Dialect;
import org.hibernate.jpa.HibernatePersistenceProvider;

import javax.persistence.EntityManagerFactory;
import javax.persistence.SharedCacheMode;
import javax.persistence.ValidationMode;
import javax.persistence.spi.ClassTransformer;
import javax.persistence.spi.PersistenceUnitInfo;
import javax.persistence.spi.PersistenceUnitTransactionType;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.*;

import static org.hibernate.cfg.AvailableSettings.*;


public class EntityManagerFactoryProvider {
    private EntityManagerFactoryProvider() {
        // Static object
    }

    public static EntityManagerFactory getEntityManager(DatabaseConfig database) {
        Map<String, Object> settings = new HashMap<>();
        settings.put(JPA_JDBC_DRIVER, SQLServerDriver.class.getName());
        settings.put(JPA_JDBC_URL, database.getJdbcUrl());
        settings.put(USER, database.getUsername());
        settings.put(PASS, database.getPassword());
        settings.put(DIALECT, SQLServer2012Dialect.class);
        settings.put(SHOW_SQL, false);
        settings.put(QUERY_STARTUP_CHECKING, false);
        settings.put(GENERATE_STATISTICS, false);
        settings.put(USE_REFLECTION_OPTIMIZER, false);
        settings.put(USE_SECOND_LEVEL_CACHE, false);
        settings.put(USE_QUERY_CACHE, false);
        settings.put(USE_STRUCTURED_CACHE, false);
        settings.put(STATEMENT_BATCH_SIZE, 20);

        return new HibernatePersistenceProvider().createContainerEntityManagerFactory(
                unitInfo(String.valueOf(database.getJdbcUrl())), settings);
    }

    private static PersistenceUnitInfo unitInfo(String unit) {
        return new PersistenceUnitInfo() {
            @Override
            public String getPersistenceUnitName() {
                return unit;
            }

            @Override
            public String getPersistenceProviderClassName() {
                return "org.hibernate.jpa.HibernatePersistenceProvider";
            }

            @Override
            public PersistenceUnitTransactionType getTransactionType() {
                return PersistenceUnitTransactionType.RESOURCE_LOCAL;
            }

            @Override
            public DataSource getJtaDataSource() {
                return null;
            }

            @Override
            public DataSource getNonJtaDataSource() {
                return null;
            }

            @Override
            public List<String> getMappingFileNames() {
                return Collections.emptyList();
            }

            @Override
            public List<java.net.URL> getJarFileUrls() {
                try {
                    return Collections.list(this.getClass().getClassLoader().getResources(""));
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
            }

            @Override
            public java.net.URL getPersistenceUnitRootUrl() {
                return null;
            }

            @Override
            public List<String> getManagedClassNames() {
                return Collections.emptyList();
            }

            @Override
            public boolean excludeUnlistedClasses() {
                return false;
            }

            @Override
            public SharedCacheMode getSharedCacheMode() {
                return null;
            }

            @Override
            public ValidationMode getValidationMode() {
                return null;
            }

            @Override
            public Properties getProperties() {
                return new Properties();
            }

            @Override
            public String getPersistenceXMLSchemaVersion() {
                return null;
            }

            @Override
            public ClassLoader getClassLoader() {
                return null;
            }

            @Override
            public void addTransformer(ClassTransformer classTransformer) {
            }

            @Override
            public ClassLoader getNewTempClassLoader() {
                return null;
            }
        };
    }
}
