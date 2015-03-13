package com.jft.utils;
import java.io.File;


import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;


public class ExcelUtil {
	
	public static int getColoumNumber(Sheet sheet,String sFieldName) {

		//Get the total number of columns
		int iTotalNumberOfColoums = sheet.getColumns();
		for( int i = 0 ; i < iTotalNumberOfColoums ; i++)
			if(sheet.getCell(i, 0).getContents().equalsIgnoreCase(sFieldName))
				return i;
		return -1;
	}

	//
	public static int getColoumNumber(String[][] sheet,String sFieldName) {

		//Get the total number of columns
		int iTotalNumberOfColoums = sheet[0].length;
		for( int i = 0 ; i < iTotalNumberOfColoums ; i++)
			if(sheet[0][i].equalsIgnoreCase(sFieldName))
				return i;
		return -1;
	}

	//Return excel as an array
	public static String[][] sheet_to_array(Sheet sheet) throws Exception {

		String[][] sheet_array;
		int total_col = 0;
		int total_row = 0;

		total_col = sheet.getColumns();
		total_row = sheet.getRows();

		sheet_array = new String[total_row][total_col];

		for(int i=0;i< total_row; i++)
			for(int j=0; j< total_col; j++)
				sheet_array[i][j] = sheet.getCell(j,i).getContents().trim();
		return sheet_array;
	}



	public static void write_to_file(String[][] content, String filename) throws Exception {
		int i,j = 0;

		WritableWorkbook output = Workbook.createWorkbook(new File(filename));
		WritableSheet sheet = output.createSheet("name", 0);

		for(i=0; i<content.length; i++)
			for(j=0;j<content[i].length;j++) {
				Label label = new Label(j, i, content[i][j]);
				//Treat the first row as heading
				if(i==0) {
    				WritableFont boldRedFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
				    label.setCellFormat(new WritableCellFormat(boldRedFont));
				}
				sheet.addCell(label);
			}
		output.write();
		output.close();
	}

}
