package com.wang.rocketmq.controller;

import com.wang.rocketmq.entity.pojo.Person;
import com.wang.rocketmq.util.excel.ExportExcelUtil;
import com.wang.rocketmq.util.excel.ImportExcelUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@RestController
@RequestMapping("/excel")
public class ExcelController {

    @PostMapping("/readExcel")
    public Object readExcel(HttpServletRequest request) throws Exception{
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        //根据请求参数获取文件名
        MultipartFile file = multipartRequest.getFile("fileName");
        if (file.isEmpty()) {
            return "文件不能为空";
        }
        InputStream inputStream = file.getInputStream();
        //读取Excel表格
        List<List<Object>> dataList = ImportExcelUtil.importExcel(file.getOriginalFilename(), inputStream);
        //第一行为标题行,所以去掉第一行的数据
        dataList.remove(0);
        inputStream.close();

        ArrayList<Person> entityList = new ArrayList<>(dataList.size());

        //自己规定好对应关系,比如上传的文件第一行为名字,第二行为年龄,然后根据下标给实体类赋值
        for (int i = 0; i < dataList.size(); i++) {
            //新创建一个实体类存放数据
            Person person = new Person();
            //取出excel中一行的数据
            List<Object> data = dataList.get(i);
            person.setName(data.get(0).toString());
            person.setAge(Integer.valueOf(data.get(1).toString()));

            //新声明的实体类放入实体类列表
            entityList.add(person);
        }

        entityList.forEach(e -> {
            System.out.println(e);
        });
        return "上传成功";
    }

    @GetMapping("/downloadTest")
    public Object downloadTest(HttpServletResponse response) throws IOException {
        ArrayList<Map<String, Object>> list = new ArrayList<>();
        HashMap<String, Object> map1 = new HashMap<>();
        map1.put("name","张三");
        map1.put("age",23);
        HashMap<String, Object> map2 = new HashMap<>();
        map2.put("name","李四");
        map2.put("age",24);
        list.add(map1);
        list.add(map2);

        //导出Excel的标题行
        String[] headers = {"姓名","年龄"};
        //map中的key名称,和标题行顺序对应
        String[] dataKey = {"name","age"};

        //test.xls是弹出下载对话框的文件名，不能为中文，中文请自行编码
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + new String(("test.xlsx").getBytes(), "iso-8859-1"));

        //导出数据
        ExportExcelUtil.exportExcel(headers,dataKey,list,"test.xlsx",response);
        return null;
    }
}

