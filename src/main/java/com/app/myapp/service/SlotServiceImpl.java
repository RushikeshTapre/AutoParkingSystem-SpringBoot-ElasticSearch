package com.app.myapp.service;

import com.app.myapp.pojo.Car;
import com.app.myapp.pojo.Slot;
import com.app.myapp.repository.ICarRepository;
import com.app.myapp.repository.ISlotRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class SlotServiceImpl implements ISlotService{
    private static final Logger logger= LoggerFactory.getLogger(SlotServiceImpl.class);
    @Autowired
    ICarRepository carRepository;
    @Autowired
    ISlotRepository slotRepository;
    public SlotServiceImpl() {
        logger.info("slot service impl");
    }
    @Override
    public boolean prepareSlot() {
        boolean slotPrepared=false;
        logger.info("Inside SlotServiceImpl.PrepareSlot()");
        Slot newSlot=null;
        for(int i=0;i<10;i++){
            newSlot=new Slot();
            newSlot.setCarPlateNumber("Empty");
            newSlot.setSlotNumber(String.valueOf(i));
            logger.info(slotRepository.save(newSlot).toString()+"saved");
            slotPrepared=true;
        }
        if(slotPrepared) {
            return true;
        }else{
            return false;
        }
    }
    @Override
    public boolean removeSlot(){
        boolean flag=false;
        Iterable<Slot> iterable= slotRepository.findAll();
        for(Slot slot:iterable){
            slotRepository.delete(slot);
            logger.info("slot deleted"+slot);
            flag=true;
        }
        Iterable<Car> carIterable=carRepository.findAll();
        for(Car car:carIterable){
            carRepository.delete(car);
            logger.info("car deleted"+car);
            flag=true;
        }
        if(flag){
            return true;
        }else{
            return false;
        }
    }
    @Override
    public List<Slot> getSlotList() {
        List<Slot> slotList=new ArrayList<>();
        Iterable<Slot> iterable= slotRepository.findAll();
        for(Slot slot:iterable){
            slotList.add(slot);
        }
        return slotList;
    }
}
