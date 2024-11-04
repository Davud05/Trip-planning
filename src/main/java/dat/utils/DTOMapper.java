package dat.utils;

import dat.entities.Guide;
import dat.entities.Trip;
import dat.dtos.GuideDTO;
import dat.dtos.TripDTO;

public class DTOMapper {

    public static TripDTO toTripDTO(Trip trip) {
        if (trip == null) return null;

        GuideDTO guideDTO = toGuideDTO(trip.getGuide());

        return new TripDTO(
                trip.getId(),
                trip.getName(),
                trip.getTripCategory(),
                trip.getStartTime(),
                trip.getEndTime(),
                trip.getLatitude(),
                trip.getLongitude(),
                trip.getPrice(),
                guideDTO
        );
    }

    public static Trip toTripEntity(TripDTO tripDTO) {
        if (tripDTO == null) return null;

        Trip trip = new Trip(
                tripDTO.getName(),
                tripDTO.getTripCategory(),
                tripDTO.getStartTime(),
                tripDTO.getEndTime(),
                tripDTO.getLongitude(),
                tripDTO.getLatitude(),
                tripDTO.getPrice()
        );

        trip.setId(tripDTO.getId());

        if (tripDTO.getGuide() != null) {
            trip.setGuide(toGuideEntity(tripDTO.getGuide()));
        }

        return trip;
    }

    public static GuideDTO toGuideDTO(Guide guide) {
        if (guide == null) return null;

        return new GuideDTO(
                guide.getId(),
                guide.getFirstName(),
                guide.getLastName(),
                guide.getEmail(),
                guide.getPhone(),
                guide.getYearsOfExperience()
        );
    }

    public static Guide toGuideEntity(GuideDTO guideDTO) {
        if (guideDTO == null) return null;

        Guide guide = new Guide(
                guideDTO.getFirstName(),
                guideDTO.getLastName(),
                guideDTO.getEmail(),
                guideDTO.getPhone(),
                guideDTO.getYearsOfExperience()
        );
        guide.setId(guideDTO.getId());
        return guide;
    }
}