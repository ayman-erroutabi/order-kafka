package order.orderkafka.services.impl;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfDiv;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;
import order.orderkafka.constants.PdfConstants;
import order.orderkafka.dtos.OrderDTO;
import order.orderkafka.dtos.OrderItemDTO;
import order.orderkafka.services.InvoiceService;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.stream.Stream;


@Service
@Slf4j
public class InvoiceServiceImpl implements InvoiceService {

    @Override
    public File createInvoiceFile(String nameFile, OrderDTO order) throws FileNotFoundException, DocumentException {
        log.warn(new File("").getAbsolutePath());
        File file = new File(PdfConstants.INVOICES_PATH + nameFile+ PdfConstants.PDF_EXTENSION);
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(file));

        document.open();
        PdfDiv masterDiv = new PdfDiv();

        /* Setting Invoice Headline */
        PdfDiv divHeader = getHeaderDiv();

        /* Setting text */
        PdfDiv divText = getTextDiv(order);

        /* Setting Invoice Table */
        PdfDiv divTable = getTableDiv(order);

        /* regroup all other divs */
        masterDiv.addElement(divHeader);
        masterDiv.addElement(divText);
        masterDiv.addElement(divTable);
        document.add(masterDiv);
        document.close();
        return file;
    }

    private PdfDiv getTableDiv(OrderDTO order) {
        PdfDiv divTable = new PdfDiv();
        PdfPTable table = new PdfPTable(4);
        addTableHeader(table);
        order.getOrderItemEntities().forEach( orderItem -> addRows(orderItem,table));
        table.setWidthPercentage(90);
        divTable.addElement(table);
        divTable.setPaddingTop(100f);
        return divTable;
    }

    private PdfDiv getTextDiv(OrderDTO order) {
        PdfDiv divText = new PdfDiv();
        Font font = FontFactory.getFont(FontFactory.TIMES, 16, BaseColor.BLACK);
        String text = String.format(PdfConstants.ORDER_FIRST_PARAGRAPH_TEXT, order.getCustomer().getCustomerName());
        Chunk firstChunk = new Chunk(text, font);
        divText.addElement(firstChunk);
        Chunk secondChunk = new Chunk(PdfConstants.ORDER_SECOND_PARAGRAPH_TEXT, font);
        divText.addElement(secondChunk);
        divText.setPaddingTop(50f);
        return divText;
    }

    private PdfDiv getHeaderDiv() {
        PdfDiv divHeader = new PdfDiv();
        Phrase phrase = new Phrase();
        phrase.add(new Chunk(PdfConstants.ORDER_INVOICE_TEXT, FontFactory.getFont(FontFactory.TIMES,22,  Font.BOLD, BaseColor.BLACK)).setUnderline(2f, -4f));
        Paragraph para = new Paragraph();
        para.add(phrase);
        para.setAlignment(Element.ALIGN_CENTER);
        divHeader.addElement(para);
        return divHeader;
    }

    private void addTableHeader(PdfPTable table) {
        PdfPCell emptyHeaderCell = new PdfPCell();
        emptyHeaderCell.setBorder(0);
        table.addCell(emptyHeaderCell);
        Stream.of(PdfConstants.PRICE_PER_UNIT_COLUMN, PdfConstants.QUANTITY_COLUMN, PdfConstants.AMOUNT_COLUMN)
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }

    private void addRows(OrderItemDTO orderItem, PdfPTable table) {
        table.addCell(createCell(orderItem.getProductEntity().getProductName()));
        table.addCell(createCell(String.valueOf(Math.round(orderItem.getProductEntity().getPrice() * 100.0) / 100.0)));
        table.addCell(createCell(String.valueOf(orderItem.getQuantity())));
        double amountPrice = Math.round(orderItem.getProductEntity().getPrice() * orderItem.getQuantity() * 100.0) / 100.0;
        table.addCell(createCell(String.valueOf(amountPrice)));
    }

    private PdfPCell createCell( String text)  {
        PdfPCell cell = new PdfPCell();
        cell.setPhrase(new Phrase(text));
        return cell;
    }

}
