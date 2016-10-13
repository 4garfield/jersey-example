package garfield.java.jersey.example.rest.resource;

import java.util.List;

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
import garfield.java.jersey.example.entity.SubDepartment;
import garfield.java.jersey.example.rest.hateoas.assembler.SubDepartmentAssembler;
import garfield.java.jersey.example.service.IDepartmentService;
import garfield.java.jersey.example.service.ISubDepartmentService;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("department/{departmentId}/subDepartment")
public class SubDepartmentResource {
    
    @PathParam("departmentId")
    private Long departmentId;
    
    @Context
    private UriInfo uriInfo;
    
    @Autowired
    private IDepartmentService departmentService;
    
    @Autowired
    private ISubDepartmentService subDepartmentService;
    
    private SubDepartmentAssembler assembler = new SubDepartmentAssembler();
    
    @GET
    @ValidateOnExecution
    @Valid
    public Response getAllSubDepartment() {
        if(!departmentService.existDepartment(departmentId))
            return ResponseUtil.buildNotFound();
        List<SubDepartment> subDepartmentList = subDepartmentService.findAllByDepartmentId(departmentId);
        return Response.ok(assembler.toResources(subDepartmentList)).build();
    }
    
    @POST
    public Response addSubDepartment(@Valid SubDepartment subDepartment) {
        if(null != subDepartment.getId())
            return ResponseUtil.buildBadRequest("cannot add the entity with id");
        if(!departmentService.existDepartment(departmentId))
            return ResponseUtil.buildBadRequest("cannot add subDepartment under the non exist department");
        subDepartment = subDepartmentService.createSubDepartment(subDepartment);
        return Response.created(uriInfo.getRequestUriBuilder().path(subDepartment.getId().toString()).build()).entity(assembler.toResource(subDepartment)).build();
    }
    
    @Path("{id}")
    @GET
    @ValidateOnExecution
    @Valid
    public Response getSubDepartment(@PathParam("id") Long id) {
        if(!subDepartmentService.existSubDepartment(departmentId, id))
            return ResponseUtil.buildNotFound();
        SubDepartment subDepartment = subDepartmentService.findSubDepartment(departmentId, id);
        return Response.ok(assembler.toResource(subDepartment)).build();
    }
    
    @Path("{id}")
    @PUT
    public Response updateSubDepartment(@PathParam("id") Long id, @Valid SubDepartment requestBody) {
        if(null == requestBody.getId() || !id.equals(requestBody.getId()))
            return ResponseUtil.buildBadRequest("unmatched path id and request body id");
        if(!subDepartmentService.existSubDepartment(departmentId, id))
            return ResponseUtil.buildBadRequest("cannot put to update an entity which doesn't exist");
        SubDepartment subDepartment = subDepartmentService.updateSubDepartment(requestBody);
        return Response.ok(assembler.toResource(subDepartment)).build();
    }
    
    @Path("{id}")
    @DELETE
    public Response deleteSubDepartment(@PathParam("id") Long id) {
        if(subDepartmentService.existSubDepartment(departmentId, id))
            subDepartmentService.deleteSubDepartment(departmentId, id);
        return Response.noContent().build();
    }
    
}
