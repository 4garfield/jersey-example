package garfield.java.jersey.example.rest.hateoas.assembler;

import org.springframework.hateoas.jaxrs.JaxRsLinkBuilder;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import garfield.java.jersey.example.entity.SubDepartment;
import garfield.java.jersey.example.rest.hateoas.BaseResourceSupportImpl;
import garfield.java.jersey.example.rest.resource.DepartmentResource;
import garfield.java.jersey.example.rest.resource.GoodsResource;
import garfield.java.jersey.example.rest.resource.SubDepartmentResource;

public class SubDepartmentAssembler extends ResourceAssemblerSupport<SubDepartment, BaseResourceSupportImpl<SubDepartment>>{
    
    @SuppressWarnings("unchecked")
    private static final Class<BaseResourceSupportImpl<SubDepartment>> supportClass = (Class<BaseResourceSupportImpl<SubDepartment>>) 
            new BaseResourceSupportImpl<SubDepartment>().getClass();
    private static final Class<SubDepartmentResource> resourceClass = SubDepartmentResource.class;
    
    public SubDepartmentAssembler() {
        super(resourceClass, supportClass);
    }
    
    @Override
    public BaseResourceSupportImpl<SubDepartment> toResource(SubDepartment subDepartment) {
        BaseResourceSupportImpl<SubDepartment> instance = new BaseResourceSupportImpl<SubDepartment>(subDepartment);
        Long departmentId = subDepartment.getDepartment().getId();
        Long subDepartmentId = subDepartment.getId();
        instance.add(JaxRsLinkBuilder.linkTo(SubDepartmentResource.class, departmentId).slash(subDepartmentId).withSelfRel());
        instance.add(JaxRsLinkBuilder.linkTo(DepartmentResource.class).slash(departmentId).withRel("department"));
        instance.add(JaxRsLinkBuilder.linkTo(GoodsResource.class, departmentId, subDepartmentId).withRel("goods"));
        return instance;
    }
}
