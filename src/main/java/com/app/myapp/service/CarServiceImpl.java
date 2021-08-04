package com.app.myapp.service;

import com.app.myapp.pojo.Car;
import com.app.myapp.pojo.Slot;
import com.app.myapp.repository.ICarRepository;
import com.app.myapp.repository.ISlotRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class CarServiceImpl implements ICarService {
    @Autowired
    ICarRepository carRepository;
    @Autowired
    ISlotRepository slotRepository;
    private static final Logger logger= LoggerFactory.getLogger(CarServiceImpl.class);
    public CarServiceImpl() {
        logger.info("in ParkingServiceImpl");
    }
    @Override
    public List<Car> getCarList() {
        Iterable<Car> iterable =carRepository.findAll();
        List<Car> carList = new LinkedList<>();
        for (Car car:iterable) {
            carList.add(car);
        }
    return carList;
    }
    @Override
    public Car carEntry(Car newCar) {
        if(checkFoundDuplicateCar(newCar.getPlateNumber())){
            logger.info("found car with same "+newCar.getPlateNumber()+" plate Number");
            return null;
        }
        Slot emptySlot=slotRepository.findByCarPlateNumber("Empty");
        if(emptySlot==null){
            logger.info("empty slot not found..");
            return null;
        }
        logger.info("slot found:"+emptySlot.toString());
        newCar.setSlotNumber(emptySlot.getSlotNumber());
        try {
            Car car = carRepository.save(newCar);
            emptySlot.setCarId(car.getCarId());
            emptySlot.setCarPlateNumber(car.getPlateNumber());
            logger.info("info after update:" + car + "\n" + emptySlot);
            slotRepository.save(emptySlot);
            return newCar;
        }catch (Exception e){
            logger.info("error while saving obj:"+e);
            return null;
        }
    }
    @Override
    public Car carExit(String plateNumber) {
        Slot slot=slotRepository.findByCarPlateNumber(plateNumber);
        if(slot==null){
            logger.info("slot not found:"+plateNumber);
            return null;
        }
        logger.info("slot found"+slot);
        Car car=carRepository.findByPlateNumber(plateNumber);
        if(car==null){
            logger.info("car not found"+plateNumber);
            return null;
        }
        logger.info(car+"car found");
        try{
            carRepository.delete(car);
            slot.setCarId(null);
            slot.setCarPlateNumber("Empty");
            slotRepository.save(slot);
            return car;
        }catch (Exception e){
            logger.info("error in car exit:"+e);
            return null;
        }
    }
    @Override
    public Slot findSlotNumberByRegistrationNumber(String plateNumber) {
        Slot slot=slotRepository.findByCarPlateNumber(plateNumber);
        if(slot==null){
            logger.info("slot not found");
            return null;
        }
        return slot;
    }
    @Override
    public boolean checkFoundDuplicateCar(String plateNumber){
        logger.info("in checkFoundDuplicateCar");
        Car car=carRepository.findByPlateNumber(plateNumber);
        boolean carFound=true;
        if(car==null){
            logger.info(getClass().getName()+"duplicate car not found");
            carFound=false;
        }
        return carFound;
    }
    @Override
    public List<Car> getCarListByColor(String colour) {
        //logger.info(carRepository.findByPlateNumber("222").toString());
        return carRepository.findByColor(colour);
    }
}
