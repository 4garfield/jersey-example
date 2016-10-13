package garfield.java.jersey.example.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import garfield.java.jersey.example.dao.IGoodsDao;
import garfield.java.jersey.example.entity.Goods;
import garfield.java.jersey.example.service.IGoodsService;

@Service
@Transactional
public class GoodsServiceImpl implements IGoodsService {
    
    @Autowired
    private IGoodsDao goodsDao;

    @Override
    public List<Goods> findAllGoods(Long departmentId, Long subDepartmentId) {
        return goodsDao.findAllGoods(departmentId, subDepartmentId);
    }

    @Override
    public Goods createGoods(Goods goods) {
        return goodsDao.save(goods);
    }

    @Override
    public boolean existGoods(Long departmentId, Long subDepartmentId, Long id) {
        return goodsDao.existGoods(departmentId, subDepartmentId, id);
    }

    @Override
    public Goods findGoods(Long departmentId, Long subDepartmentId, Long id) {
        return goodsDao.findGoods(departmentId, subDepartmentId, id);
    }

    @Override
    public Goods updateGoods(Goods goods) {
        return goodsDao.update(goods);
    }

    @Override
    public void deleteGoods(Long departmentId, Long subDepartmentId, Long id) {
        goodsDao.deleteGoods(departmentId, subDepartmentId, id);
    }
    
}
