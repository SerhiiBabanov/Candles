package com.candles.demo.model;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class EmailSubscription {
    @Email
    private String email;
}
