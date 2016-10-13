package garfield.java.jersey.example.dao;

import java.util.List;

import garfield.java.jersey.example.entity.Goods;

public interface IGoodsDao extends IBaseDao<Goods, Long>{

    public List<Goods> findAllGoods(Long departmentId, Long subDepartmentId);

    public boolean existGoods(Long departmentId, Long subDepartmentId, Long id);

    public Goods findGoods(Long departmentId, Long subDepartmentId, Long id);

    public void deleteGoods(Long departmentId, Long subDepartmentId, Long id);
}
