package com.example.listycity;

public class City {
    private String name;
    private String province;

    public City (String name, String province) {
        this.name = name;
        this.province = province;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getProvince() {
        return province;
    }

    // Setters (for editing)
    public void setName(String name) {
        this.name = name;
    }

    public void setProvince(String province) {
        this.province = province;
    }
}
