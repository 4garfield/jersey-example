package garfield.java.jersey.example.dao;

import java.util.List;

import garfield.java.jersey.example.entity.SubDepartment;

public interface ISubDepartmentDao extends IBaseDao<SubDepartment, Long>{

    public List<SubDepartment> findAllByDepartmentId(Long departmentId);

    public SubDepartment findSubDepartment(Long departmentId, Long id);

    public boolean existSubDepartment(Long departmentId, Long id);

    public void deleteSubDepartment(Long departmentId, Long id);
}
