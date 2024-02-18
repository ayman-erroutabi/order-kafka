package ma.therightman.orderkafka.services;

import com.itextpdf.text.DocumentException;
import ma.therightman.orderkafka.dtos.OrderDTO;
import ma.therightman.orderkafka.dtos.requests.OrderRequest;
import ma.therightman.orderkafka.entities.OrderEntity;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public interface OrderService {

    OrderDTO getOrderById(long id);

    CompletableFuture<OrderDTO> createOrder(OrderRequest orderRequest);

    CompletableFuture<OrderDTO> processOrderOk(OrderDTO orderDTO) throws IOException, MessagingException, DocumentException;

    CompletableFuture<OrderDTO> processOrderNotOk(OrderDTO orderDTO);

    OrderEntity buildOrder(OrderDTO orderDTO);
}
