
package Model;

import java.io.FileOutputStream;
import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import DAO.emergenciaDAO;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class Relatorio {

    private emergenciaDAO emergenciaDAO;

	// criação do documento
	public Relatorio() {
		// TODO Auto-generated constructor stub
	  Document doc = null;
      FileOutputStream os = null;
		
      emergenciaDAO = new emergenciaDAO();
      
        
      try {
      		//cria o documento tamanho A4, margens de 2,54cm
    	  doc = new Document(PageSize.A4, 0, 0, 72, 72);

             
//              PdfWriter.getInstance(doc, new FileOutputStream("C:\\Users\\ThiMary\\Desktop\\Relatorio.pdf"));             
              PdfWriter.getInstance(doc, new FileOutputStream("reports\\Relatorio.pdf"));             
              ResultSet dataBd = emergenciaDAO.ListaResultados();
            
             doc.open();
         	
             //adiciona o texto ao PDF
             
             Font headerFont = new Font(FontFamily.TIMES_ROMAN, 20, Font.BOLDITALIC);
             Font subHeaderFont = new Font(FontFamily.TIMES_ROMAN, 20, Font.ITALIC);

             Paragraph header = new Paragraph("FIRE SIMULATOR 11-2013",headerFont);
             header.setAlignment(Element.ALIGN_CENTER);
             header.setSpacingAfter(20);
             Paragraph subHeader = new Paragraph("Relatório de Emergências",subHeaderFont);
             subHeader.setAlignment(Element.ALIGN_CENTER);
             subHeader.setSpacingAfter(20);
             doc.add(header); 
             doc.add(subHeader); 
             
             Font metaDataFont = new Font(FontFamily.TIMES_ROMAN, 12, Font.BOLD);
             	PdfPTable table = new PdfPTable(new float[] { 1.4f, 0.9f,1.2f, 1.1f, 1.0f,0.6f, 0.6f });
	            PdfPCell col2 = new PdfPCell(new Paragraph("STATUS DA MISSÃO" ,metaDataFont));
	            PdfPCell col3 = new PdfPCell(new Paragraph("VIATURA" ,metaDataFont));
	            PdfPCell col4 = new PdfPCell(new Paragraph("CONTROLE",metaDataFont));
	            PdfPCell col5= new PdfPCell(new Paragraph("DATA" ,metaDataFont));
	            PdfPCell col6 = new PdfPCell(new Paragraph("DURAÇÃO" ,metaDataFont));
	            PdfPCell col7 = new PdfPCell(new Paragraph("EIXO X " ,metaDataFont));
	            PdfPCell col8 = new PdfPCell(new Paragraph("EIXO Y " ,metaDataFont));
             
             try{

 	            table.addCell(col2);
 	            table.addCell(col3);
 	            table.addCell(col4);
 	            table.addCell(col5);
 	            table.addCell(col6);
 	            table.addCell(col7);
 	            table.addCell(col8);
 				
 	            while (dataBd.next()){
 	           
 	            	String cell2 = dataBd.getString(2);
 	            	String cell3 = dataBd.getString(3);
 	            	String cell4 = dataBd.getString(4);
 	            	String cell5 = dataBd.getString(5);
 	            	String cell6 = dataBd.getString(6);
 	            	String cell7 = dataBd.getString(7);
 	            	String cell8 = dataBd.getString(8);

 	            	table.addCell(cell2);
 	            	table.addCell(cell3);
 	            	table.addCell(cell4);
 	            	table.addCell(cell5);
 	            	table.addCell(cell6);
 	            	table.addCell(cell7);
 	            	table.addCell(cell8);
 	            	
 	            }
 	            doc.add(table);

             JOptionPane.showMessageDialog(null, "Relatorio Gerado com sucesso!!!");

         } finally {
             if (doc != null) {
                 //fechamento do documento
                 doc.close();
             }
             if (os != null) {
                //fechamento da stream de saída
                os.close();
             }
         }
             
             
          }
          catch(DocumentException de) {
              System.err.println(de.getMessage());
          }
          catch(IOException ioe) {
        	  JOptionPane.showMessageDialog(null, ioe.getMessage());
          } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
          doc.close();
      }   

	public ResultSet ListaEmergencia() throws Exception {  

		Connection connection = null;
		String query;
		PreparedStatement stm;
		ResultSet rs;  
		try{

			emergenciaDAO.open();
			query = "SELECT emrg.idEmergencia, emrg.statusMissao, cci.NomeCCI, emrg.controleViatura, emrg.data_emergencia, emrg.tempo, emrg.localizacaoX,emrg.localizacaoY FROM emergencia emrg, cci cci WHERE viaturaUtilizada = idCCI ORDER BY emrg.idEmergencia ASC;";
			
			stm = connection.prepareStatement(query);
			
			rs = stm.executeQuery();				  

	  
		} catch (SQLException e) {  
			e.printStackTrace();  
			throw new Exception("Erro ao Listar Emergencias");  
		}  
			return rs;
		 }
}

