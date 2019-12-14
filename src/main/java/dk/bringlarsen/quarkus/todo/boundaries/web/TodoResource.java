package dk.bringlarsen.quarkus.todo.boundaries.web;

import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.ResponseBuilder;

import dk.bringlarsen.quarkus.todo.PageableResult;
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
        
        PageableResult pageableResult = repository.findAll(pageIndex, pageSize);
        ResponseBuilder responseBuilder = Response.ok()
            .status(200)    
            .entity(pageableResult.getResult())
            .links(Link.fromUri(uriInfo.getRequestUri()).rel("self").build());
        
        if(pageableResult.hasPrevious()) {
            responseBuilder.links(getNavigationLink(uriInfo, "prev", pageIndex-1, pageSize));
        }

        if(pageableResult.hasNext()) {
            responseBuilder.links(getNavigationLink(uriInfo, "next", pageIndex+1, pageSize));
        }
        return responseBuilder.build();
    }

    private Link getNavigationLink(UriInfo uriInfo, String rel, int pageIndex, int pageSize) {
        UriBuilder uri = uriInfo.getAbsolutePathBuilder();
        uri.queryParam("pageIndex", pageIndex);
        uri.queryParam("pageSize", pageSize);
        return Link.fromUri(uri.build()).rel(rel).build();
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

    @PATCH
    @Path("/{id}")
    public Response patch(@PathParam("id") long id, TodoInputModel todo) {
        Optional<Todo> result = repository.save(new Todo(id, todo.title, false, 0));
        if(!result.isPresent()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.OK).entity(result.get()).build();
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
