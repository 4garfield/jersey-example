package garfield.java.jersey.example.dao;

import java.util.List;

import garfield.java.jersey.example.entity.Department;

public interface IDepartmentDao extends IBaseDao<Department, Long> {

    public List<Department> findAllDepartment();
}