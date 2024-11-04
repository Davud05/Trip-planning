package dat.config;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HibernateConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(HibernateConfig.class);
    private static EntityManagerFactory emf;

    public static EntityManagerFactory getEntityManagerFactory() {
        if (emf == null) {
            try {
                LOGGER.info("Building EntityManagerFactory");
                emf = Persistence.createEntityManagerFactory("trips");
                LOGGER.info("EntityManagerFactory built successfully");
            } catch (Exception e) {
                LOGGER.error("Error building EntityManagerFactory", e);
                throw e;
            }
        }
        return emf;
    }
}