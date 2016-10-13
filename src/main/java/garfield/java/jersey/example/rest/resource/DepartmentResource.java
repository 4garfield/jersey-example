package garfield.java.jersey.example.rest.resource;

import javax.inject.Singleton;
import javax.validation.Valid;
import javax.validation.executable.ValidateOnExecution;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;

import garfield.java.jersey.example.common.util.ResponseUtil;
import garfield.java.jersey.example.entity.Department;
import garfield.java.jersey.example.rest.hateoas.assembler.DepartmentAssembler;
import garfield.java.jersey.example.service.IDepartmentService;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("department")
@Singleton
public class DepartmentResource {
    
    @Context
    private UriInfo uriInfo;
    
    @Autowired
    private IDepartmentService departmentService;
    
    private DepartmentAssembler assembler = new DepartmentAssembler();
    
    @GET
    @ValidateOnExecution
    @Valid
    public Response getAllDepartment() {
        return Response.ok(assembler.toResources(departmentService.findAll())).build();
    }
    
    @POST
    public Response addDepartment(@Valid Department requestBody) {
        if(null != requestBody.getId())
            return ResponseUtil.buildBadRequest("cannot add the entity with id");
        Department department = departmentService.createDepartment(requestBody);
        return Response.created(uriInfo.getRequestUriBuilder().path(department.getId().toString()).build())
                .entity(assembler.toResource(department)).build();
    }
    
    @Path("{id}")
    @GET
    @ValidateOnExecution
    @Valid
    public Response getDepartment(@PathParam("id") Long id) {
        if(!departmentService.existDepartment(id))
            return ResponseUtil.buildNotFound();
        Department department = departmentService.findDepartment(id);
        return Response.ok(assembler.toResource(department)).build();
    }
    
    @Path("{id}")
    @PUT
    public Response updateDepartment(@PathParam("id") Long id, @Valid Department requestBody) {
        if(null == requestBody.getId() || !id.equals(requestBody.getId()))
            return ResponseUtil.buildBadRequest("unmatched path id and request body id");
        if(!departmentService.existDepartment(id))
            return ResponseUtil.buildBadRequest("cannot put to update an entity which doesn't exist");
        Department department = departmentService.updateDepartment(requestBody);
        return Response.ok(assembler.toResource(department)).build();
    }
    
    @Path("{id}")
    @DELETE
    public Response deleteDepartment(@PathParam("id") Long id) {
        if(departmentService.existDepartment(id))
            departmentService.deleteDepartment(id);
        return Response.noContent().build();
    }
    
}
