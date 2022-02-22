package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class Subscription {

    private UUID id;

    private String name;

    private String email;

}
