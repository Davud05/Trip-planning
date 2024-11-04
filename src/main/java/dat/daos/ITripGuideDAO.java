package dat.daos;

import dat.dtos.TripDTO;
import java.util.Set;

public interface ITripGuideDAO {
    /**
     * Add a guide to a trip
     * @param tripId The ID of the trip
     * @param guideId The ID of the guide
     */
    void addGuideToTrip(Long tripId, Long guideId);

    /**
     * Get all trips for a specific guide
     * @param guideId The ID of the guide
     * @return Set of trips associated with the guide
     */
    Set<TripDTO> getTripsByGuide(Long guideId);
}