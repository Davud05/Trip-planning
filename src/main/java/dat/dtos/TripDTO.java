package dat.dtos;

import dat.entities.TripCategory;
import java.time.LocalDateTime;

public class TripDTO {
    private Long id;
    private String name;
    private TripCategory tripCategory;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private double latitude;
    private double longitude;
    private double price;
    private GuideDTO guide;

    // Default constructor
    public TripDTO() {}

    // Full constructor
    public TripDTO(Long id, String name, TripCategory tripCategory,
                   LocalDateTime startTime, LocalDateTime endTime,
                   double latitude, double longitude, double price,
                   GuideDTO guide) {
        this.id = id;
        this.name = name;
        this.tripCategory = tripCategory;
        this.startTime = startTime;
        this.endTime = endTime;
        this.latitude = latitude;
        this.longitude = longitude;
        this.price = price;
        this.guide = guide;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public TripCategory getTripCategory() {
        return tripCategory;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getPrice() {
        return price;
    }

    public GuideDTO getGuide() {
        return guide;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTripCategory(TripCategory tripCategory) {
        this.tripCategory = tripCategory;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setGuide(GuideDTO guide) {
        this.guide = guide;
    }
}