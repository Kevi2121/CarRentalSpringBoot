package com.carrentalproject.Car_Rental_Spring.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table (name = "car")
public class Car {

    @Id
    @GeneratedValue
    private Long idCar;
    private String model;
    private String fuel;
    private String engine;
    private String transmision;
    private Integer nrOfDoors;
    private Long price;
    private Boolean availability;

}
