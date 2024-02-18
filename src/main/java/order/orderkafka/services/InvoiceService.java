package ma.therightman.orderkafka.services;

import com.itextpdf.text.DocumentException;
import ma.therightman.orderkafka.dtos.OrderDTO;

import java.io.File;
import java.io.FileNotFoundException;

public interface InvoiceService {

    File createInvoiceFile(String nameFile, OrderDTO order) throws FileNotFoundException, DocumentException;
}
