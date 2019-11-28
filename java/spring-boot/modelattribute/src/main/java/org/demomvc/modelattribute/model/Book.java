package org.demomvc.modelattribute.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@AllArgsConstructor
@Getter
@Setter
public class Book {


    private String author;
    private String title;
    private String isbn;


}
