package garfield.java.jersey.example.rest.hateoas.assembler;

import org.springframework.hateoas.jaxrs.JaxRsLinkBuilder;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import garfield.java.jersey.example.entity.Department;
import garfield.java.jersey.example.rest.hateoas.BaseResourceSupportImpl;
import garfield.java.jersey.example.rest.resource.DepartmentResource;
import garfield.java.jersey.example.rest.resource.SubDepartmentResource;

public class DepartmentAssembler extends ResourceAssemblerSupport<Department, BaseResourceSupportImpl<Department>>{
    
    @SuppressWarnings("unchecked")
    private static final Class<BaseResourceSupportImpl<Department>> supportClass = (Class<BaseResourceSupportImpl<Department>>) 
            new BaseResourceSupportImpl<Department>().getClass();
    private static final Class<DepartmentResource> resourceClass = DepartmentResource.class;
    
    public DepartmentAssembler() {
        super(resourceClass, supportClass);
    }
    
    @Override
    public BaseResourceSupportImpl<Department> toResource(Department department) {
        BaseResourceSupportImpl<Department> instance = new BaseResourceSupportImpl<Department>(department);
        Long id = department.getId();
        instance.add(JaxRsLinkBuilder.linkTo(resourceClass).slash(id).withSelfRel());
        instance.add(JaxRsLinkBuilder.linkTo(SubDepartmentResource.class, id).withRel("subDepartment"));
        return instance;
    }
}
