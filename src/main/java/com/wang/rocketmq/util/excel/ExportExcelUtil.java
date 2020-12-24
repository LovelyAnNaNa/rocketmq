package com.wang.rocketmq.util.excel;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.*;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.util.*;

/**
 * @Auther: wbh
 * @Date: 2019/9/21 09:16
 * @Description:
 */
@Slf4j
public class ExportExcelUtil {

    /**
     *
     * @param headers 打印出的数据标题行
     * @param dataKey 每一行的数据列标题
     * @param dataset 所有行数据集合
     * @param fileName
     * @param response
     */
    public static void exportExcel(String[] headers,String[] dataKey, List<Map<String,Object>> dataset, String fileName, HttpServletResponse response) {
        // 声明一个工作薄
        XSSFWorkbook workbook = new XSSFWorkbook();
        // 生成一个表格
        XSSFSheet sheet = workbook.createSheet(fileName);
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
            //行下标
            int rowIndex = 0;
            //遍历所有的数据
            for (int i = 0; i < dataset.size(); i++) {
                //列下标
                int cellIndex = 0;
                rowIndex++;
                //创建一个新行
                row = sheet.createRow(rowIndex);

                //获取一列数据
                Map<String, Object> rowInfo = dataset.get(i);
                //根据dataHeader取出map中的值
                for (String key : dataKey) {
                    try {
                        //创建一行新列
                        XSSFCell cell = row.createCell(cellIndex);
                        Object objValue = rowInfo.get(key);
                        cell.setCellValue(objValue == null ? "" : objValue.toString());
                    }catch (Exception e){
                        e.printStackTrace();
                    } finally {
                        cellIndex++;
                    }
                }
            }
            workbook.write(response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void exportExcel(String[] headers, List<Map<String,Object>> dataset, String fileName, HttpServletResponse response) {
        // 声明一个工作薄
        XSSFWorkbook workbook = new XSSFWorkbook();
        // 生成一个表格
        XSSFSheet sheet = workbook.createSheet(fileName);
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
            //行下标
            int rowIndex = 0;
            //遍历所有的数据
            for (int i = 0; i < dataset.size(); i++) {
                //列下标
                int cellIndex = 0;
                rowIndex++;
                //创建一个新行
                row = sheet.createRow(rowIndex);

                Map<String, Object> rowInfo = dataset.get(i);

                for (String header : headers) {
                    try {
                        //创建一行新列
                        XSSFCell cell = row.createCell(cellIndex);
                        Object objValue = rowInfo.get(header);
                        cell.setCellValue(objValue == null ? "" : objValue.toString());
                    }catch (Exception e){
                        e.printStackTrace();
                    } finally {
                        cellIndex++;
                    }
                }
            }
            workbook.write(response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
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
    public static void getExportedFile(XSSFWorkbook workbook, String name, HttpServletResponse response) throws Exception {
        BufferedOutputStream fos = null;
        try {
            String fileName = name + ".xlsx";
            response.setContentType("application/x-msdownload");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
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