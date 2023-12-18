package com.example.db_mai.service;

import com.example.db_mai.dto.order.ComponentOrderInfoDto;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class ReceiptService {

    public static final String FONT = "./src/main/resources/fonts/FreeSans.ttf";

    public ByteArrayOutputStream generateReceipt(List<ComponentOrderInfoDto> componentOrderList, Double totalCost, Timestamp date) {
        Document document = new Document();
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        BaseFont bf = null;
        try {
            bf = BaseFont.createFont(FONT, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Font font = new Font(bf, 30, Font.NORMAL);

        try {
            PdfWriter.getInstance(document, byteStream);
            document.open();

            // Заголовок чека
            Font titleFont = new Font(bf, 18, Font.BOLD);
            Paragraph title = new Paragraph("Товарный чек", titleFont);
            title.setAlignment(Paragraph.ALIGN_CENTER);
            title.setSpacingAfter(15);
            document.add(title);

            Font standartFont = new Font(bf, 12, Font.NORMAL);

            // Дата заказа
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
            String orderDate = dateFormat.format(date);
            Paragraph dateParagraph = new Paragraph("Дата заказа: " + orderDate, standartFont);
            document.add(dateParagraph);

            // Информация о компонентах заказа
            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            Font headerFont = new Font(bf, 12, Font.BOLD);
            addCell(table, "Код товара", headerFont);
            addCell(table, "Модель", headerFont);
            addCell(table, "Цена", headerFont);
            addCell(table, "Количество", headerFont);

            for (ComponentOrderInfoDto componentOrder : componentOrderList) {
                addCell(table, componentOrder.getSku(), standartFont);
                addCell(table, componentOrder.getModel(), standartFont);
                addCell(table, String.valueOf(componentOrder.getPrice()), standartFont);
                addCell(table, String.valueOf(componentOrder.getQuantity()), standartFont);
            }

            document.add(table);

            // Итоговая стоимость
            Font totalCostFont = new Font(bf, 14, Font.BOLD);
            Paragraph totalCostParagraph = new Paragraph("Итого: " + totalCost, totalCostFont);
            totalCostParagraph.setAlignment(Paragraph.ALIGN_RIGHT);
            document.add(totalCostParagraph);

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
        return byteStream;
    }

    private void addCell(PdfPTable table, String text) {
        PdfPCell cell = new PdfPCell(new Phrase(text));
        table.addCell(cell);
    }

    private void addCell(PdfPTable table, String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        table.addCell(cell);
    }
}
