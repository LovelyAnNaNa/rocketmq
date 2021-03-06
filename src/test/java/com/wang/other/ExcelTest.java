package com.wang.other;

import com.wang.rocketmq.entity.pojo.Person;
import com.wang.rocketmq.util.excel.ExportExcelUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.*;
import org.junit.Test;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

/**
 * @Auther: wbh
 * @Date: 2019/9/21 09:16
 * @Description:
 */
public class ExcelTest {

    @Test
    public void test1(){
        String[] colsName = {"姓名","年龄"};
        ArrayList<HashMap> personList = new ArrayList<>();
        HashMap<String, Object> map1 = new HashMap<>();
        map1.put("name","张三");
        map1.put("age",23);
        HashMap<String, Object> map2 = new HashMap<>();
        map2.put("name","李四");
        map2.put("age",24);

        ExportExcelUtil.exportExcel(colsName,personList,"test.xlsx","E:\\download\\other\\test.xlsx");
    }

    public static void main(String[] args) {
//        ArrayList<Message> messages = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            Message message = new Message();
//            message.setMethod("aaaaa");
//            message.setParams("bbbb");
//            messages.add(message);
//        }
//        exportExcel(new String[]{"a"},messages,null,"E:\\wang\\json\\1.xlsx");

        //实体类中的字段数组中对应的属性下标和属性名
        HashMap<String, Integer> fieldIndexMap = new HashMap<>();

        Field[] fieldArray = Person.class.getDeclaredFields();
        for (int i = 0; i < fieldArray.length; i++) {
            Field field = fieldArray[i];
            fieldIndexMap.put(field.getName(),i);
        }

        fieldIndexMap.forEach( (k,v) -> {
            System.out.println(k + "," + v); });
    }
    public static void exportExcel(String[] headers, Collection dataset, String fileName,String filePath) {
        // 声明一个工作薄
        XSSFWorkbook workbook = new XSSFWorkbook();
        // 生成一个表格
        XSSFSheet sheet = workbook.createSheet("1.xlsx");
        // 设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth((short) 20);
        // 产生表格标题行
        XSSFRow row = sheet.createRow(0);
        for (short i = 0; i < headers.length; i++) {
            XSSFCell cell = row.createCell(i);
            XSSFRichTextString text = new XSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }
        try {
            // 遍历集合数据，产生数据行
            Iterator it = dataset.iterator();
            int index = 0;
            while (it.hasNext()) {
                index++;
                row = sheet.createRow(index);
                //当前遍历的集合对象
                Object next = it.next();
                //获取遍历的对象所有字段
                Field[] fields = next.getClass().getDeclaredFields();
                for (int i = 0; i < fields.length; i++) {
                    //创建一行新列
                    XSSFCell cell = row.createCell(i);
                    Field field = fields[i];
                    field.setAccessible(true);
                    //获取字段属性
                    String value = field.get(next).toString();
                    value = StringUtils.isBlank(value) ? "" : value;
                    cell.setCellValue(value);
                }
            }
            workbook.write(new FileOutputStream(filePath));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //产生输出
    public void getExportedFile(XSSFWorkbook workbook, String name,HttpServletResponse response) throws Exception {
        BufferedOutputStream fos = null;
        try {
            String fileName = name + ".xlsx";
            response.setContentType("application/x-msdownload");
            response.setHeader("Content-Disposition", "attachment;filename=" + new String( fileName.getBytes("gb2312"), "ISO8859-1" ));
            fos = new BufferedOutputStream(response.getOutputStream());
            workbook.write(fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                fos.close();
            }
        }
    }

}