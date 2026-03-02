package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CarTest {

    @Test
    void gettersAndSetters_work() {
        Car car = new Car();
        car.setCarId("id-1");
        car.setCarName("Sedan");
        car.setCarColor("Black");
        car.setCarQuantity(3);

        assertEquals("id-1", car.getCarId());
        assertEquals("Sedan", car.getCarName());
        assertEquals("Black", car.getCarColor());
        assertEquals(3, car.getCarQuantity());
    }
}
