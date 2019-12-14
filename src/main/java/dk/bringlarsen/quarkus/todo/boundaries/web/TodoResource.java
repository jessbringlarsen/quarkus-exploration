package dk.bringlarsen.quarkus.todo.boundaries.web;

import dk.bringlarsen.quarkus.todo.Todo;
import dk.bringlarsen.quarkus.todo.TodoRepository;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

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
    public Response all(@QueryParam("pageIndex") int pageIndex, @QueryParam("pageSize") int pageSize) {
        return Response.ok().entity(repository.findAll(pageIndex, pageSize)).status(200).build();
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
        Optional<Todo> result = repository.create(new Todo(0L, todo.title, false));
        if(!result.isPresent()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        return Response.status(Response.Status.CREATED).entity(result.get()).build();
    }

}
