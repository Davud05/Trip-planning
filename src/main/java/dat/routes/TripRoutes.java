package dat.routes;

import dat.controllers.impl.TripController;
import dat.dtos.TripDTO;
import dat.entities.TripCategory;
import dat.utils.Populator;
import io.javalin.Javalin;
import io.javalin.http.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class TripRoutes {
    private static final Logger LOGGER = LoggerFactory.getLogger(TripRoutes.class);
    private final TripController tripController = new TripController();

    public void addRoutes(Javalin app) {
        app.get("/trips", this::getAllTrips);
        app.get("/trips/{id}", this::getTripById);
        app.post("/trips", this::createTrip);
        app.put("/trips/{id}", this::updateTrip);
        app.delete("/trips/{id}", this::deleteTrip);
        app.put("/trips/{tripId}/guides/{guideId}", this::addGuideToTrip);
        app.post("/trips/populate", this::populateDatabase);
        app.get("/trips/category/{category}", this::getTripsByCategory);
        app.get("/trips/guides/totalprice", this::getGuidesTotalPrice);
    }

    private void getAllTrips(Context ctx) {
        try {
            LOGGER.info("Getting all trips");
            ctx.json(tripController.getAllTrips());
        } catch (Exception e) {
            LOGGER.error("Error getting all trips", e);
            ctx.status(500).json(new ErrorResponse("Error getting all trips", e.getMessage()));
        }
    }

    private void getTripById(Context ctx) {
        try {
            Long id = ctx.pathParamAsClass("id", Long.class).get();
            LOGGER.info("Getting trip with id: {}", id);
            TripDTO trip = tripController.getTripById(id);
            if (trip != null) {
                ctx.json(trip);
            } else {
                ctx.status(404).json(new ErrorResponse("Trip not found", "No trip with id: " + id));
            }
        } catch (Exception e) {
            LOGGER.error("Error getting trip by id", e);
            ctx.status(500).json(new ErrorResponse("Error getting trip", e.getMessage()));
        }
    }

    private void createTrip(Context ctx) {
        try {
            TripDTO tripDTO = ctx.bodyAsClass(TripDTO.class);
            LOGGER.info("Creating new trip: {}", tripDTO.getName());
            TripDTO createdTrip = tripController.createTrip(tripDTO);
            ctx.status(201).json(createdTrip);
        } catch (Exception e) {
            LOGGER.error("Error creating trip", e);
            ctx.status(500).json(new ErrorResponse("Error creating trip", e.getMessage()));
        }
    }

    private void updateTrip(Context ctx) {
        try {
            Long id = ctx.pathParamAsClass("id", Long.class).get();
            TripDTO tripDTO = ctx.bodyAsClass(TripDTO.class);
            tripDTO.setId(id);
            LOGGER.info("Updating trip with id: {}", id);
            TripDTO updatedTrip = tripController.updateTrip(tripDTO);
            if (updatedTrip != null) {
                ctx.json(updatedTrip);
            } else {
                ctx.status(404).json(new ErrorResponse("Trip not found", "No trip with id: " + id));
            }
        } catch (Exception e) {
            LOGGER.error("Error updating trip", e);
            ctx.status(500).json(new ErrorResponse("Error updating trip", e.getMessage()));
        }
    }

    private void deleteTrip(Context ctx) {
        try {
            Long id = ctx.pathParamAsClass("id", Long.class).get();
            LOGGER.info("Deleting trip with id: {}", id);
            boolean deleted = tripController.deleteTrip(id);
            if (deleted) {
                ctx.status(204);
            } else {
                ctx.status(404).json(new ErrorResponse("Trip not found", "No trip with id: " + id));
            }
        } catch (Exception e) {
            LOGGER.error("Error deleting trip", e);
            ctx.status(500).json(new ErrorResponse("Error deleting trip", e.getMessage()));
        }
    }

    private void addGuideToTrip(Context ctx) {
        try {
            Long tripId = ctx.pathParamAsClass("tripId", Long.class).get();
            Long guideId = ctx.pathParamAsClass("guideId", Long.class).get();
            LOGGER.info("Adding guide {} to trip {}", guideId, tripId);
            tripController.addGuideToTrip(tripId, guideId);
            ctx.status(200).json(new SuccessResponse("Guide added to trip successfully"));
        } catch (IllegalArgumentException e) {
            LOGGER.warn("Invalid guide or trip id", e);
            ctx.status(400).json(new ErrorResponse("Invalid request", e.getMessage()));
        } catch (Exception e) {
            LOGGER.error("Error adding guide to trip", e);
            ctx.status(500).json(new ErrorResponse("Error adding guide to trip", e.getMessage()));
        }
    }

    private void populateDatabase(Context ctx) {
        try {
            LOGGER.info("Populating database");
            new Populator().populate();
            ctx.status(200).json(new SuccessResponse("Database populated successfully"));
        } catch (Exception e) {
            LOGGER.error("Error populating database", e);
            ctx.status(500).json(new ErrorResponse("Error populating database", e.getMessage()));
        }
    }
    private void getTripsByCategory(Context ctx) {
        try {
            TripCategory category = TripCategory.valueOf(ctx.pathParam("category").toUpperCase());
            LOGGER.info("Getting trips by category: {}", category);
            ctx.json(tripController.getTripsByCategory(category));
        } catch (IllegalArgumentException e) {
            LOGGER.warn("Invalid category requested", e);
            ctx.status(400).json(new ErrorResponse(
                    "Invalid category",
                    "Valid categories are: " + Arrays.toString(TripCategory.values())
            ));
        } catch (Exception e) {
            LOGGER.error("Error getting trips by category", e);
            ctx.status(500).json(new ErrorResponse(
                    "Server error",
                    "Error getting trips: " + e.getMessage()
            ));
        }
    }

    private void getGuidesTotalPrice(Context ctx) {
        try {
            LOGGER.info("Getting total price overview for guides");
            ctx.json(tripController.getGuidesTotalPrice());
        } catch (Exception e) {
            LOGGER.error("Error getting guide total prices", e);
            ctx.status(500).json(new ErrorResponse(
                    "Server error",
                    "Error getting guide prices: " + e.getMessage()
            ));
        }
    }

    private static class ErrorResponse {
        private final String message;
        private final String details;

        public ErrorResponse(String message, String details) {
            this.message = message;
            this.details = details;
        }

        public String getMessage() {
            return message;
        }

        public String getDetails() {
            return details;
        }
    }

    private static class SuccessResponse {
        private final String message;

        public SuccessResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}