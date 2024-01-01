package com.IOT.IOT_system.controller;

import com.IOT.IOT_system.model.Stream_bit;
import com.IOT.IOT_system.repository.Stream_bit_Repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8082")
@RequestMapping("/map")
public class Position_Controller {
    @Autowired
    private Stream_bit_Repo SB_repo;


    @GetMapping(path = "/lightposition")
    public String position(){
        List<Stream_bit> x= SB_repo.findAll();
        return x.get(x.size()-1).getStreamBit();
//        return "ok";
    }

}
