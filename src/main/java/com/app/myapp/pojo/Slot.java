package com.app.myapp.pojo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;


@Getter
@Setter
@Document(indexName = "slot")
public class Slot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String slotId;
    private String slotNumber;
    private String carId;
    private String carPlateNumber;

    public Slot(String slotId, String slotNumber, String carId, String carPlateNumber) {
        this.slotId = slotId;
        this.slotNumber = slotNumber;
        this.carId = carId;
        this.carPlateNumber = carPlateNumber;
    }

    public Slot() {
    }

    @Override
    public String toString() {
        return "Slot{" +
                "slotId='" + slotId + '\'' +
                ", slotNumber='" + slotNumber + '\'' +
                ", carId='" + carId + '\'' +
                ", carPlateNumber='" + carPlateNumber + '\'' +
                '}';
    }

    public String getSlotId() {
        return slotId;
    }

    public void setSlotId(String slotId) {
        this.slotId = slotId;
    }

    public String getSlotNumber() {
        return slotNumber;
    }

    public void setSlotNumber(String slotNumber) {
        this.slotNumber = slotNumber;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getCarPlateNumber() {
        return carPlateNumber;
    }

    public void setCarPlateNumber(String carPlateNumber) {
        this.carPlateNumber = carPlateNumber;
    }
}
