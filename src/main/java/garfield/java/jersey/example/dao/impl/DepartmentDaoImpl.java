package garfield.java.jersey.example.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import garfield.java.jersey.example.dao.IDepartmentDao;
import garfield.java.jersey.example.entity.Department;

@Repository
public class DepartmentDaoImpl extends BaseDaoImpl<Department, Long> implements IDepartmentDao {
    
    public DepartmentDaoImpl() {
        super(Department.class);
    }

    @Override
    public List<Department> findAllDepartment() {
        return super.createEntityNamedQuery("Department.findAll", null).getResultList();
    }
    
}
