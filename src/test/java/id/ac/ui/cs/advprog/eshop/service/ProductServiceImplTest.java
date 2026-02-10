package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl service;

    @Test
    void create_delegatesToRepository() {
        Product product = new Product();
        product.setProductName("Item");
        product.setProductQuantity(3);

        Product result = service.create(product);

        assertSame(product, result);
        verify(productRepository).create(product);
    }

    @Test
    void findAll_collectsAllProducts() {
        Product first = new Product();
        first.setProductName("A");
        first.setProductQuantity(1);
        Product second = new Product();
        second.setProductName("B");
        second.setProductQuantity(2);

        when(productRepository.findAll()).thenReturn(List.of(first, second).iterator());

        List<Product> result = service.findAll();

        assertEquals(2, result.size());
        assertSame(first, result.get(0));
        assertSame(second, result.get(1));
    }

    @Test
    void findById_returnsRepositoryResult() {
        Product product = new Product();
        product.setProductName("Item");
        product.setProductQuantity(4);
        when(productRepository.findById("id-1")).thenReturn(product);

        Product result = service.findById("id-1");

        assertSame(product, result);
    }

    @Test
    void update_delegatesToRepository() {
        Product updated = new Product();
        updated.setProductName("New");
        updated.setProductQuantity(9);
        when(productRepository.update("id-2", updated)).thenReturn(updated);

        Product result = service.update("id-2", updated);

        assertSame(updated, result);
    }

    @Test
    void delete_delegatesToRepository() {
        service.delete("id-3");

        verify(productRepository).delete("id-3");
    }
}
