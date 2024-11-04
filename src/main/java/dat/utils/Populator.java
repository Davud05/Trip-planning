package dat.utils;

import dat.config.HibernateConfig;
import dat.entities.Guide;
import dat.entities.Trip;
import dat.entities.TripCategory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

public class Populator {
    private static final Logger LOGGER = LoggerFactory.getLogger(Populator.class);
    private final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();

    public void populate() {
        EntityManager em = emf.createEntityManager();
        try {
            LOGGER.info("Starting database population");
            em.getTransaction().begin();

            // Create guides
            Guide guide1 = new Guide("John", "Doe", "john@example.com", "12345678", 5);
            Guide guide2 = new Guide("Jane", "Smith", "jane@example.com", "87654321", 3);

            em.persist(guide1);
            em.persist(guide2);
            LOGGER.info("Guides created successfully");

            // Create trips
            Trip trip1 = new Trip(
                    "Beach Adventure",
                    TripCategory.BEACH,
                    LocalDateTime.now().plusDays(1),
                    LocalDateTime.now().plusDays(1).plusHours(4),
                    12.5683,
                    55.6761,
                    299.99
            );
            trip1.setGuide(guide1);

            Trip trip2 = new Trip(
                    "Lake Experience",
                    TripCategory.LAKE,
                    LocalDateTime.now().plusDays(2),
                    LocalDateTime.now().plusDays(2).plusHours(6),
                    12.6683,
                    55.7761,
                    399.99
            );
            trip2.setGuide(guide1);

            Trip trip3 = new Trip(
                    "City Tour",
                    TripCategory.CITY,
                    LocalDateTime.now().plusDays(3),
                    LocalDateTime.now().plusDays(3).plusHours(3),
                    12.5683,
                    55.6761,
                    199.99
            );
            trip3.setGuide(guide2);

            em.persist(trip1);
            em.persist(trip2);
            em.persist(trip3);

            em.getTransaction().commit();
            LOGGER.info("Database populated successfully");
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            LOGGER.error("Error populating database", e);
            throw e;
        } finally {
            em.close();
        }
    }
}