package dat.dtos;

public class GuidePriceDTO {
    private Long guideId;
    private double totalPrice;

    // Default constructor
    public GuidePriceDTO() {
    }

    // Full constructor
    public GuidePriceDTO(Long guideId, double totalPrice) {
        this.guideId = guideId;
        this.totalPrice = totalPrice;
    }

    // Getters and setters
    public Long getGuideId() {
        return guideId;
    }

    public void setGuideId(Long guideId) {
        this.guideId = guideId;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}