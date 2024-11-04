package dat.daos.impl;

import dat.config.HibernateConfig;
import dat.daos.IDAO;
import dat.dtos.GuideDTO;
import dat.entities.Guide;
import dat.utils.DTOMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;

public class GuideDAO implements IDAO<GuideDTO, Long> {

    @Override
    public GuideDTO create(GuideDTO guideDTO) {
        EntityManager em = HibernateConfig.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            Guide guide = DTOMapper.toGuideEntity(guideDTO);
            em.persist(guide);
            transaction.commit();
            return DTOMapper.toGuideDTO(guide);
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
    public List<GuideDTO> getAll() {
        EntityManager em = HibernateConfig.getEntityManagerFactory().createEntityManager();
        try {
            List<Guide> guides = em.createQuery("SELECT g FROM Guide g", Guide.class).getResultList();
            return guides.stream()
                    .map(DTOMapper::toGuideDTO)
                    .toList();
        } finally {
            em.close();
        }
    }

    @Override
    public GuideDTO getById(Long id) {
        EntityManager em = HibernateConfig.getEntityManagerFactory().createEntityManager();
        try {
            Guide guide = em.find(Guide.class, id);
            return DTOMapper.toGuideDTO(guide);
        } finally {
            em.close();
        }
    }

    @Override
    public GuideDTO update(GuideDTO guideDTO) {
        EntityManager em = HibernateConfig.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            Guide guide = DTOMapper.toGuideEntity(guideDTO);
            Guide updatedGuide = em.merge(guide);
            transaction.commit();
            return DTOMapper.toGuideDTO(updatedGuide);
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
            Guide guide = em.find(Guide.class, id);
            if (guide != null) {
                em.remove(guide);
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
}