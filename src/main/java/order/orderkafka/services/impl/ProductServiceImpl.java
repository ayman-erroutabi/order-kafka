package ma.therightman.orderkafka.services.impl;

import ma.therightman.orderkafka.entities.ProductEntity;
import ma.therightman.orderkafka.repositories.ProductRepository;
import ma.therightman.orderkafka.services.ProductService;
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
