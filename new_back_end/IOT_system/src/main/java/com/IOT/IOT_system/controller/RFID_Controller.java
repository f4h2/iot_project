package com.IOT.IOT_system.controller;

import com.IOT.IOT_system.model.RFID;
import com.IOT.IOT_system.model.RFID_out;
import com.IOT.IOT_system.model.Stream_bit;
import com.IOT.IOT_system.repository.RfidRepository;
import com.IOT.IOT_system.repository.Rfid_out;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin
@RequestMapping("/RFID")
public class RFID_Controller {
    @Autowired
    private RfidRepository rfid_repo;
    @Autowired
    private Rfid_out rfid_out;

    @GetMapping(path = "/listRFID")
    public List<RFID> getAllRFID() {
        return rfid_repo.findAll();
    }
    @GetMapping(path = "/rfidout")
    public String Rfid_out(){
        List<RFID_out> x= rfid_out.findAll();
        String h= "xe có mã số: "+x.get(x.size()-1).getID_out()+"đã rời bãi đỗ xe lúc"+x.get(x.size()-1).getDate_out();
        return h;
    }
}