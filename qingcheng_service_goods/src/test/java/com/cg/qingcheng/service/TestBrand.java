package com.cg.qingcheng.service;

import com.cg.qingcheng.dao.BrandMapper;
import com.cg.qingcheng.pojo.goods.Brand;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * @program: qingcheng_parent->TestBrand
 * @description:
 * @author: cg
 * @create: 2020-02-21 16:14
 **/

public class TestBrand {

    @Test
    public void test() throws Exception{
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-dao.xml", "applicationContext-common.xml");
        //DruidDataSource bean = context.getBean(DruidDataSource.class);
        //System.out.println(bean.getUsername());
        //DruidPooledConnection connection = bean.getConnection();
        //System.out.println(connection);
        BrandMapper bean = context.getBean(BrandMapper.class);
        System.out.println(bean);
        List<Brand> brands = bean.selectAll();
        for (Brand brand : brands) {
            System.out.println(brand.getName());
        }
    }

}
