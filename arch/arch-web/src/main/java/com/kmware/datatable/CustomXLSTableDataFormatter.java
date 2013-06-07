package com.kmware.datatable;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.openfaces.component.table.TableData;
import org.openfaces.component.table.TableRowData;
import org.openfaces.component.table.XLSXTableDataFormatter;

public class CustomXLSTableDataFormatter extends XLSXTableDataFormatter{
	
	@Override
	    protected void writeFileContent(TableData tableData, PrintWriter writer, OutputStream outputStream) {
		 	HSSFWorkbook wb = new HSSFWorkbook();
	        CreationHelper helper = wb.getCreationHelper();
	        Sheet sheet = null;  
	        List<TableRowData> rowDatas = tableData.getTableRowDatas();
	        List<String> headers = tableData.getTableColumnDatas();
	        int r = 0;
	        int s = 0;
	        for (TableRowData rowData : rowDatas) {
	        	if(r%1000 == 0){
	        		r = 0;
	        		sheet = wb.createSheet("sheet "+s++);
	        		Row hrow = sheet.createRow(r++);
	        		int hc =0;
	        		for (String  header : headers) {
						Cell hcell = hrow.createCell(hc);
						hcell.setCellValue(header);
						hc++;
					}
	        	}
	        	Row row = sheet.createRow(r++);
	        	List<Object> datas = rowData.getCellDatas();
	        	if(datas!=null){
	        		int c = 0;
		        	for (Object o : datas) {
		        		Cell cell = row.createCell(c);
		        		cell.setCellValue(helper.createRichTextString(o.toString()));
		        		c++;
		        	}	
	        	}
				
	        }
	        try {
				wb.write(outputStream);
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
	
	
	@Override
	public void sendResponse(FacesContext context, TableData tableData, String fileName) {
	        boolean binaryContent = isBinaryContent();
	        StringWriter stringWriter = !binaryContent ? new StringWriter() : new StringWriter();
	        ByteArrayOutputStream byteArrayOutputStream = binaryContent ? new ByteArrayOutputStream() : null;
	        PrintWriter printWriter = new PrintWriter(stringWriter);
	        writeFileContent(tableData, printWriter, byteArrayOutputStream);
	        printWriter.flush();

	        HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
	        response.setContentType(getContentType());
	        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

	        if (!binaryContent) {
	            String stringFileContent = stringWriter.toString();
	            PrintWriter responseWriter;
	            try {
	                responseWriter = response.getWriter();
	            } catch (IOException e) {
	                throw new RuntimeException(e);
	            }
	            responseWriter.write(stringFileContent);
	            responseWriter.close();
	        } else {
	            byte[] binaryFileContent = byteArrayOutputStream.toByteArray();
	            OutputStream responseOutputStream;
	            try {
	                responseOutputStream = response.getOutputStream();
	                responseOutputStream.write(binaryFileContent);
	                responseOutputStream.flush();
	                responseOutputStream.close();
	            } catch (IOException e) {
	                throw new RuntimeException(e);
	            }
	        }
	        context.responseComplete();
	    }
}
