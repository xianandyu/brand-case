package com.itheima.web;

import com.alibaba.fastjson.JSON;
import com.itheima.pojo.Brand;
import com.itheima.pojo.PageBean;
import com.itheima.service.brandService;
import com.itheima.service.impl.BrandServiceImpl;
import org.apache.ibatis.annotations.Insert;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet("/brand/*")
public class brandServlet extends BaseServlet {
    private brandService service = new BrandServiceImpl();

    /**
     * 查询所有
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void selectAll(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Brand> brands = service.selectAll();
        String jsonString = JSON.toJSONString(brands);

        resp.setContentType("text/json;charset=utf-8");
        resp.getWriter().write(jsonString);
    }

    /**
     * 添加品牌
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取JSON对象
        BufferedReader reader = req.getReader();
        String param = reader.readLine();

        //转换成brand类
        Brand brand = JSON.parseObject(param, Brand.class);

        //新增信息
        service.addBrand(brand);

        //返回信息
        resp.getWriter().write("success");
    }

    /**
     * 删除品牌
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取JSON对象
        BufferedReader reader = req.getReader();
        String param = reader.readLine();

        //转换成brand类
        Integer id = JSON.parseObject(param, Integer.class);

        //新增信息
        service.deleteById(id);

        //返回信息
        resp.getWriter().write("success");
    }

    /**
     * 更新品牌
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取JSON对象
        BufferedReader reader = req.getReader();
        String param = reader.readLine();

        //转换成brand类
        Brand brand = JSON.parseObject(param, Brand.class);

        //新增信息
        service.updateBrand(brand);

        //返回信息
        resp.getWriter().write("success");
    }

    /**
     * 批量删除
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void deleteByIds(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1. 接收数据  [1,2,3]
        BufferedReader br = req.getReader();
        String params = br.readLine();//json字符串

        //转为 int[]
        int[] ids = JSON.parseObject(params, int[].class);

        System.out.println(ids);
        //2. 调用service添加
        service.deleteByIds(ids);

        //3. 响应成功的标识
        resp.getWriter().write("success");
    }

    /**
     * 返回分页数据，数据总数
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void selectByPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取页码相关数据
        //begin:起始数据 size:每页数据量
        int begin = Integer.parseInt(req.getParameter("begin"));
        int size = Integer.parseInt(req.getParameter("size"));

        //转换为JSON对象
        PageBean<Brand> pageBean = service.selectByPage(begin, size);
        String jsonString = JSON.toJSONString(pageBean);

        resp.setContentType("text/json;charset=utf-8");
        resp.getWriter().write(jsonString);
    }

    public void selectByPageAndCondition(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取页码相关数据
        //begin:起始数据 size:每页数据量
        int begin = Integer.parseInt(req.getParameter("begin"));
        int size = Integer.parseInt(req.getParameter("size"));
        //获取Brand对象
        BufferedReader br = req.getReader();
        String params = br.readLine();

        //转换
        Brand brand = JSON.parseObject(params, Brand.class);

        //转换为JSON对象
        PageBean<Brand> pageBean = service.selectByPageAndCondition(begin, size, brand);
        String toJSONString = JSON.toJSONString(pageBean);

        resp.setContentType("text/json;charset=utf-8");
        resp.getWriter().write(toJSONString);
    }
}
