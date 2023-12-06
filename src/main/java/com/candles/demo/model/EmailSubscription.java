package com.candles.demo.model;

import jakarta.validation.constraints.Email;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class EmailSubscription {
    @Id
    @Email
    private String email;
}
