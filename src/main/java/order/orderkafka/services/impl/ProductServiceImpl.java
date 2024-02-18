package order.orderkafka.services.impl;

import order.orderkafka.entities.ProductEntity;
import order.orderkafka.repositories.ProductRepository;
import order.orderkafka.services.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    ProductServiceImpl(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public List<ProductEntity> getAllProductsByIds(List<Long> ids) {
        return this.productRepository.findAllByIdIsIn(ids);
    }
}
