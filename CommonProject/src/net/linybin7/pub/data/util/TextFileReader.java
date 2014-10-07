package net.linybin7.pub.data.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 *
 * <p>Copyright: 2012 . All rights reserved.</p>
 * <p>Company: eshine</p>
 * <p>CreateDate:2012-12-28</p>
 * @author LinYuBin 
 * <p>----------------------------------------------</p>
 * <p>Date			Mender			Reason</p>
 */
public class TextFileReader{
	private int currentLine;
	FileReader fr = null;
	BufferedReader br = null;
	
	Workbook work = null;
	Sheet sheet = null;
	InputStream in = null;
	int fileType=1;//1:excel	2:txt	3:csv	0:error
	File file;
	private int fileLine=0;
	
	private int excelSheet=0;
	
	public TextFileReader(File file) throws Exception{
		String nameStr=file.getName();
		this.file=file;
		if (isXls(nameStr)) {
			try {
				in = new FileInputStream(file);
				work = new HSSFWorkbook(in);
			} catch (Exception e) {
				in = new FileInputStream(file);
				work = new XSSFWorkbook(in);
			}
			sheet = work.getSheetAt(0);
			currentLine=0;
			fileType=1;
			fileLine=sheet.getPhysicalNumberOfRows();
		} else if (isTxt(nameStr)) {
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			currentLine=0;
			fileType=2;
			fileLine=readFileLine(file);
		} else if (isCsv(nameStr)) {
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			currentLine=0;
			fileType=3;
			fileLine=readFileLine(file);
		} else{
			fileType=0;
		}
	}
	
	public String[] readLine()throws Exception{
		if(fileType==2){
			String title = br.readLine(); // 文件头解析
			currentLine++;
			String[] titles = title.split("	");
			return titles;
		}if(fileType==3){
			String title = br.readLine(); // 文件头解析
			currentLine++;
			String[] titles = title.split(",");
			return titles;
		}else if(fileType==1){
			Row row = sheet.getRow(currentLine);
			String[] s = new String[row.getLastCellNum()];
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for (int j = 0; j < row.getLastCellNum(); j++) {
				String cellValue = "";
				Cell cell = row.getCell(j);
				if (cell != null) {
					if (Cell.CELL_TYPE_STRING == cell.getCellType()) {
						cellValue = cell.getStringCellValue();
					} else if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()) {
						if (HSSFDateUtil.isCellDateFormatted(cell)) {
							double d = cell.getNumericCellValue();
							Date date = HSSFDateUtil.getJavaDate(d);
							cellValue = sdf.format(date);
						} else {
							cellValue = cell.getNumericCellValue() + "";
						}
					}
				}
				s[j] = cellValue;
			}
			currentLine++;
			return s;
		}else{
			throw new Exception("文件类型错误!" );
		}
	}
	
	public boolean hasLine() throws Exception{
		return currentLine<fileLine;
	}
	
	public int getFileLine(){
		return fileLine;
	}
	
	public void close(){
		if (in != null) {
			try {
				in.close();
			} catch (Exception e) {
			}
		}
		try {
			if (br != null) {
				br.close();
			}
			if (fr != null) {
				fr.close();
			}
		} catch (Exception e1) {
		}
	}

	private int readFileLine(File f) throws IOException {
		int count = 0;
		// File f = new File(strFile);
		InputStream input = new FileInputStream(f);

		BufferedReader b = new BufferedReader(new InputStreamReader(input));

		String value = b.readLine();
		if (value != null)

			while (value != null) {
				count++;
				value = b.readLine();
			}
		b.close();
		input.close();
		return count;
	}
	
	public static boolean isSupportFileType(String fileName){
		return (isTxt(fileName)||isXls(fileName)||isCsv(fileName));
	}

	public static boolean isTxt(String fileName) {
		Pattern pattern = Pattern.compile("^.+\\.(?i)(txt)$");
		return pattern.matcher(fileName).matches();
	}
	
	public static boolean isCsv(String fileName) {
		Pattern pattern = Pattern.compile("^.+\\.(?i)(csv)$");
		return pattern.matcher(fileName).matches();
	}

	public static boolean isXls(String fileName) {
		Pattern pattern = Pattern.compile("^.+\\.(?i)((xls)|(xlsx))");
		return pattern.matcher(fileName).matches();
	}

	public int getExcelSheet() {
		return excelSheet;
	}

	public void setExcelSheet(int excelSheet) throws Exception {
		if(fileType!=1){
			throw new Exception("文件格式不支持");
		}
		this.excelSheet = excelSheet;
	}

	public int getCurrentLine() {
		return currentLine;
	}

	public void setCurrentLine(int currentLine) throws Exception {
		if(fileType!=1){
			throw new Exception("文件格式不支持");
		}
		this.currentLine = currentLine;
	}
	
	
}