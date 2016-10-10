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
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

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
		//��ʽ
		HSSFCellStyle style = workbook.createCellStyle();
		style.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
		HSSFCellStyle style1 = workbook.createCellStyle();
		//����
		HSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short) 10);
		style.setFont(font);
		style1.setFont(font);

		HSSFRow row0 = xsheet.createRow(0);
		for (int col = 0; col<colName.length; col++){
//			if (col==7) {
//				CellRangeAddress cra = new CellRangeAddress(0,0,col,col+2);
//				xsheet.addMergedRegion(cra);
//			}
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
			//����
			HSSFFont font = workbook.createFont();
			font.setFontHeightInPoints((short) 10);
			style1.setFont(font);

			for (int row = 0; row < objList.size(); row++) {
				HSSFRow xrow = sheet.createRow(row+1);
				ArrayList<String> list = (ArrayList<String>) objList.get(row);
				for (int col = 0; col < list.size(); col++) {
					System.out.println("zlp--poi-row="+row+",col="+col+",length="+list.get(col).length());
					String value = list.get(col);
					HSSFCell cell = xrow.createCell(col);
					cell.setCellStyle(style1);
					cell.setCellValue(value);
//					if (col==7) {
//						CellRangeAddress cra = new CellRangeAddress(row,row,col,col+2);
//						sheet.addMergedRegion(cra);
//					}
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
	 * ����excel�ļ�������ArrayList
	 *
	 * @param
	 * @throws IOException
	 */
	public static ArrayList<ArrayList<String>> readFromExcel(String filePath) {
		// String filePath = /storage/emulated/0/SampleTable.xls
		ArrayList<ArrayList<String>> excelInfo = new ArrayList<ArrayList<String>>(50);
		Log.d("POI-readFromExcel-zlp", filePath);
		Workbook workbook = getWorkBook(filePath);
		Sheet sheet = workbook.getSheetAt(0);
		Row row = sheet.getRow(0);
		int rowNumber = sheet.getPhysicalNumberOfRows();
		int columnNumber = row.getPhysicalNumberOfCells();
		// ��excel��2�п�ʼ��������
		for (int i = 1; i < rowNumber; i++) {
			ArrayList<String> rowList = new ArrayList<String>();
			for (int j = 0; j < columnNumber; j++) {
				String cellValue;
//				if (j==7){
//					cellValue = getMergedRegionValues(sheet,i,j);
//				}
				cellValue = sheet.getRow(i).getCell(j).getStringCellValue();
				Log.d("POI-zlp-", cellValue);
				rowList.add(cellValue);
			}
			excelInfo.add(rowList);
		}
		try {
			workbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return excelInfo;
	}

	// �����ļ���׺����ͬ(xls��xlsx)��ò�ͬ��Workbookʵ�������
	public static Workbook getWorkBook(String filepath) {
		//����Workbook���������󣬱�ʾ����excel
		Workbook workbook = null;
		try {
			//��ȡexcel�ļ���io��
			InputStream is = new FileInputStream(new File(filepath));
			//�����ļ���׺����ͬ(xls��xlsx)��ò�ͬ��Workbookʵ�������
			if (filepath.endsWith(xls)) {
				//2003
				workbook = new HSSFWorkbook(is);
			} else if (filepath.endsWith(xlsx)) {
				//2007
				workbook = new XSSFWorkbook(is);
			}
			is.close();
			workbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return workbook;
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

	public static String getMergedRegionValues(Sheet sheet, int row, int column){
		CellRangeAddress ca = sheet.getMergedRegion(row);
		int firstCol = ca.getFirstColumn();
		int lastCol = ca.getLastColumn();
		int firstRow = ca.getFirstRow();
		int lastRow = ca.getLastRow();
		String value;
		if(firstCol<=lastCol && firstRow<=lastRow){
				Row fRow = sheet.getRow(firstRow);
				Cell fCell =fRow.getCell(firstCol);
				value = fCell.getStringCellValue();
		}
		else value = "";
		return value;
	}

}
