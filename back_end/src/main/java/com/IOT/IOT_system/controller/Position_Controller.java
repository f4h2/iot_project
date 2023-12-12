package com.IOT.IOT_system.controller;

import com.IOT.IOT_system.repository.Stream_bit_Repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/map")
public class Position_Controller {
    @Autowired
    private Stream_bit_Repo SB_repo;

    @GetMapping(path = "/light_position")
    public String position(){
        return SB_repo.findTopById().getStreamBit();
    }
}
