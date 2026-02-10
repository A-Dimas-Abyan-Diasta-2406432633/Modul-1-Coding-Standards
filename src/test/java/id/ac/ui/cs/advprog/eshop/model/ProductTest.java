package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductTest {

    @Test
    void gettersAndSetters_work() {
        Product product = new Product();
        product.setProductId("id-1");
        product.setProductName("Item");
        product.setProductQuantity(10);

        assertEquals("id-1", product.getProductId());
        assertEquals("Item", product.getProductName());
        assertEquals(10, product.getProductQuantity());
    }
}
