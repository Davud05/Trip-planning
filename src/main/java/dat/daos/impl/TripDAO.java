package dat.daos.impl;

import dat.config.HibernateConfig;
import dat.daos.IDAO;
import dat.daos.ITripGuideDAO;
import dat.dtos.TripDTO;
import dat.entities.Trip;
import dat.entities.Guide;
import dat.utils.DTOMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TripDAO implements IDAO<TripDTO, Long>, ITripGuideDAO {

    @Override
    public TripDTO create(TripDTO tripDTO) {
        EntityManager em = HibernateConfig.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            Trip trip = DTOMapper.toTripEntity(tripDTO);
            em.persist(trip);
            transaction.commit();
            return DTOMapper.toTripDTO(trip);
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public List<TripDTO> getAll() {
        EntityManager em = HibernateConfig.getEntityManagerFactory().createEntityManager();
        try {
            List<Trip> trips = em.createQuery("SELECT t FROM Trip t", Trip.class).getResultList();
            return trips.stream()
                    .map(DTOMapper::toTripDTO)
                    .toList();
        } finally {
            em.close();
        }
    }

    @Override
    public TripDTO getById(Long id) {
        EntityManager em = HibernateConfig.getEntityManagerFactory().createEntityManager();
        try {
            Trip trip = em.find(Trip.class, id);
            return DTOMapper.toTripDTO(trip);
        } finally {
            em.close();
        }
    }

    @Override
    public TripDTO update(TripDTO tripDTO) {
        EntityManager em = HibernateConfig.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            Trip trip = DTOMapper.toTripEntity(tripDTO);
            Trip updatedTrip = em.merge(trip);
            transaction.commit();
            return DTOMapper.toTripDTO(updatedTrip);
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public boolean delete(Long id) {
        EntityManager em = HibernateConfig.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            Trip trip = em.find(Trip.class, id);
            if (trip != null) {
                em.remove(trip);
                transaction.commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void addGuideToTrip(Long tripId, Long guideId) {
        EntityManager em = HibernateConfig.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();

            Trip trip = em.find(Trip.class, tripId);
            Guide guide = em.find(Guide.class, guideId);

            if (trip == null || guide == null) {
                throw new IllegalArgumentException("Trip eller Guide findes ikke");
            }

            trip.setGuide(guide);
            guide.getTrips().add(trip);

            em.merge(trip);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public Set<TripDTO> getTripsByGuide(Long guideId) {
        EntityManager em = HibernateConfig.getEntityManagerFactory().createEntityManager();
        try {
            List<Trip> trips = em.createQuery(
                            "SELECT t FROM Trip t WHERE t.guide.id = :guideId",
                            Trip.class)
                    .setParameter("guideId", guideId)
                    .getResultList();

            return new HashSet<>(trips.stream()
                    .map(DTOMapper::toTripDTO)
                    .toList());
        } finally {
            em.close();
        }
    }
}