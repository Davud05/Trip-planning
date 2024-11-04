// src/main/java/dat/test/HibernateTest.java

import dat.config.HibernateConfig;
import dat.entities.Guide;
import dat.entities.Trip;
import dat.entities.TripCategory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

import java.time.LocalDateTime;

public class HibernateTest {

    public static void main(String[] args) {
        // Opret EntityManagerFactory
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();

        // Opret EntityManager
        EntityManager em = emf.createEntityManager();

        // Test oprettelse af en Guide og en Trip
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        try {
            // Opret en Guide
            Guide guide = new Guide();
            guide.setFirstName("John");
            guide.setLastName("Doe");
            guide.setEmail("john.doe@example.com");
            guide.setPhone("12345678");
            guide.setYearsOfExperience(5);

            // Gem guiden først
            em.persist(guide);

            // Opret en Trip
            Trip trip = new Trip();
            trip.setStartTime(LocalDateTime.now());
            trip.setEndTime(LocalDateTime.now().plusDays(1));
            trip.setLongitude(12.5683); // Eksempel på longitude
            trip.setLatitude(55.6761);   // Eksempel på latitude
            trip.setName("Copenhagen City Tour");
            trip.setPrice(100.0);
            trip.setCategory("CITY");
            trip.setGuide(guide); // Sæt guiden for turen

            // Gem turen
            em.persist(trip);

            transaction.commit();
            System.out.println("Guide and Trip created successfully!");
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            em.close(); // Luk EntityManager
        }

        // Hent og vis Guide og Trip
        em = emf.createEntityManager(); // Opret en ny EntityManager for at hente data
        try {
            Guide foundGuide = em.find(Guide.class, 1L); // Antag at ID 1 er den første guide
            System.out.println("Found Guide: " + foundGuide.getFirstName() + " " + foundGuide.getLastName());

            // Hent ture for guiden
            for (Trip t : foundGuide.getTrips()) {
                System.out.println("Trip: " + t.getName() + ", Category: " + t.getCategory());
            }
        } finally {
            em.close(); // Luk EntityManager
            emf.close(); // Luk EntityManagerFactory
        }
    }
}