
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
        @JsonProperty("color")
        public String color;
        @JsonProperty("Color")
        public String Color;
        @JsonProperty("Description")
        public String description;
        @JsonProperty("generation")
        public String generation;
        @JsonProperty("Generation")
        public String Generation;
        @JsonProperty("price")
        public double price;
        @JsonProperty("Price")
        public double Price;
        @JsonProperty("capacity")
        public String capacity;
        @JsonProperty("Capacity")
        public String Capacity;
        @JsonProperty("capacity GB")
        public String capacityGB;
        @JsonProperty("Strap Colour")
        public String strapColour;
        @JsonProperty("Case Size")
        public String caseSize;
        @JsonProperty("Screen size")
        public String screenSize;
        @JsonProperty("CPU model")
        public String CPUModel;
        @JsonProperty("Hard disk size")
        public String hardDiskSize;
    }
}
