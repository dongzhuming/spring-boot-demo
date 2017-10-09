package org.molecule.demo.springbootmongo;

import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document
public class City implements Serializable {
    private String name;

    public City() {

    }
    public City(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
