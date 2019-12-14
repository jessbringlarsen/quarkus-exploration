package dk.bringlarsen.quarkus.address;

import java.util.List;

public interface AddressRepository {

    List<Address> lookupAddress(String searchTerm);
}
