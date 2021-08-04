package com.app.myapp.repository;

import com.app.myapp.pojo.Car;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ICarRepository extends ElasticsearchRepository<Car,String>{
    List<Car> findByColor(String color);
    Car findByPlateNumber(String plateNumber);
    //Boolean deleteByPlateNumber(String plateNumber);
    //boolean findDuplicateCar(String plateNumber);
}
