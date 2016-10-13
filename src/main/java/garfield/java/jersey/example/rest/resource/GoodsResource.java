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
import garfield.java.jersey.example.entity.Goods;
import garfield.java.jersey.example.rest.hateoas.assembler.GoodsAssembler;
import garfield.java.jersey.example.service.IGoodsService;
import garfield.java.jersey.example.service.ISubDepartmentService;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("department/{departmentId}/subDepartment/{subDepartmentId}/goods")
public class GoodsResource {
    
    @PathParam("departmentId")
    private Long departmentId;
    
    @PathParam("subDepartmentId")
    private Long subDepartmentId;
    
    @Context
    private UriInfo uriInfo;
    
    @Autowired
    private ISubDepartmentService subDepartmentService;
    
    @Autowired
    private IGoodsService goodsService;
    
    private GoodsAssembler assembler = new GoodsAssembler();
    
    @GET
    @ValidateOnExecution
    @Valid
    public Response getAllGoods() {
        if(!subDepartmentService.existSubDepartment(departmentId, subDepartmentId))
            return ResponseUtil.buildNotFound();
        List<Goods> goodsList = goodsService.findAllGoods(departmentId, subDepartmentId);
        return Response.ok(assembler.toResources(goodsList)).build();
    }
    
    @POST
    public Response addGoods(@Valid Goods requestBody) {
        if(null != requestBody.getId())
            return ResponseUtil.buildBadRequest("cannot add the entity with id");
        if(!subDepartmentService.existSubDepartment(departmentId, subDepartmentId))
            return ResponseUtil.buildBadRequest("cannot add goods under the non exist subDepartment");
        Goods goods = goodsService.createGoods(requestBody);
        return Response.created(uriInfo.getRequestUriBuilder().path(goods.getId().toString()).build())
                .entity(assembler.toResource(goods)).build();
    }
    
    @Path("{id}")
    @GET
    @ValidateOnExecution
    @Valid
    public Response getGoods(@PathParam("id") Long id) {
        if(!goodsService.existGoods(departmentId, subDepartmentId, id))
            return ResponseUtil.buildNotFound();
        Goods goods = goodsService.findGoods(departmentId, subDepartmentId, id);
        return Response.ok(assembler.toResource(goods)).build();
    }
    
    @Path("{id}")
    @PUT
    public Response updateGoods(@PathParam("id") Long id, @Valid Goods requestBody) {
        if(null == requestBody.getId() || !id.equals(requestBody.getId()))
            return ResponseUtil.buildBadRequest("unmatched path id and request body id");
        if(!goodsService.existGoods(departmentId, subDepartmentId, id))
            return ResponseUtil.buildBadRequest("cannot put to update an entity which doesn't exist");
        Goods goods = goodsService.updateGoods(requestBody);
        return Response.ok(assembler.toResource(goods)).build();
    }
    
    @Path("{id}")
    @DELETE
    public Response deleteGoods(@PathParam("id") Long id) {
        if(goodsService.existGoods(departmentId, subDepartmentId, id))
            goodsService.deleteGoods(departmentId, subDepartmentId, id);
        return Response.noContent().build();
    }
    
}
