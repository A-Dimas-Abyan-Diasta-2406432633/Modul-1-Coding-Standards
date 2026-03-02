package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl extends BaseCrudService<Product> implements ProductService {

    public ProductServiceImpl(ProductRepository productRepository) {
        super(productRepository);
    }
}
