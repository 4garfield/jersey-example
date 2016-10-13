package garfield.java.jersey.example.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import garfield.java.jersey.example.initial.config.SpringConfig;
import garfield.java.jersey.example.service.IDepartmentService;
import garfield.java.jersey.example.service.IGoodsService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringConfig.class})
@WebAppConfiguration
public class DepartmentServiceTest {
    
    private static final Log logger = LogFactory.getLog(DepartmentServiceTest.class);
    
    @Autowired
    private IDepartmentService departmentService;
    
    @Autowired
    private IGoodsService goodsService;
    
    @Test
    public void testFindAll() {
        logger.info(">>start processing......");
        departmentService.findAll();
    }
    
    @Test
    @Ignore
    public void testExistDepartment() {
        boolean exist = departmentService.existDepartment(1L);
        Assert.assertTrue(exist);
    }
    
    @Test
    public void testFindAllGoods() {
        goodsService.findAllGoods(1L, 1L);
    }
    
}
