package org.example;


import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Main {
    public static class Immatriculation {
        private String immatriculation1;
        private String immatriculation2;
        private Image logoRm;
        private Image transparentLogoRm;

        public Immatriculation(String immatriculation1, String immatriculation2, Image logoRm, Image transparentLogoRm) {
            this.immatriculation1 = immatriculation1;
            this.immatriculation2 = immatriculation2;
            this.logoRm = logoRm;
            this.transparentLogoRm = transparentLogoRm;
        }

        public String getImmatriculation1() {
            return immatriculation1;
        }

        public String getImmatriculation2() {
            return immatriculation2;
        }

        public Image getLogoRm() {
            return logoRm;
        }

        public Image getTransparentLogoRm() {
            return transparentLogoRm;
        }
    }

    public static void main(String[] args) {
        try {
            // Load the JRXML file from the file system
            File reportFile = new File("src/main/resources/blank_a4.jrxml");
            InputStream reportStream = new FileInputStream(reportFile);
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

            // Load images
            Image logoRm = ImageIO.read(new File("src/main/resources/images/logoRm.png"));
            Image transparentLogoRm = ImageIO.read(new File("src/main/resources/images/transparentLogoRm.png"));

            // Create data source
            List<Immatriculation> dataList = new ArrayList<>();
            for (int i = 1; i <= 9; i++) {
                dataList.add(new Immatriculation("Label - " + i, "Label - " + i, logoRm, transparentLogoRm));
            }
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(dataList);

            // Parameters map
            Map<String, Object> parameters = new HashMap<>();

            // Fill the report
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            // Export the report to a PDF file
            JasperExportManager.exportReportToPdfFile(jasperPrint, "report.pdf");

            System.out.println("Report generated successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}