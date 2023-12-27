package com.candles.features.order;

import lombok.Data;

@Data
public class Customer {
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String address;
    private String comment;
}
