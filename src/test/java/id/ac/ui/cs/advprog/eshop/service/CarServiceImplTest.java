package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.repository.CarRepository;
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
class CarServiceImplTest {

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarServiceImpl service;

    @Test
    void create_delegatesToRepository() {
        Car car = new Car();
        car.setCarName("Sedan");
        car.setCarColor("Black");
        car.setCarQuantity(2);

        Car result = service.create(car);

        assertSame(car, result);
        verify(carRepository).create(car);
    }

    @Test
    void findAll_collectsAllCars() {
        Car first = new Car();
        first.setCarName("A");
        first.setCarColor("Red");
        first.setCarQuantity(1);
        Car second = new Car();
        second.setCarName("B");
        second.setCarColor("Blue");
        second.setCarQuantity(2);

        when(carRepository.findAll()).thenReturn(List.of(first, second).iterator());

        List<Car> result = service.findAll();

        assertEquals(2, result.size());
        assertSame(first, result.get(0));
        assertSame(second, result.get(1));
    }

    @Test
    void findById_returnsRepositoryResult() {
        Car car = new Car();
        car.setCarName("Sedan");
        car.setCarColor("Black");
        car.setCarQuantity(2);
        when(carRepository.findById("id-1")).thenReturn(car);

        Car result = service.findById("id-1");

        assertSame(car, result);
    }

    @Test
    void update_delegatesToRepository() {
        Car updated = new Car();
        updated.setCarName("New");
        updated.setCarColor("White");
        updated.setCarQuantity(3);

        service.update("id-2", updated);

        verify(carRepository).update("id-2", updated);
    }

    @Test
    void delete_delegatesToRepository() {
        service.delete("id-3");

        verify(carRepository).delete("id-3");
    }
}
