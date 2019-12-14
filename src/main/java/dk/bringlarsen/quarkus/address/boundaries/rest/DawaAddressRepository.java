package dk.bringlarsen.quarkus.address.boundaries.rest;

import dk.bringlarsen.quarkus.address.Address;
import dk.bringlarsen.quarkus.address.AddressRepository;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class DawaAddressRepository implements AddressRepository {

    private DawaClient dawaClient;

    @Inject
    public DawaAddressRepository(@RestClient DawaClient dawaClient) {
        this.dawaClient = dawaClient;
    }

    public List<Address> lookupAddress(String searchTerm) {
        List<DawaAddress> result = dawaClient.lookupAddress(searchTerm);

        if(result == null || result.isEmpty()) {
            return Collections.emptyList();
        }

        return result.stream()
                .map(a -> new Address(a.forslagstekst, a.data.vejnavn, a.data.husnr, a.data.postnr))
                .collect(Collectors.toList());
    }
}
