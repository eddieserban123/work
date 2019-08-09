package com.report.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("person")
public class Person {

    @PrimaryKey
    private String id;

    private String name;
}
