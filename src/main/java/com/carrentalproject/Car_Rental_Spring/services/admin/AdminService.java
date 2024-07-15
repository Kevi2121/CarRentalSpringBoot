package com.carrentalproject.Car_Rental_Spring.services.admin;

import com.carrentalproject.Car_Rental_Spring.dto.CarDto;
import org.springframework.aop.target.LazyInitTargetSource;

import java.io.IOException;
import java.util.List;

public interface AdminService {
    boolean postCar(CarDto carDto) throws IOException;

   List<CarDto> getAllCars();
   void deleteCar(Long id);
}
