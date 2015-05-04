package com.example.home.hackathon.api;

import com.google.gson.annotations.SerializedName;

/**
 * {
 * id: 3,
 * name: "Jeans1",
 * image: "http://192.168.21.38/media/img/%D0%B4%D0%B6%D0%B8%D0%BD%D1%811.jpg",
 * price: "100.00",
 * category: 5
 * }
 */
public class Clothing {
    public int id;
    public String name;
    public String image;
    public float price;
    public Category category;

    public enum Category {
        @SerializedName("5")
        TROUSERS,
        @SerializedName("7")
        SHIRT,
        @SerializedName("6")
        SHOES,
        @SerializedName("8")
        GIFT
    }
}
