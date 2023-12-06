package com.candles.demo.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
@JsonSerialize
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailSubscription {
    @Id
    @Email
    private String email;
}
