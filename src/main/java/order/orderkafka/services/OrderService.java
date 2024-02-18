package order.orderkafka.services;

import com.itextpdf.text.DocumentException;
import order.orderkafka.dtos.OrderDTO;
import order.orderkafka.dtos.requests.OrderRequest;
import order.orderkafka.entities.OrderEntity;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public interface OrderService {

    OrderDTO getOrderById(long id);

    CompletableFuture<OrderDTO> createOrder(OrderRequest orderRequest);

    void processOrderOk(OrderDTO orderDTO) throws IOException, MessagingException, DocumentException;

    void processOrderNotOk(OrderDTO orderDTO);

    OrderEntity buildOrder(OrderDTO orderDTO);
}
