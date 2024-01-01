package com.IOT.IOT_system.model;

import jakarta.persistence.*;

import java.util.Date;
@Entity
public class RFID_out {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private String ID_out;
    private Date date_out;

    public RFID_out() {

    }


    public RFID_out(Long id, String ID_out, Date date_out) {
        this.id = id;
        this.ID_out = ID_out;
        this.date_out = date_out;

    }

    public Date getDate_out() {
        return date_out;
    }

    public void setDate_out(Date date) {
        this.date_out = date_out;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getID_out() {
        return ID_out;
    }

    public void setID_out(String IDx) {
        this.ID_out = ID_out;
    }
}
