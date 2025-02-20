package com.apiautomation.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.crypto.Data;
import java.util.Date;

public class ResponseItem {
@JsonProperty("id")
    public String id;
@JsonProperty("name")
    public String name;
@JsonProperty("createdAt")
    public Date createdAt;
@JsonProperty("data")
    public Data data;
@JsonProperty("message")
    public String message;
@JsonProperty("error")
    public String error;

public static class Data {
    @JsonProperty("year")
    public int year;
    @JsonProperty("price")
    public double price;
    @JsonProperty("CPU model")
    public String CPUModel;
    @JsonProperty("Hard disk size")
    public String hardDiskSize;
}
}
