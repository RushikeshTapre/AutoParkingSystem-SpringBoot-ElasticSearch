package com.app.myapp.repository;

import com.app.myapp.pojo.Slot;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISlotRepository extends ElasticsearchRepository<Slot,String> {
        Slot findByCarPlateNumber(String plateNumber);
}
