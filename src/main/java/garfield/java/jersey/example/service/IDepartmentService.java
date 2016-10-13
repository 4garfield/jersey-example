package garfield.java.jersey.example.service;

import java.util.List;

import garfield.java.jersey.example.entity.Department;

public interface IDepartmentService {
    
    public List<Department> findAll();
    
    public Department findDepartment(Long id);
    
    public Department createDepartment(Department departmentType);
    
    public Department updateDepartment(Department departmentType);
    
    public void deleteDepartment(Long id);
    
    public boolean existDepartment(Long id);
}
