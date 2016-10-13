package garfield.java.jersey.example.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import garfield.java.jersey.example.dao.IDepartmentDao;
import garfield.java.jersey.example.entity.Department;
import garfield.java.jersey.example.service.IDepartmentService;

@Service
@Transactional
public class DepartmentServiceImpl implements IDepartmentService {
    
    @Autowired
    private IDepartmentDao departmentDao;
    
    @Override
    public List<Department> findAll() {
        return departmentDao.findAllDepartment();
    }
    
    @Override
    public Department findDepartment(Long id) {
        return departmentDao.find(id);
    }

    @Override
    public Department createDepartment(Department department) {
        return departmentDao.save(department);
    }
    
    @Override
    public Department updateDepartment(Department department) {
        return departmentDao.update(department);
    }
    
    @Override
    public void deleteDepartment(Long id) {
        departmentDao.delete(id);
    }
    
    @Override
    public boolean existDepartment(Long id) {
        return departmentDao.existEntity(id);
    }
}
