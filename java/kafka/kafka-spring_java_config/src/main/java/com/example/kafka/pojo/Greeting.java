package com.example.kafka.pojo;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Greeting {
    private String msg;
    private String name;
}
