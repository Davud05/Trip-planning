

import dat.daos.impl.TripDAO;
import dat.entities.Trip;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TripDAOTest {

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private TripDAO tripDAO;

    @BeforeEach
    public void setUp() {
        entityManagerFactory = Persistence.createEntityManagerFactory("Trip");
        entityManager = entityManagerFactory.createEntityManager();
        tripDAO = new TripDAO();
        tripDAO.setEntityManager(entityManager);
    }

    @AfterEach
    public void tearDown() {
        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    public void testCreateTrip() {
        Trip trip = new Trip();
        trip.setStartTime(LocalDateTime.now());
        trip.setEndTime(LocalDateTime.now().plusDays(1));
        trip.setLongitude(12.34);
        trip.setLatitude(56.78);
        trip.setName("Paris Adventure");
        trip.setPrice(199.99);
        trip.setCategory("city");

        tripDAO.createTrip(trip);

        Trip retrievedTrip = tripDAO.getTrip(trip.getId());
        assertNotNull(retrievedTrip);
        assertEquals("Paris Adventure", retrievedTrip.getName());
    }

    @Test
    public void testGetAllTrips() {
        Trip trip1 = new Trip();
        trip1.setStartTime(LocalDateTime.now());
        trip1.setEndTime(LocalDateTime.now().plusDays(1));
        trip1.setLongitude(12.34);
        trip1.setLatitude(56.78);
        trip1.setName("London Tour");
        trip1.setPrice(150.00);
        trip1.setCategory("city");
        tripDAO.createTrip(trip1);

        Trip trip2 = new Trip();
        trip2.setStartTime(LocalDateTime.now());
        trip2.setEndTime(LocalDateTime.now().plusDays(2));
        trip2.setLongitude(23.45);
        trip2.setLatitude(67.89);
        trip2.setName("Beach Getaway");
        trip2.setPrice(250.00);
        trip2.setCategory("beach");
        tripDAO.createTrip(trip2);

        List<Trip> trips = tripDAO.getAllTrips();
        assertEquals(2, trips.size());
    }
}