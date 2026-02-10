package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    void createProductPage_returnsViewWithModel() throws Exception {
        mockMvc.perform(get("/product/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("CreateProduct"))
                .andExpect(model().attributeExists("product"));
    }

    @Test
    void createProductPost_redirectsToList() throws Exception {
        mockMvc.perform(post("/product/create")
                        .param("productName", "Susu")
                        .param("productQuantity", "7"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("list"));

        verify(productService).create(any(Product.class));
    }

    @Test
    void productListPage_returnsProducts() throws Exception {
        Product product = new Product();
        product.setProductName("Item");
        product.setProductQuantity(2);
        when(productService.findAll()).thenReturn(List.of(product));

        mockMvc.perform(get("/product/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("ProductList"))
                .andExpect(model().attribute("products", hasSize(1)));
    }

    @Test
    void editProductPage_returnsProduct() throws Exception {
        Product product = new Product();
        product.setProductId("id-1");
        product.setProductName("Item");
        product.setProductQuantity(4);
        when(productService.findById("id-1")).thenReturn(product);

        mockMvc.perform(get("/product/edit/id-1"))
                .andExpect(status().isOk())
                .andExpect(view().name("EditProduct"))
                .andExpect(model().attribute("product", samePropertyValuesAs(product)));
    }

    @Test
    void editProductPost_redirectsToList() throws Exception {
        mockMvc.perform(post("/product/edit")
                        .param("productId", "id-2")
                        .param("productName", "New")
                        .param("productQuantity", "9"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("list"));

        verify(productService).update(any(String.class), any(Product.class));
    }

    @Test
    void deleteProduct_redirectsToList() throws Exception {
        mockMvc.perform(get("/product/delete/id-3"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/product/list"));

        verify(productService).delete("id-3");
    }
}
