package dk.bringlarsen.quarkus.address.boundaries.web;

import dk.bringlarsen.quarkus.address.Address;
import dk.bringlarsen.quarkus.address.AddressRepository;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/addresses")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AddressResource {

    private AddressRepository addressRepository;

    @Inject
    public AddressResource(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @GET
    @Path("/lookup/{searchTerm}")
    public Response lookupAddress(@PathParam("searchTerm") String searchTerm) {
        return Response.ok().entity(addressRepository.lookupAddress(searchTerm)).build();
    }
}
