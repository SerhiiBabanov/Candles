package com.candles.demo.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@JsonSerialize
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailSubscription {
    @Email
    private String email;
}
