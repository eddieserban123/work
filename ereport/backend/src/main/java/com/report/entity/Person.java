package com.report.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("person")
public class Person {

    @PrimaryKey
    private String id;

    @Column("first_name")
    private String firstName;

    @Column("last_name")
    private String lastName;

    private LocalDate birth;
}
