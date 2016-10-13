package garfield.java.jersey.example.rest.hateoas.assembler;

import org.springframework.hateoas.jaxrs.JaxRsLinkBuilder;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import garfield.java.jersey.example.entity.Goods;
import garfield.java.jersey.example.rest.hateoas.BaseResourceSupportImpl;
import garfield.java.jersey.example.rest.resource.GoodsResource;
import garfield.java.jersey.example.rest.resource.SubDepartmentResource;

public class GoodsAssembler extends ResourceAssemblerSupport<Goods, BaseResourceSupportImpl<Goods>> {
    
    @SuppressWarnings("unchecked")
    private static final Class<BaseResourceSupportImpl<Goods>> supportClass = (Class<BaseResourceSupportImpl<Goods>>) 
            new BaseResourceSupportImpl<Goods>().getClass();
    private static final Class<GoodsResource> resourceClass = GoodsResource.class;
    
    public GoodsAssembler() {
        super(resourceClass, supportClass);
    }
    
    @Override
    public BaseResourceSupportImpl<Goods> toResource(Goods goods) {
        BaseResourceSupportImpl<Goods> instance = new BaseResourceSupportImpl<Goods>(goods);
        Long id = goods.getId();
        Long subDepartmentId = goods.getSubDepartment().getId();
        Long departmentId = goods.getSubDepartment().getDepartment().getId();
        instance.add(JaxRsLinkBuilder.linkTo(GoodsResource.class, departmentId, subDepartmentId).slash(id)
            .withSelfRel());
        instance.add(JaxRsLinkBuilder.linkTo(SubDepartmentResource.class, departmentId).slash(subDepartmentId)
            .withRel("subDepartment"));
        return instance;
    }
    
}
