package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Car;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarRepositoryTest {

    @Test
    void create_assignsIdAndStoresCar() {
        CarRepository repository = new CarRepository();
        Car car = new Car();
        car.setCarName("Sedan");
        car.setCarColor("Black");
        car.setCarQuantity(2);

        Car created = repository.create(car);

        assertNotNull(created.getCarId());
        assertEquals("Sedan", created.getCarName());
        assertEquals("Black", created.getCarColor());
        assertEquals(2, created.getCarQuantity());
        Car stored = repository.findById(created.getCarId());
        assertNotNull(stored);
        assertEquals("Sedan", stored.getCarName());
        assertEquals("Black", stored.getCarColor());
        assertEquals(2, stored.getCarQuantity());
    }

    @Test
    void findAll_returnsAllCars() {
        CarRepository repository = new CarRepository();
        Car first = new Car();
        first.setCarName("A");
        first.setCarColor("Red");
        first.setCarQuantity(1);
        Car second = new Car();
        second.setCarName("B");
        second.setCarColor("Blue");
        second.setCarQuantity(2);

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
    void findById_missingCar_returnsNull() {
        CarRepository repository = new CarRepository();

        Car result = repository.findById("missing-id");

        assertNull(result);
    }

    @Test
    void update_existingCar_updatesData() {
        CarRepository repository = new CarRepository();
        Car car = new Car();
        car.setCarName("Old");
        car.setCarColor("White");
        car.setCarQuantity(5);
        Car created = repository.create(car);

        Car updated = new Car();
        updated.setCarName("New");
        updated.setCarColor("Black");
        updated.setCarQuantity(7);
        Car result = repository.update(created.getCarId(), updated);

        assertNotNull(result);
        assertEquals(created.getCarId(), result.getCarId());
        assertEquals("New", result.getCarName());
        assertEquals("Black", result.getCarColor());
        assertEquals(7, result.getCarQuantity());
        Car stored = repository.findById(created.getCarId());
        assertNotNull(stored);
        assertEquals("New", stored.getCarName());
        assertEquals("Black", stored.getCarColor());
        assertEquals(7, stored.getCarQuantity());
    }

    @Test
    void update_missingCar_returnsNull() {
        CarRepository repository = new CarRepository();
        Car updated = new Car();
        updated.setCarName("New");
        updated.setCarColor("Blue");
        updated.setCarQuantity(4);

        Car result = repository.update("missing-id", updated);

        assertNull(result);
    }

    @Test
    void delete_existingCar_removesCar() {
        CarRepository repository = new CarRepository();
        Car car = new Car();
        car.setCarName("Sedan");
        car.setCarColor("Black");
        car.setCarQuantity(2);
        Car created = repository.create(car);

        repository.delete(created.getCarId());

        assertNull(repository.findById(created.getCarId()));
    }

    @Test
    void delete_missingCar_keepsExisting() {
        CarRepository repository = new CarRepository();
        Car car = new Car();
        car.setCarName("Sedan");
        car.setCarColor("Black");
        car.setCarQuantity(2);
        Car created = repository.create(car);

        repository.delete("missing-id");

        Car stored = repository.findById(created.getCarId());
        assertNotNull(stored);
        assertEquals("Sedan", stored.getCarName());
        assertEquals("Black", stored.getCarColor());
        assertEquals(2, stored.getCarQuantity());
    }
}
