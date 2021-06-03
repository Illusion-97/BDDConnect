package com.bdd.java;

import java.sql.Date;
import java.time.LocalDate;

public class Fruit {

    private long id;
    private String name;
    private Date dlc;

    public Fruit(long id, String name, Date dlc) {
        this.id = id;
        this.name = name;
        this.dlc = dlc;
    }

    public Fruit(String name) {
        this.name = name;
        this.dlc = Date.valueOf(LocalDate.now().plusDays(3));
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDlc() {
        return dlc.toLocalDate();
    }

    public void setDlc(LocalDate dlc) {
        this.dlc = Date.valueOf(dlc);
    }
}
