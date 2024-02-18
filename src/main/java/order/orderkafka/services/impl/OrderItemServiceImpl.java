package ma.therightman.orderkafka.services.impl;

import ma.therightman.orderkafka.entities.OrderItemEntity;
import ma.therightman.orderkafka.repositories.OrderItemRepository;
import ma.therightman.orderkafka.services.OrderItemService;
import org.springframework.stereotype.Service;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;

    OrderItemServiceImpl(OrderItemRepository orderItemRepository){
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public OrderItemEntity save( OrderItemEntity orderItemEntity ){
        return this.orderItemRepository.save(orderItemEntity);
    }

}
