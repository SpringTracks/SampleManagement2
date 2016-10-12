package com.lge.excel;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liping.zhu on 2016/9/30.
 */
public class PoiExcelUtils {
	private static String xls = "xls";
	private static String xlsx = "xlsx";

	public static String initExcel(String talbeName, String[] colName) {
		String path = getSDPath() + "/SampleManagement/";
		File dir = new File(path);
		makeDir(dir);
		String filePath = path + talbeName + ".xls";

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet xsheet = workbook.createSheet(talbeName);
		xsheet.setDefaultColumnWidth(10);
		// 样式
		HSSFCellStyle style = workbook.createCellStyle();
		style.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		HSSFCellStyle style1 = workbook.createCellStyle();
		// 字体
		HSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short) 10);
		style.setFont(font);
		style1.setFont(font);

		HSSFRow row0 = xsheet.createRow(0);
		for (int col = 0; col < colName.length; col++) {
			HSSFCell cell = row0.createCell(col);
			cell.setCellStyle(style);
			cell.setCellValue(colName[col]);
		}

		try {
			FileOutputStream fos = new FileOutputStream(new File(filePath));
			workbook.write(fos);
			fos.flush();
			fos.close();
			workbook.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return filePath;
	}

	public static <T> void writeObjListToExcel(List<T> objList, String filePath, Context c) {

		try {
			InputStream in = new FileInputStream(new File(filePath));
			HSSFWorkbook workbook = new HSSFWorkbook(in);
			HSSFSheet sheet = workbook.getSheetAt(0);

			HSSFCellStyle style1 = workbook.createCellStyle();
			// 字体
			HSSFFont font = workbook.createFont();
			font.setFontHeightInPoints((short) 10);
			style1.setFont(font);

			for (int row = 0; row < objList.size(); row++) {
				HSSFRow xrow = sheet.createRow(row + 1);
				ArrayList<String> list = (ArrayList<String>) objList.get(row);
				for (int col = 0; col < list.size(); col++) {
//					System.out.println("zlp--poi-row=" + row + ",col=" + col + ",length=" + list.get(col).length());
					String value = list.get(col);
					HSSFCell cell = xrow.createCell(col);
					cell.setCellStyle(style1);
					cell.setCellValue(value);
				}
			}
			OutputStream os = new FileOutputStream(new File(filePath));
			workbook.write(os);
			os.flush();
			os.close();
			workbook.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 读入excel文件，返回ArrayList
	 *
	 * @param
	 * @throws IOException
	 */
	public static ArrayList<ArrayList<String>> readFromExcel(String filePath) {
		// String filePath = /storage/emulated/0/SampleTable.xls
		ArrayList<ArrayList<String>> excelInfo = new ArrayList<ArrayList<String>>(50);
		Log.d("POI-readFromExcel-zlp", filePath);
		try {
			InputStream is = new FileInputStream(new File(filePath));
			// 根据文件后缀名不同(xls和xlsx)获得不同的Workbook实现类对象
			// if (filePath.endsWith(xls)) {
			// 2003
			HSSFWorkbook workbook = new HSSFWorkbook(is);
			Sheet sheet = workbook.getSheetAt(0);
			Row row = sheet.getRow(0);
			int rowNumber = sheet.getPhysicalNumberOfRows();
			int columnNumber = row.getPhysicalNumberOfCells();
			// 从excel第2行开始导入数据
			for (int i = 1; i < rowNumber; i++) {
				ArrayList<String> rowList = new ArrayList<String>();
				for (int j = 0; j < columnNumber; j++) {
					String cellValue;
					cellValue = sheet.getRow(i).getCell(j).getStringCellValue();
//					Log.d("POI-zlp-" + xls, cellValue);
					rowList.add(cellValue);
				}
				excelInfo.add(rowList);
			}
			try {
				workbook.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			// } else {
			// // 2007
			// Log.d("zlp-"+xlsx, "2007");
			// XSSFWorkbook workbook = new XSSFWorkbook(is);
			// Log.d("zlp"+xlsx, "new workbook success");
			// XSSFSheet sheet = workbook.getSheetAt(0);
			// Row row = sheet.getRow(0);
			// int rowNumber = sheet.getPhysicalNumberOfRows();
			// int columnNumber = row.getPhysicalNumberOfCells();
			// // 从excel第2行开始导入数据
			// for (int i = 1; i < rowNumber; i++) {
			// ArrayList<String> rowList = new ArrayList<String>();
			// Row rowi = sheet.getRow(i);
			// for (int j = 0; j < columnNumber; j++) {
			// String cellValue;
			// cellValue = rowi.getCell(j).getStringCellValue();
			// Log.d("POI-zlp-" +xlsx, cellValue);
			// rowList.add(cellValue);
			// }
			// excelInfo.add(rowList);
			// }
			//
			// }
		} catch (Exception e) {
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
