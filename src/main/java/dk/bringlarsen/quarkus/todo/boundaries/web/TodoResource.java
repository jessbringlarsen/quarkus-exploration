package dk.bringlarsen.quarkus.todo.boundaries.web;

import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.ResponseBuilder;

import dk.bringlarsen.quarkus.todo.Page;
import dk.bringlarsen.quarkus.todo.Todo;
import dk.bringlarsen.quarkus.todo.TodoRepository;

@Path("/todos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TodoResource {

    private TodoRepository repository;

    @Inject
    public TodoResource(TodoRepository repository) {
        this.repository = repository;
    }

    @GET
    public Response all(@QueryParam("pageIndex") int pageIndex, @QueryParam("pageSize") int pageSize, @Context UriInfo uriInfo) {
        if(pageSize == 0) {
            pageSize = 10;
        }
        
        Page page = repository.findAll(pageIndex, pageSize);
        ResponseBuilder responseBuilder = Response.ok()
            .status(200)    
            .entity(page.getTodos())
            .links(Link.fromUri(uriInfo.getRequestUri()).rel("self").build());
        
        if(page.hasPrevious()) {
            UriBuilder uri = uriInfo.getAbsolutePathBuilder();
            uri.queryParam("pageIndex", pageIndex-1);
            uri.queryParam("pageSize", pageSize);
            Link link = Link.fromUri(uri.build()).rel("prev").build();
            responseBuilder.links(link);
        }

        if(page.hasNext()) {
            UriBuilder uri = uriInfo.getAbsolutePathBuilder();
            uri.queryParam("pageIndex", pageIndex+1);
            uri.queryParam("pageSize", pageSize);
            Link link = Link.fromUri(uri.build()).rel("next").build();
            responseBuilder.links(link);
        }

        return responseBuilder.build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") long id) {
        Optional<Todo> result = repository.findById(id);
        if(!result.isPresent()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(result.get()).build();
    }

    @POST
    public Response create(TodoInputModel todo) {
        Optional<Todo> result = repository.create(new Todo(todo.title));
        if(!result.isPresent()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.status(Response.Status.CREATED).entity(result.get()).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") long id) {
        boolean deleted = repository.delete(id);
        if(!deleted) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
