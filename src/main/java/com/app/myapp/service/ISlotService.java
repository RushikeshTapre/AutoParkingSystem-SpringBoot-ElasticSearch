package com.app.myapp.service;

import com.app.myapp.pojo.Slot;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ISlotService {
    public boolean prepareSlot();
    public List<Slot> getSlotList();
    public boolean removeSlot();
}
