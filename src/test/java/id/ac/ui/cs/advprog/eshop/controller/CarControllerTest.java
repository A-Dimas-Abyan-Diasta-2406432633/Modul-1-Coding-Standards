package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.service.CarServiceImpl;
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

@WebMvcTest(CarController.class)
class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarServiceImpl carService;

    @MockBean
    private ProductService productService;

    @Test
    void createCarPage_returnsViewWithModel() throws Exception {
        mockMvc.perform(get("/car/createCar"))
                .andExpect(status().isOk())
                .andExpect(view().name("createCar"))
                .andExpect(model().attributeExists("car"));
    }

    @Test
    void createCarPost_redirectsToList() throws Exception {
        mockMvc.perform(post("/car/createCar")
                        .param("carName", "Sedan")
                        .param("carColor", "Black")
                        .param("carQuantity", "2"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("listCar"));

        verify(carService).create(any(Car.class));
    }

    @Test
    void carListPage_returnsCars() throws Exception {
        Car car = new Car();
        car.setCarId("id-1");
        car.setCarName("Sedan");
        car.setCarColor("Black");
        car.setCarQuantity(2);
        when(carService.findAll()).thenReturn(List.of(car));

        mockMvc.perform(get("/car/listCar"))
                .andExpect(status().isOk())
                .andExpect(view().name("carList"))
                .andExpect(model().attribute("cars", hasSize(1)));
    }

    @Test
    void editCarPage_returnsCar() throws Exception {
        Car car = new Car();
        car.setCarId("id-1");
        car.setCarName("Sedan");
        car.setCarColor("Black");
        car.setCarQuantity(2);
        when(carService.findById("id-1")).thenReturn(car);

        mockMvc.perform(get("/car/editCar/id-1"))
                .andExpect(status().isOk())
                .andExpect(view().name("editCar"))
                .andExpect(model().attribute("car", samePropertyValuesAs(car)));
    }

    @Test
    void editCarPost_redirectsToList() throws Exception {
        mockMvc.perform(post("/car/editCar")
                        .param("carId", "id-2")
                        .param("carName", "New")
                        .param("carColor", "White")
                        .param("carQuantity", "5"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("listCar"));

        verify(carService).update(any(String.class), any(Car.class));
    }

    @Test
    void deleteCar_redirectsToList() throws Exception {
        mockMvc.perform(post("/car/deleteCar")
                        .param("carId", "id-3"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("listCar"));

        verify(carService).deleteCarById("id-3");
    }
}
