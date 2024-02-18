package ma.therightman.orderkafka.services;


import ma.therightman.orderkafka.entities.ProductEntity;

import java.util.List;

public interface ProductService {

    List<ProductEntity> getAllProductsByIds(List<Long> ids);
}
