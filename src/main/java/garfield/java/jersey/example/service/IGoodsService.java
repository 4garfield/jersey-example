package garfield.java.jersey.example.service;

import java.util.List;

import garfield.java.jersey.example.entity.Goods;

public interface IGoodsService {
    
    public List<Goods> findAllGoods(Long departmentId, Long subDepartmentId);

    public Goods createGoods(Goods goods);

    public boolean existGoods(Long departmentId, Long subDepartmentId, Long id);

    public Goods findGoods(Long departmentId, Long subDepartmentId, Long id);

    public Goods updateGoods(Goods goods);

    public void deleteGoods(Long departmentId, Long subDepartmentId, Long id);
}
