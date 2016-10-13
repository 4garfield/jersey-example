package garfield.java.jersey.example.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import garfield.java.jersey.example.common.util.DatabaseUtil;
import garfield.java.jersey.example.dao.ISubDepartmentDao;
import garfield.java.jersey.example.entity.SubDepartment;

@Repository
public class SubDepartmentDaoImpl extends BaseDaoImpl<SubDepartment, Long> implements ISubDepartmentDao{
    
    public SubDepartmentDaoImpl() {
        super(SubDepartment.class);
    }

    private Map<String, Object> generateParamMap(Long departmentId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("departmentId", departmentId);
        return paramMap;
    }

    @Override
    public List<SubDepartment> findAllByDepartmentId(Long departmentId) {
        Map<String, Object> paramMap = generateParamMap(departmentId);
        return super.createEntityNamedQuery("SubDepartment.findAllByDepartmentId", paramMap).getResultList();
    }

    @Override
    public SubDepartment findSubDepartment(Long departmentId, Long id) {
        Map<String, Object> paramMap = generateParamMap(departmentId);
        paramMap.put("id", id);
        return super.createEntityNamedQuery("SubDepartment.findOne", paramMap).getSingleResult();
    }

    @Override
    public boolean existSubDepartment(Long departmentId, Long id) {
        Map<String, Object> paramMap = generateParamMap(departmentId);
        paramMap.put("id", id);
        Long count = (Long) super.createNamedQuery("SubDepartment.exist", paramMap).getSingleResult();
        return DatabaseUtil.checkExists(count);
    }

    @Override
    public void deleteSubDepartment(Long departmentId, Long id) {
        Map<String, Object> paramMap = generateParamMap(departmentId);
        paramMap.put("id", id);
        super.createNamedQuery("SubDepartment.delete", paramMap).executeUpdate();
    }
    
}
