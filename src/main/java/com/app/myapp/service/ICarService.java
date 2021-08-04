package com.app.myapp.service;

import com.app.myapp.pojo.Car;
import com.app.myapp.pojo.Slot;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ICarService {
    List<Car> getCarList();
    Car carEntry(Car c);
    Car carExit(String plateNumber);
    Slot findSlotNumberByRegistrationNumber(String registrationNumber );
    List<Car> getCarListByColor(String color);
    boolean checkFoundDuplicateCar(String plateNumber);
}
