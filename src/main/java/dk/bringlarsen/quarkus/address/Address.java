package dk.bringlarsen.quarkus.address;

public class Address {

    private String roadName;
    private String postalCode;

    public Address(String roadName, String postalCode) {
        this.roadName = roadName;
        this.postalCode = postalCode;
    }

    public String getRoadName() {
        return roadName;
    }

    public String getPostalCode() {
        return postalCode;
    }

    @Override
    public String toString() {
        return "Address{" +
                "roadName='" + roadName + '\'' +
                ", postalCode='" + postalCode + '\'' +
                '}';
    }
}
