package dk.bringlarsen.quarkus.address;

public class Address {

    private String text;
    private String roadName;
    private String houseNo;
    private String postalCode;

    public Address(String text, String roadName, String houseNo, String postalCode) {
        this.text = text;
        this.roadName = roadName;
        this.houseNo = houseNo;
        this.postalCode = postalCode;
    }

    public String getText() {
        return text;
    }

    public String getRoadName() {
        return roadName;
    }

    public String getHouseNo() {
        return houseNo;
    }

    public String getPostalCode() {
        return postalCode;
    }

    @Override
    public String toString() {
        return "Address{" +
                "text='" + text + '\'' +
                ", roadName='" + roadName + '\'' +
                ", houseNo='" + houseNo + '\'' +
                ", postalCode='" + postalCode + '\'' +
                '}';
    }
}
