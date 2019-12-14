package dk.bringlarsen.quarkus.address;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.List;

@QuarkusTest
public class AddressRepositoryTest {

    @Inject
    private AddressRepository repository;

    @Test
    public void testAddressRepository() {
        List<Address> addresses = repository.lookupAddress("Mosesvinget");

        Assertions.assertEquals("Mosesvinget", addresses.get(0).getRoadName());
        Assertions.assertEquals("2400", addresses.get(0).getPostalCode());
    }
}
