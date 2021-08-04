package com.app.myapp.service;

import com.app.myapp.AutoParkingSystemApplication;
import com.app.myapp.repository.ICarRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = AutoParkingSystemApplication.class
)
@AutoConfigureMockMvc
class CarServiceImplTest {
    private static final Logger logger= LoggerFactory.getLogger(CarServiceImplTest.class);

    @Autowired
    ICarRepository carRepository;
    @Autowired
    ICarService carService;

    @Test
    void checkFoundDuplicateCar() {
        logger.info("in test find Duplicate Car!");
        String plateNumber="444";
        boolean flag=carService.checkFoundDuplicateCar(plateNumber);
        assertThat(flag).isEqualTo(false);
        if(!flag){
            logger.info("car not found with "+plateNumber);
        }else{
            logger.info("car found with "+plateNumber);
        }
    }
}