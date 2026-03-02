package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.repository.CarRepository;
import org.springframework.stereotype.Service;
import id.ac.ui.cs.advprog.eshop.model.Car;

@Service
public class CarServiceImpl extends BaseCrudService<Car> implements CarService {

    public CarServiceImpl(CarRepository carRepository) {
        super(carRepository);
    }
}
