package com.server.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.server.enums.StocksEnums;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AggregatesDTO {
    private String sym;
    private double o;
    private double c;
    private double h;
    private double l;
    private Date s;
    private Date e;

    public String getSym() {
        return sym;
    }

    public void setSym(String sym) {
        this.sym = sym;
    }

    public double getO() {
        return o;
    }

    public void setO(double o) {
        this.o = o;
    }

    public double getC() {
        return c;
    }

    public void setC(double c) {
        this.c = c;
    }

    public double getH() {
        return h;
    }

    public void setH(double h) {
        this.h = h;
    }

    public double getL() {
        return l;
    }

    public void setL(double l) {
        this.l = l;
    }

    public Date getS() {
        return s;
    }

    public void setS(Date s) {
        this.s = s;
    }

    public Date getE() {
        return e;
    }

    public void setE(Date e) {
        this.e = e;
    }

}
