package com.server.aggregates.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.ZonedDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AggregatesDTO {
    private String sym;
    private double o;
    private double c;
    private double h;
    private double l;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private ZonedDateTime s;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private ZonedDateTime e;

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

    public ZonedDateTime getS() {
        return s;
    }

    public ZonedDateTime getE() {
        return e;
    }

}
