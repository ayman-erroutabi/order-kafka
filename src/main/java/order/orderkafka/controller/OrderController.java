package order.orderkafka.controller;

import lombok.extern.slf4j.Slf4j;
import order.orderkafka.dtos.OrderDTO;
import order.orderkafka.dtos.RestExceptionDTO;
import order.orderkafka.dtos.requests.OrderRequest;
import order.orderkafka.exceptions.ProductOutOfStockException;
import order.orderkafka.services.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

@RestController
@RequestMapping("/api/orders")
@Slf4j
public class OrderController {

    private final OrderService orderService;

    OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    @GetMapping("/{id}")
    public OrderDTO getOrderById(@PathVariable long id){
        return this.orderService.getOrderById(id);
    }

    @PostMapping("/create")
    public ResponseEntity<Object> creationOrder(@RequestBody OrderRequest orderRequest) {
        // Call the asynchronous createOrder method in the service
        try {
            // Call the asynchronous createOrder method in the service
            CompletableFuture<OrderDTO> future = this.orderService.createOrder(orderRequest);

            // Wait for the completion of the CompletableFuture and handle exceptions
            OrderDTO result = future.join();

            // Return the result in the ResponseEntity
            return ResponseEntity.ok(result);
        } catch (CompletionException | ProductOutOfStockException e) {
            // Handle exceptions thrown during the asynchronous computation
            Throwable cause = (e.getCause() != null) ? e.getCause() : e;
            log.error(cause.getMessage());

            RestExceptionDTO exceptionDTO = new RestExceptionDTO();
            exceptionDTO.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            exceptionDTO.setMessage(cause.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(exceptionDTO);
        }
    }
}
