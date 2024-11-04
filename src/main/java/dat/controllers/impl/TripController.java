package dat.controllers.impl;

import dat.daos.impl.TripDAO;
import dat.dtos.GuidePriceDTO;
import dat.dtos.TripDTO;
import dat.entities.TripCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TripController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TripController.class);
    private final TripDAO tripDAO = new TripDAO();

    public TripDTO createTrip(TripDTO tripDTO) {
        return tripDAO.create(tripDTO);
    }

    public List<TripDTO> getAllTrips() {
        return tripDAO.getAll();
    }

    public TripDTO getTripById(Long id) {
        return tripDAO.getById(id);
    }

    public TripDTO updateTrip(TripDTO tripDTO) {
        return tripDAO.update(tripDTO);
    }

    public boolean deleteTrip(Long id) {
        return tripDAO.delete(id);
    }

    public void addGuideToTrip(Long tripId, Long guideId) {
        tripDAO.addGuideToTrip(tripId, guideId);
    }

    public Set<TripDTO> getTripsByGuide(Long guideId) {
        return tripDAO.getTripsByGuide(guideId);
    }

    // Nye metoder til streams og queries
    public List<TripDTO> getTripsByCategory(TripCategory category) {
        try {
            LOGGER.info("Getting trips by category: {}", category);
            return tripDAO.getAll().stream()
                    .filter(trip -> trip.getTripCategory() == category)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            LOGGER.error("Error getting trips by category: {}", category, e);
            throw e;
        }
    }

    public List<GuidePriceDTO> getGuidesTotalPrice() {
        try {
            LOGGER.info("Calculating total price for all guides");
            return tripDAO.getAll().stream()
                    .filter(trip -> trip.getGuide() != null)
                    .collect(Collectors.groupingBy(
                            trip -> trip.getGuide().getId(),
                            Collectors.summingDouble(TripDTO::getPrice)))
                    .entrySet().stream()
                    .map(entry -> new GuidePriceDTO(entry.getKey(), entry.getValue()))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            LOGGER.error("Error calculating guide total prices", e);
            throw e;
        }
    }
}

