package com.app.myapp.pojo;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Getter
@Setter
@Document(indexName = "car")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String carId;
    private String color;
    private String plateNumber;
    private String slotNumber;

    public Car() {
    }

    public Car(String color, String plateNumber, String slotNumber) {
        this.color = color;
        this.plateNumber = plateNumber;
        this.slotNumber = slotNumber;
    }

    public Car(String id, String color, String plateNumber, String slotNumber) {
        this.carId = id;
        this.color = color;
        this.plateNumber = plateNumber;
        this.slotNumber = slotNumber;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + carId +
                ", color='" + color + '\'' +
                ", plateNumber='" + plateNumber + '\'' +
                ", slotNumber='" + slotNumber + '\'' +
                '}';
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getSlotNumber() {
        return slotNumber;
    }

    public void setSlotNumber(String slotNumber) {
        this.slotNumber = slotNumber;
    }
}
