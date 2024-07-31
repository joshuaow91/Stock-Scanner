package com.server.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AggregatesDTO {
    private String sym;
    private double o;
    private double c;
    private double h;
    private double l;
    private LocalDateTime s;
    private LocalDateTime e;

    public String getSym() {
        return sym;
    }

    public double getO() {
        return o;
    }

    public double getC() {
        return c;
    }

    public double getH() {
        return h;
    }

    public double getL() {
        return l;
    }

    public LocalDateTime getS() {
        return s;
    }

    public LocalDateTime getE() {
        return e;
    }

}
