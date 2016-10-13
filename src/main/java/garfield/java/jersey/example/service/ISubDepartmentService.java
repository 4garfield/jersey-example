package garfield.java.jersey.example.service;

import java.util.List;

import garfield.java.jersey.example.entity.SubDepartment;

public interface ISubDepartmentService {
    
    public List<SubDepartment> findAllByDepartmentId(Long departmentId);
    
    public SubDepartment findSubDepartment(Long departmentId, Long id);
    
    public SubDepartment createSubDepartment(SubDepartment subDepartment);
    
    public SubDepartment updateSubDepartment(SubDepartment subDepartment);
    
    public void deleteSubDepartment(Long departmentId, Long id);
    
    public boolean existSubDepartment(Long id);
    
    public boolean existSubDepartment(Long departmentId, Long id);
}
