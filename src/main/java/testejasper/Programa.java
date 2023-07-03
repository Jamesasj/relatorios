package testejasper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;


public class Programa {

	private static void compileJrxml(String jrxmlFilePath) throws IOException {
		try {
			// Carrega o arquivo JRXML em um objeto JasperDesign
			JasperDesign jasperDesign = JRXmlLoader.load(jrxmlFilePath);

			// Configura a conexão com o banco de dados
			// Exemplo: jasperDesign.setConnection(connection);

			// Compila o objeto JasperDesign para o formato Jasper (.jasper)
			JasperCompileManager.compileReportToFile(jasperDesign, "rel/Wave_Book.jasper");

			System.out.println("Compilação concluída com sucesso!");
		} catch (Exception e) {
			System.out.println("Ocorreu um erro durante a compilação do arquivo JRXML: " + e.getMessage());
			e.printStackTrace();
			throw new IOException("Erro ao compilar o arquivo JRXML");
		}
	}


    public static void main(String[] args) {
    	
        try {
            // Carrega o arquivo .jasper do relatório
            String jasperFilePath = "src/main/resources/Wave_Book.jrxml";
          /// compileJrxml(jasperFilePath);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperFilePath);

            // Criação dos parâmetros do relatório, se necessário
            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("BookTitle", "Book Title here James");
            parameters.put("param2", "valor2");

            // Criação da fonte de dados do relatório, se necessário
            JRDataSource dataSource = new JREmptyDataSource();

            // Compilação do relatório
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            // Exportação do relatório para PDF
            String outputFilePath = "src/main/resources/arquivo4.pdf";
            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(new File(outputFilePath)));
            exporter.exportReport();

            System.out.println("Relatório gerado com sucesso: " + outputFilePath);
        } catch (Exception e) {
            System.out.println("Ocorreu um erro ao gerar o relatório: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
