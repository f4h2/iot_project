package com.IOT.IOT_system.model;

import jakarta.persistence.*;

import java.util.Date;


@Entity
@Table(name = "rfid")
public class RFID {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private String IDx;
    private Date date;

    public RFID() {

    }


    public RFID(Long id, String IDx, Date date) {
        this.id = id;
        this.IDx = IDx;
        this.date = date;

    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIDx() {
        return IDx;
    }

    public void setIDx(String IDx) {
        this.IDx = IDx;
    }
}
