

import dat.daos.impl.GuideDAO;
import dat.entities.Guide;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GuideDAOTest {

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private GuideDAO guideDAO;

    @BeforeEach
    public void setUp() {
        entityManagerFactory = Persistence.createEntityManagerFactory("Trip");
        entityManager = entityManagerFactory.createEntityManager();
        guideDAO = new GuideDAO();
        guideDAO.setEntityManager(entityManager);
    }

    @AfterEach
    public void tearDown() {
        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    public void testCreateGuide() {
        Guide guide = new Guide();
        guide.setFirstName("John");
        guide.setLastName("Doe");
        guide.setEmail("john.doe@example.com");
        guide.setPhone("12345678");
        guide.setYearsOfExperience(5);

        guideDAO.createGuide(guide);

        Guide retrievedGuide = guideDAO.getGuide(guide.getId());
        assertNotNull(retrievedGuide);
        assertEquals("John", retrievedGuide.getFirstName());
    }

    @Test
    public void testGetAllGuides() {
        Guide guide1 = new Guide();
        guide1.setFirstName("Jane");
        guide1.setLastName("Smith");
        guide1.setEmail("jane.smith@example.com");
        guide1.setPhone("87654321");
        guide1.setYearsOfExperience(3);
        guideDAO.createGuide(guide1);

        Guide guide2 = new Guide();
        guide2.setFirstName("Alice");
        guide2.setLastName("Johnson");
        guide2.setEmail("alice.johnson@example.com");
        guide2.setPhone("11223344");
        guide2.setYearsOfExperience(7);
        guideDAO.createGuide(guide2);

        List<Guide> guides = guideDAO.getAllGuides();
        assertEquals(2, guides.size());
    }
}