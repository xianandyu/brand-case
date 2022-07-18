package com.itheima.service.impl;

import com.itheima.mapper.BrandMapper;
import com.itheima.pojo.Brand;
import com.itheima.pojo.PageBean;
import com.itheima.service.brandService;
import com.itheima.util.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

public class BrandServiceImpl implements brandService {
    private SqlSessionFactory factory =SqlSessionFactoryUtils.getSqlSessionFactory();

    /**
     * 查询所有数据
     * @return
     */
    @Override
    public List<Brand> selectAll() {
        SqlSession sqlSession = factory.openSession();
        BrandMapper mapper = sqlSession.getMapper(BrandMapper.class);

        List<Brand> brands = mapper.selectAll();
        sqlSession.close();
        return brands;
    }

    /**
     * 添加数据
     * @param brand
     */
    @Override
    public void addBrand(Brand brand) {
        SqlSession sqlSession = factory.openSession();
        BrandMapper mapper = sqlSession.getMapper(BrandMapper.class);

        mapper.addBrand(brand);

        sqlSession.commit();
        sqlSession.close();
    }

    /**
     * 删除数据
     * @param id
     */
    @Override
    public void deleteById(int id) {
        SqlSession sqlSession = factory.openSession();
        BrandMapper mapper = sqlSession.getMapper(BrandMapper.class);

        mapper.deleteById(id);

        sqlSession.commit();
        sqlSession.close();
    }

    /**
     * 更新数据
     * @param brand
     */
    @Override
    public void updateBrand(Brand brand) {
        SqlSession sqlSession = factory.openSession();
        BrandMapper mapper = sqlSession.getMapper(BrandMapper.class);

        mapper.updateBrand(brand);

        sqlSession.commit();
        sqlSession.close();
    }

    /**
     * 批量删除
     * @param ids
     */
    @Override
    public void deleteByIds(int[] ids) {
        SqlSession sqlSession = factory.openSession();
        BrandMapper mapper = sqlSession.getMapper(BrandMapper.class);

        mapper.deleteByIds(ids);

        sqlSession.commit();
        sqlSession.close();
    }

    /**
     * 返回分页数据，数据总数
     * @param begin
     * @param size
     * @return
     */
    @Override
    public PageBean<Brand> selectByPage(int begin, int size) {
        SqlSession sqlSession = factory.openSession();
        BrandMapper mapper = sqlSession.getMapper(BrandMapper.class);

        // (begin - 1) * size:计算每页的起始数
        // size：每页数量
        List<Brand> rows = mapper.selectByPage((begin - 1) * size, size);

        //计算总数
        int totalCount = mapper.selectTotalCount();

        //封装PageBean对象
        PageBean<Brand> brandPageBean = new PageBean();
        brandPageBean.setRows(rows);
        brandPageBean.setTotalCount(totalCount);

        sqlSession.close();
        return brandPageBean;
    }

    /**
     * 分页条件查询
     * @param begin
     * @param size
     * @param brand
     * @return
     */
    @Override
    public PageBean<Brand> selectByPageAndCondition(int begin, int size, Brand brand) {
        SqlSession sqlSession = factory.openSession();
        BrandMapper mapper = sqlSession.getMapper(BrandMapper.class);

        // 处理brand条件，模糊表达式
        String brandName = brand.getBrandName();
        if (brandName != null && brandName.length() > 0) {
            brand.setBrandName("%" + brandName + "%");
        }

        String companyName = brand.getCompanyName();
        if (companyName != null && companyName.length() > 0) {
            brand.setCompanyName("%" + companyName + "%");
        }
        // (begin - 1) * size:计算每页的起始数
        // size：每页数量
        List<Brand> brands = mapper.selectByPageAndCondition((begin - 1) * size, size, brand);

        //计算总数
        int totalCount = mapper.selectTotalCountByCondition(brand);

        //封装对象
        PageBean<Brand> pageBean = new PageBean();
        pageBean.setRows(brands);
        pageBean.setTotalCount(totalCount);

        sqlSession.close();
        return  pageBean;
    }


}
