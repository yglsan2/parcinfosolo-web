package com.parfinfo.service;

import com.parfinfo.exception.ApiException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ExportService {
    private final AppareilService appareilService;
    private final PeripheriqueService peripheriqueService;
    private final MaintenanceService maintenanceService;

    public ExportService(
        AppareilService appareilService,
        PeripheriqueService peripheriqueService,
        MaintenanceService maintenanceService
    ) {
        this.appareilService = appareilService;
        this.peripheriqueService = peripheriqueService;
        this.maintenanceService = maintenanceService;
    }

    public byte[] exportToExcel(String type, String format) {
        try {
            Workbook workbook = "xlsx".equals(format) ? new XSSFWorkbook() : new HSSFWorkbook();
            Sheet sheet = workbook.createSheet(type);

            // Création du style pour l'en-tête
            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);

            // Création des en-têtes et des données selon le type
            switch (type.toLowerCase()) {
                case "appareils":
                    createAppareilsSheet(sheet, headerStyle);
                    break;
                case "peripheriques":
                    createPeripheriquesSheet(sheet, headerStyle);
                    break;
                case "maintenances":
                    createMaintenancesSheet(sheet, headerStyle);
                    break;
                default:
                    throw new ApiException.Builder()
                        .message("Type d'export non supporté")
                        .status(HttpStatus.BAD_REQUEST)
                        .code("INVALID_EXPORT_TYPE")
                        .build();
            }

            // Ajustement automatique de la largeur des colonnes
            for (int i = 0; i < sheet.getRow(0).getLastCellNum(); i++) {
                sheet.autoSizeColumn(i);
            }

            // Conversion en tableau de bytes
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            workbook.close();

            return outputStream.toByteArray();
        } catch (IOException e) {
            throw new ApiException.Builder()
                .message("Erreur lors de la création du fichier Excel")
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .code("EXCEL_CREATION_ERROR")
                .details(e.getMessage())
                .build();
        }
    }

    private void createAppareilsSheet(Sheet sheet, CellStyle headerStyle) {
        // Création des en-têtes
        Row headerRow = sheet.createRow(0);
        String[] headers = {"ID", "Nom", "Type", "État", "Numéro de série", "Numéro d'inventaire", 
                          "Date d'acquisition", "Date de fin de garantie", "Coût d'acquisition", 
                          "Fournisseur", "Emplacement", "Responsable"};
        
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }

        // Récupération et ajout des données
        List<Appareil> appareils = appareilService.findAll();
        int rowNum = 1;
        for (Appareil appareil : appareils) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(appareil.getId());
            row.createCell(1).setCellValue(appareil.getNom());
            row.createCell(2).setCellValue(appareil.getType().toString());
            row.createCell(3).setCellValue(appareil.getEtat().toString());
            row.createCell(4).setCellValue(appareil.getNumeroSerie());
            row.createCell(5).setCellValue(appareil.getNumeroInventaire());
            row.createCell(6).setCellValue(appareil.getDateAcquisition().toString());
            row.createCell(7).setCellValue(appareil.getDateFinGarantie().toString());
            row.createCell(8).setCellValue(appareil.getCoutAcquisition().doubleValue());
            row.createCell(9).setCellValue(appareil.getFournisseur());
            row.createCell(10).setCellValue(appareil.getEmplacement());
            row.createCell(11).setCellValue(appareil.getResponsable());
        }
    }

    private void createPeripheriquesSheet(Sheet sheet, CellStyle headerStyle) {
        // Création des en-têtes
        Row headerRow = sheet.createRow(0);
        String[] headers = {"ID", "Nom", "Type", "État", "Numéro de série", "Numéro d'inventaire", 
                          "Date d'acquisition", "Date de fin de garantie", "Coût d'acquisition", 
                          "Fournisseur", "Emplacement", "Responsable", "Appareil associé"};
        
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }

        // Récupération et ajout des données
        List<Peripherique> peripheriques = peripheriqueService.findAll();
        int rowNum = 1;
        for (Peripherique peripherique : peripheriques) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(peripherique.getId());
            row.createCell(1).setCellValue(peripherique.getNom());
            row.createCell(2).setCellValue(peripherique.getType().toString());
            row.createCell(3).setCellValue(peripherique.getEtat().toString());
            row.createCell(4).setCellValue(peripherique.getNumeroSerie());
            row.createCell(5).setCellValue(peripherique.getNumeroInventaire());
            row.createCell(6).setCellValue(peripherique.getDateAcquisition().toString());
            row.createCell(7).setCellValue(peripherique.getDateFinGarantie().toString());
            row.createCell(8).setCellValue(peripherique.getCoutAcquisition().doubleValue());
            row.createCell(9).setCellValue(peripherique.getFournisseur());
            row.createCell(10).setCellValue(peripherique.getEmplacement());
            row.createCell(11).setCellValue(peripherique.getResponsable());
            row.createCell(12).setCellValue(peripherique.getAppareil() != null ? 
                peripherique.getAppareil().getNom() : "");
        }
    }

    private void createMaintenancesSheet(Sheet sheet, CellStyle headerStyle) {
        // Création des en-têtes
        Row headerRow = sheet.createRow(0);
        String[] headers = {"ID", "Type", "Date", "Description", "Coût", "Technicien", 
                          "Équipement", "Type d'équipement"};
        
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }

        // Récupération et ajout des données
        List<Maintenance> maintenances = maintenanceService.findAll();
        int rowNum = 1;
        for (Maintenance maintenance : maintenances) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(maintenance.getId());
            row.createCell(1).setCellValue(maintenance.getType().toString());
            row.createCell(2).setCellValue(maintenance.getDateMaintenance().toString());
            row.createCell(3).setCellValue(maintenance.getDescription());
            row.createCell(4).setCellValue(maintenance.getCout().doubleValue());
            row.createCell(5).setCellValue(maintenance.getTechnicien());
            
            if (maintenance.getAppareil() != null) {
                row.createCell(6).setCellValue(maintenance.getAppareil().getNom());
                row.createCell(7).setCellValue("Appareil");
            } else if (maintenance.getPeripherique() != null) {
                row.createCell(6).setCellValue(maintenance.getPeripherique().getNom());
                row.createCell(7).setCellValue("Périphérique");
            }
        }
    }
} 