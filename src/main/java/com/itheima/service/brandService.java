package com.itheima.service;

import com.itheima.pojo.Brand;
import com.itheima.pojo.PageBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface brandService {

    List<Brand> selectAll();

    void addBrand(Brand brand);

    void deleteById(int id);

    void updateBrand(Brand brand);

    void deleteByIds(int[] ids);

    PageBean<Brand> selectByPage(int begin, int size);

    PageBean<Brand> selectByPageAndCondition(int begin, int size,Brand brand);
}
