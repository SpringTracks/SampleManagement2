package com.lge.excel;

import com.lge.samplemanagement2.R;
import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Colour;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;


/**
 * Created by liping.zhu on 2016/9/1.
 */
public class JxlExcelUtils {
    public static WritableFont arial12font = null;
    public static WritableCellFormat arial12format = null;

    public static WritableFont arial10font = null;
    public static WritableCellFormat arial10format = null;

    public final static String UTF8_ENCODING = "UTF-8";
    public final static String GBK_ENCODING = "GBK";

    public static void format() {
        try {
            arial12font = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD);
            arial12format = new WritableCellFormat(arial12font);
            arial12format.setAlignment(jxl.format.Alignment.CENTRE);
            arial12format.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
            arial12format.setBackground(Colour.LIGHT_BLUE);

            arial10font = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
            arial10format = new WritableCellFormat(arial10font);
            arial10format.setAlignment(jxl.format.Alignment.CENTRE);
            arial10format.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
        }
        catch (WriteException e) {
            e.printStackTrace();
        }
    }
    public static String initExcel(String talbeName, String[] colName) {
        String path = getSDPath()+"/SampleManagement/";
        File dir = new File(path);
        makeDir(dir);
        String filePath = path+talbeName+".xls" ;
        format();
        WritableWorkbook workbook = null;
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            }
            workbook = Workbook.createWorkbook(file);
            WritableSheet sheet = workbook.createSheet(talbeName, 0);
            for (int c = 0; c < colName.length; c++) {
                Label label = new Label(c, 0, colName[c], arial12format);
                sheet.addCell(label);
            }
            workbook.write();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (workbook != null) {
                try {
                    workbook.close();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return filePath;

    }


    public static <T> void writeObjListToExcel(List<T> objList, String filePath, Context c) {
        if (objList != null && objList.size() > 0) {
            WritableWorkbook writebook = null;
            InputStream in = null;
            try {
                WorkbookSettings setEncode = new WorkbookSettings();
                setEncode.setEncoding(UTF8_ENCODING);
                in = new FileInputStream(new File(filePath));
                Workbook workbook = Workbook.getWorkbook(in);
                writebook = Workbook.createWorkbook(new File(filePath), workbook);
                WritableSheet sheet = writebook.getSheet(0);
                for (int row = 0; row < objList.size(); row++) {
                    ArrayList<String> list=(ArrayList<String>) objList.get(row);
                    for (int col = 0; col < list.size(); col++) {
                        sheet.addCell(new Label(col, row+1, list.get(col), arial10format));
                    }
                }
                writebook.write();
                Toast.makeText(c, R.string.export_success, Toast.LENGTH_SHORT).show();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                if (writebook != null) {
                    try {
                        writebook.close();
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (in != null) {
                    try {
                        in.close();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }

    //read from excel
    public static ArrayList<ArrayList<String>> readFromExcel(String filePath) {
        ArrayList<ArrayList<String>> excelInfo = new ArrayList<ArrayList<String>>(50);
        try {
            //filePath="/mnt/sdcard/test.xls"
            InputStream is = new FileInputStream(filePath);
            Workbook workbook = Workbook.getWorkbook(is);
            Sheet sheet = workbook.getSheet(0);
            int rows = sheet.getRows();
            int column = sheet.getColumns();
            //从excel第2行开始导入数据
            for(int r=1;r<rows;r++){
                ArrayList<String> rowlist=new ArrayList<String>();
                for (int c = 0; c<column;c++){
                    //获取每个单元格的内容
                    Cell cell = sheet.getCell(c,r);
					System.out.print("3.zlp-getCell("+r+","+c+") ");
                    String contents = cell.getContents();
					System.out.println(" contents="+contents);
                    rowlist.add(contents);
                }
                excelInfo.add(rowlist);
            }
            workbook.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }
        return excelInfo;
    }

    public static void makeDir(File dir) {
        if (!dir.getParentFile().exists()) {
            makeDir(dir.getParentFile());
        }
        dir.mkdir();
    }

    public static String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();
        }
        String dir = sdDir.toString();
        return dir;

    }
}
