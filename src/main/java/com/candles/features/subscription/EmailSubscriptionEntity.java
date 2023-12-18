package com.candles.features.subscription;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


@JsonSerialize
@Document(collection = "subscription")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailSubscriptionEntity {
    @Id
    String id;
    @Email
    @Indexed(unique = true)
    private String email;
}
