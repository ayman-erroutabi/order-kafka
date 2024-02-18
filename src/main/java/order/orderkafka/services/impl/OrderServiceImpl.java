package ma.therightman.orderkafka.services.impl;

import com.itextpdf.text.DocumentException;
import lombok.extern.slf4j.Slf4j;
import ma.therightman.orderkafka.dtos.OrderDTO;
import ma.therightman.orderkafka.dtos.OrderItemDTO;
import ma.therightman.orderkafka.dtos.requests.OrderRequest;
import ma.therightman.orderkafka.dtos.requests.ProductRequest;
import ma.therightman.orderkafka.entities.CustomerEntity;
import ma.therightman.orderkafka.entities.OrderEntity;
import ma.therightman.orderkafka.entities.OrderItemEntity;
import ma.therightman.orderkafka.entities.ProductEntity;
import ma.therightman.orderkafka.exceptions.CustomerNotFoundException;
import ma.therightman.orderkafka.exceptions.ProductNotFoundException;
import ma.therightman.orderkafka.exceptions.ProductOutOfStockException;
import ma.therightman.orderkafka.repositories.OrderRepository;
import ma.therightman.orderkafka.services.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static ma.therightman.orderkafka.constants.KafkaConstants.KAFKA_TOPIC_ORDERS;

@Service
@Transactional
@Slf4j
public class OrderServiceImpl implements OrderService {

    public static final String TEST_MAIL_ORDER_STRING = "Test Mail Order ";

    private final OrderRepository orderRepository;

    private final OrderItemService orderItemService;

    private final CustomerService customerService;

    private final ProductService productService;

    private final EmailService emailService;

    private final InvoiceService invoiceService;

    private final KafkaTemplate<String, OrderDTO> kafkaTemplate;

    OrderServiceImpl(OrderRepository orderRepository,
                     OrderItemService orderItemService,
                     CustomerService customerService,
                     ProductService productService,
                     EmailService emailService,
                     InvoiceService invoiceService,
                     KafkaTemplate<String, OrderDTO> kafkaTemplate){
        this.orderItemService = orderItemService;
        this.orderRepository = orderRepository;
        this.customerService = customerService;
        this.productService = productService;
        this.emailService = emailService;
        this.invoiceService = invoiceService;
        this.kafkaTemplate = kafkaTemplate;

    }

    private final ModelMapper modelMapper = new ModelMapper();
    private CompletableFuture<OrderDTO> future;


    @Override
    public OrderDTO getOrderById(long id){
        OrderEntity orderEntity = this.orderRepository.findOrderEntityById(id);
        return this.modelMapper.map(orderEntity,OrderDTO.class);
    }

    @Override
    public CompletableFuture<OrderDTO> processOrderOk(OrderDTO orderDTO) throws MessagingException, FileNotFoundException, DocumentException {
        OrderEntity order =  buildOrder(orderDTO);
        OrderDTO dto = this.modelMapper.map(order, OrderDTO.class);
        log.warn(dto.toString());
        future.complete(dto);
        File file =  this.invoiceService.createInvoiceFile(TEST_MAIL_ORDER_STRING, dto);
        this.emailService.sendMail(dto.getCustomer().getEmail(),
                TEST_MAIL_ORDER_STRING + new Date().toString(),
               "This is a message test", file );
        return future;
    }

    @Override
    public CompletableFuture<OrderDTO> processOrderNotOk(OrderDTO orderDTO) {
        ProductOutOfStockException ex = new ProductOutOfStockException(orderDTO.getErrorMessage());
        future.completeExceptionally(ex);
        return future;
    }

    @Override
    public OrderEntity buildOrder(OrderDTO orderDTO) {
        CustomerEntity customerEntity = this.customerService.getCustomerById(orderDTO.getCustomer().getId()) ;
        List<Long> productIds = orderDTO.getOrderItemEntities().stream().map(x -> x.getProductEntity().getId()).collect(Collectors.toList());
        List<ProductEntity> productEntityList = this.productService.getAllProductsByIds(productIds);
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setCustomer(customerEntity);
        orderEntity.setDateCreated(orderDTO.getDateCreated());
        OrderEntity newOrderEntity = this.orderRepository.save(orderEntity);
        List<OrderItemEntity> orderItemEntities = new ArrayList<>();
        for ( ProductEntity product: productEntityList  ) {
            int orderQuantity = orderDTO.getOrderItemEntities()
                    .stream().filter(x -> x.getProductEntity().getId() == product.getId())
                    .map(OrderItemDTO::getQuantity).findFirst()
                    .orElseThrow(() ->  new ProductNotFoundException(product.getProductName()));
            OrderItemEntity orderItemEntity = new OrderItemEntity();
            orderItemEntity.setOrderEntity(newOrderEntity);
            orderItemEntity.setProductEntity(product);
            orderItemEntity.setQuantity(orderQuantity);
            orderItemEntities.add(this.orderItemService.save(orderItemEntity) );
            log.warn( this.modelMapper.map(orderItemEntities,  new TypeToken<List<OrderItemDTO>>() {}.getType() ).toString() );

        }
        newOrderEntity.setOrderItemEntities(orderItemEntities);
        return this.orderRepository.save(newOrderEntity);
    }

    @Override
    public CompletableFuture<OrderDTO> createOrder( OrderRequest orderRequest) {
        future = new CompletableFuture<>();
        CustomerEntity customerEntity = this.customerService.getCustomerById(orderRequest.getCustomerId()) ;
        if(orderRequest.getProductRequests().isEmpty()){
            log.error("no product is sent");
            return null;
        }
        List<Long> productIds = orderRequest.getProductRequests().stream().map(ProductRequest::getProductId).collect(Collectors.toList());
        List<ProductEntity> productEntityList = this.productService.getAllProductsByIds(productIds);
        if(productEntityList.size()!=productIds.size()){
            log.error("A product does not exist");
            return null;
        }
        if(Objects.isNull(customerEntity)){
            log.error("Customer with id : "+orderRequest.getCustomerId() + " does not exist");
            throw new CustomerNotFoundException(orderRequest.getCustomerId());
        }
        OrderEntity orderEntity = new OrderEntity();
        List<OrderItemEntity> orderItemEntities = new ArrayList<>();
        for ( ProductEntity product: productEntityList  ) {
            int orderQuantity = orderRequest.getProductRequests()
                    .stream().filter(x -> x.getProductId() == product.getId())
                    .map(ProductRequest::getQuantity).findFirst()
                    .orElseThrow(() ->  new ProductNotFoundException(product.getProductName()));
            orderItemEntities.add( new OrderItemEntity(orderEntity,product,orderQuantity));
        }
        orderEntity.setCustomer(customerEntity);
        orderEntity.setOrderItemEntities(orderItemEntities);
        orderEntity.setDateCreated(new Date());
        kafkaTemplate.send(KAFKA_TOPIC_ORDERS, this.modelMapper.map(orderEntity,OrderDTO.class));
        return future;
    }


}
