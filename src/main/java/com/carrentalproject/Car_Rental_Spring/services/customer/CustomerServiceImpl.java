package com.carrentalproject.Car_Rental_Spring.services.customer;

import com.carrentalproject.Car_Rental_Spring.Entity.BookACar;
import com.carrentalproject.Car_Rental_Spring.Entity.Car;
import com.carrentalproject.Car_Rental_Spring.Entity.User;
import com.carrentalproject.Car_Rental_Spring.dto.BookACarDto;
import com.carrentalproject.Car_Rental_Spring.dto.CarDto;
import com.carrentalproject.Car_Rental_Spring.enums.BookCarStatus;
import com.carrentalproject.Car_Rental_Spring.repostory.BookACarRepository;
import com.carrentalproject.Car_Rental_Spring.repostory.CarRepository;
import com.carrentalproject.Car_Rental_Spring.repostory.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CarRepository carRepository;
    private final UserRepository userRepository;
    private final BookACarRepository bookACarRepository;

    @Override
    public List<CarDto> getAllCars() {

        return carRepository.findAll().stream().map(Car::getCarDto).collect(Collectors.toList());
    }

    @Override
    public boolean bookACar(BookACarDto bookACarDto) {
        Optional<Car> optionalCar = carRepository.findById(bookACarDto.getCarId());
        Optional<User> optionalUser = userRepository.findById(bookACarDto.getUserId());
        if (optionalCar.isPresent() && optionalUser.isPresent()) {
            Car existingCar = optionalCar.get();
            BookACar bookACar = new BookACar();
            bookACar.setUser(optionalUser.get());
            bookACar.setCar(existingCar);
            bookACar.setBookCarStatus(BookCarStatus.PENDING);

            bookACar.setFromDate(bookACarDto.getFromDate());
            bookACar.setToDate(bookACarDto.getToDate());

            long diffInMilliSeconds = bookACarDto.getToDate().getTime() - bookACarDto.getFromDate().getTime();
            long days = TimeUnit.MILLISECONDS.toDays(diffInMilliSeconds);

            bookACar.setDays(days);
            bookACar.setPrice(existingCar.getPrice() * days);
            bookACarRepository.save(bookACar);

            return true;
        }

        return false;
    }



    @Override
    public CarDto getCarById(Long carId) {
        Optional<Car>optionalCar = carRepository.findById(carId);
        return optionalCar.map(Car::getCarDto).orElse(null);
    }

    @Override
    public List<BookACarDto> getBookingsByUserId(Long userId) {
        return bookACarRepository.findAllByUserId(userId).stream().map(BookACar::getBookACarDto).collect(Collectors.toList());
    }
}
