package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductRepositoryTest {

    @Test
    void update_existingProduct_updatesData() {
        ProductRepository repository = new ProductRepository();
        Product product = new Product();
        product.setProductName("Old");
        product.setProductQuantity(10);
        Product created = repository.create(product);

        Product updated = new Product();
        updated.setProductName("New");
        updated.setProductQuantity(20);
        Product result = repository.update(created.getProductId(), updated);

        assertNotNull(result);
        assertEquals(created.getProductId(), result.getProductId());
        assertEquals("New", result.getProductName());
        assertEquals(20, result.getProductQuantity());
        Product stored = repository.findById(created.getProductId());
        assertNotNull(stored);
        assertEquals("New", stored.getProductName());
        assertEquals(20, stored.getProductQuantity());
    }

    @Test
    void update_missingProduct_returnsNull() {
        ProductRepository repository = new ProductRepository();
        Product updated = new Product();
        updated.setProductName("New");
        updated.setProductQuantity(20);

        Product result = repository.update("missing-id", updated);

        assertNull(result);
    }

    @Test
    void delete_existingProduct_removesProduct() {
        ProductRepository repository = new ProductRepository();
        Product product = new Product();
        product.setProductName("Item");
        product.setProductQuantity(5);
        Product created = repository.create(product);

        repository.delete(created.getProductId());

        assertNull(repository.findById(created.getProductId()));
    }

    @Test
    void delete_missingProduct_keepsExisting() {
        ProductRepository repository = new ProductRepository();
        Product product = new Product();
        product.setProductName("Item");
        product.setProductQuantity(5);
        Product created = repository.create(product);

        repository.delete("missing-id");

        Product stored = repository.findById(created.getProductId());
        assertNotNull(stored);
        assertEquals("Item", stored.getProductName());
        assertEquals(5, stored.getProductQuantity());
    }
}
