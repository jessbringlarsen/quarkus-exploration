package dk.bringlarsen.quarkus.address.boundaries.rest;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.List;

@RegisterRestClient
public interface DawaClient {

    @GET
    @Path("/autocomplete")
    @Produces(MediaType.APPLICATION_JSON)
    List<DawaAddress> lookupAddress(@QueryParam("q") String searchTerm);
}
