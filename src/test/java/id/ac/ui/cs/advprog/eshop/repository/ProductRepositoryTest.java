package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductRepositoryTest {

    @Test
    void create_assignsIdAndStoresProduct() {
        ProductRepository repository = new ProductRepository();
        Product product = new Product();
        product.setProductName("Item");
        product.setProductQuantity(5);

        Product created = repository.create(product);

        assertNotNull(created.getProductId());
        assertEquals("Item", created.getProductName());
        assertEquals(5, created.getProductQuantity());
        Product stored = repository.findById(created.getProductId());
        assertNotNull(stored);
        assertEquals("Item", stored.getProductName());
        assertEquals(5, stored.getProductQuantity());
    }

    @Test
    void findAll_returnsAllProducts() {
        ProductRepository repository = new ProductRepository();
        Product first = new Product();
        first.setProductName("A");
        first.setProductQuantity(1);
        Product second = new Product();
        second.setProductName("B");
        second.setProductQuantity(2);

        repository.create(first);
        repository.create(second);

        int count = 0;
        for (var ignored = repository.findAll(); ignored.hasNext(); ) {
            ignored.next();
            count++;
        }
        assertEquals(2, count);
    }

    @Test
    void findById_missingProduct_returnsNull() {
        ProductRepository repository = new ProductRepository();

        Product result = repository.findById("missing-id");

        assertNull(result);
    }

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
