package com.candles.demo.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;

@JsonSerialize
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailSubscription {
    @Email
    @Indexed(unique = true)
    private String email;
}
