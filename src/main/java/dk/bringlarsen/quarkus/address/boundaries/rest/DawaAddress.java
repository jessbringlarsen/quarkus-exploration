package dk.bringlarsen.quarkus.address.boundaries.rest;

public class DawaAddress {

    public String tekst;
    public Data data;

    public static class Data {
        public String id;
        public String vejnavn;
        public String postnr;
    }
}
