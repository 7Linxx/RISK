package co.edu.unbosque.model.persistence;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class FileHandler {

	private static Document document;
	private static FileOutputStream fos;
	private static FileInputStream fis;
	private static ZipOutputStream zos;
	private static Paragraph title;
	private static Paragraph text;
	
	public FileHandler() {}

	public static void createPDF(String pathPdf,String pathImg,String header, String content,String content2,String[] items) {
		document = new Document();
		try {
			fos = new FileOutputStream(pathPdf);
			PdfWriter.getInstance(document, fos);
			document.open();
			title = new Paragraph(header);
			title.setAlignment(1);
			document.add(title);
			document.add(Chunk.NEWLINE);
			text = new Paragraph(content);
			text.setAlignment(Element.ALIGN_JUSTIFIED);
			document.add(text);
			if(items.length>0) {
				document.add(Chunk.NEWLINE);
				document.add(new Paragraph(items[0]));
				List list=new List();
				for (int i = 1; i < items.length; i++) {
					list.add(new ListItem(items[i]));
				}
				document.add(list);
				document.add(Chunk.NEWLINE);
			}
			document.add(new Paragraph(content2));
			document.add(Chunk.NEWLINE);
			Image img = Image.getInstance(pathImg);
			img.scaleToFit(400, 400);
			img.setAlignment(1);
			document.add(img);
			document.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public static void createZIP(String pathPdf,String pathZip) {
		try {
			File pdfFile=new File(pathPdf);
			File zipFile=new File(pathZip);
			
			fis=new FileInputStream(pdfFile);
			fos=new FileOutputStream(zipFile);
			zos=new ZipOutputStream(fos);
			
			ZipEntry zipEntry=new ZipEntry(pdfFile.getName());
			zos.putNextEntry(zipEntry);
			
			byte[] bytes=new byte[1024];
			int length;
			while((length=fis.read(bytes))>=0) {
				zos.write(bytes,0,length);
			}
			zos.closeEntry();
			fis.close();
			zos.close();
			fos.close();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void generateZIP(String pathPdf,String pathZip,String pathImg,String header, String content,String content2,String[] items) {
		createPDF(pathPdf,pathImg, header, content,content2,items);
		createZIP(pathPdf, pathZip);
	}

}
