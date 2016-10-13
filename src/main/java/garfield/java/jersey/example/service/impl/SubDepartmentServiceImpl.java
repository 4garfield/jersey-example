package garfield.java.jersey.example.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import garfield.java.jersey.example.dao.ISubDepartmentDao;
import garfield.java.jersey.example.entity.SubDepartment;
import garfield.java.jersey.example.service.ISubDepartmentService;

@Service
@Transactional
public class SubDepartmentServiceImpl implements ISubDepartmentService {
    
    @Autowired
    private ISubDepartmentDao subDepartmentDao;
    
    @Override
    public List<SubDepartment> findAllByDepartmentId(Long departmentId) {
        return subDepartmentDao.findAllByDepartmentId(departmentId);
    }
    
    @Override
    public SubDepartment findSubDepartment(Long departmentId, Long id) {
        return subDepartmentDao.findSubDepartment(departmentId, id);
    }

    @Override
    public SubDepartment createSubDepartment(SubDepartment subDepartment) {
        return subDepartmentDao.save(subDepartment);
    }
    
    @Override
    public SubDepartment updateSubDepartment(SubDepartment subDepartment) {
        return subDepartmentDao.update(subDepartment);
    }
    
    @Override
    public void deleteSubDepartment(Long departmentId, Long id) {
        subDepartmentDao.deleteSubDepartment(departmentId, id);
    }
    
    @Override
    public boolean existSubDepartment(Long id) {
        return subDepartmentDao.existEntity(id);
    }
    
    @Override
    public boolean existSubDepartment(Long departmentId, Long id) {
        return subDepartmentDao.existSubDepartment(departmentId, id);
    }
}
