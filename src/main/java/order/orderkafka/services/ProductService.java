package order.orderkafka.services;


import order.orderkafka.entities.ProductEntity;

import java.util.List;

public interface ProductService {

    List<ProductEntity> getAllProductsByIds(List<Long> ids);
}
