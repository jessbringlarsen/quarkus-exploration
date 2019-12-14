package dk.bringlarsen.quarkus.address.boundarires.rest;

import dk.bringlarsen.quarkus.address.boundaries.rest.DawaAddress;
import dk.bringlarsen.quarkus.address.boundaries.rest.DawaClient;
import io.quarkus.test.junit.QuarkusTest;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.List;

@QuarkusTest
public class DawaClientTest {

    @Inject
    @RestClient
    private DawaClient dawaClient;

    @Test
    public void testDawaClient() {
        List<DawaAddress> addresses = dawaClient.lookupAddress("Mosesvinget 28");

        Assertions.assertEquals("Mosesvinget 28, 2400 København NV", addresses.get(0).forslagstekst);
        Assertions.assertEquals("2400", addresses.get(0).data.postnr);

    }
}
