package garfield.java.jersey.example.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import garfield.java.jersey.example.common.util.DatabaseUtil;
import garfield.java.jersey.example.dao.IGoodsDao;
import garfield.java.jersey.example.entity.Goods;

@Repository
public class GoodsDaoImpl extends BaseDaoImpl<Goods, Long> implements IGoodsDao{
    
    public GoodsDaoImpl() {
        super(Goods.class);
    }

    private Map<String, Object> generateParamMap(Long departmentId, Long subDepartmentId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("departmentId", departmentId);
        paramMap.put("subDepartmentId", subDepartmentId);
        return paramMap;
    }

    @Override
    public List<Goods> findAllGoods(Long departmentId, Long subDepartmentId) {
        Map<String, Object> paramMap = generateParamMap(departmentId, subDepartmentId);
        return super.createEntityNamedQuery("Goods.findAll", paramMap).getResultList();
    }

    @Override
    public boolean existGoods(Long departmentId, Long subDepartmentId, Long id) {
        Map<String, Object> paramMap = generateParamMap(departmentId, subDepartmentId);
        paramMap.put("id", id);
        Long count = (Long) super.createNamedQuery("Goods.exist", paramMap).getSingleResult();
        return DatabaseUtil.checkExists(count);
    }

    @Override
    public Goods findGoods(Long departmentId, Long subDepartmentId, Long id) {
        Map<String, Object> paramMap = generateParamMap(departmentId, subDepartmentId);
        paramMap.put("id", id);
        return super.createEntityNamedQuery("Goods.findOne", paramMap).getSingleResult();
    }

    @Override
    public void deleteGoods(Long departmentId, Long subDepartmentId, Long id) {
        Map<String, Object> paramMap = generateParamMap(departmentId, subDepartmentId);
        paramMap.put("id", id);
        super.createNamedQuery("Goods.delete", paramMap).executeUpdate();
    }
    
}
