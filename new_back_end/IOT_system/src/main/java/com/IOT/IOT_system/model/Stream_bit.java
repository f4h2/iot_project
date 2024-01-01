package com.IOT.IOT_system.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Stream_bit")
public class Stream_bit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreamBit() {
        return streamBit;
    }

    public void setStreamBit(String streamBit) {
        this.streamBit = streamBit;
    }

    private String streamBit;
}
