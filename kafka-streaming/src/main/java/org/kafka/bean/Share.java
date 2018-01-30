package org.kafka.bean;

import java.time.LocalDateTime;

public class Share {
    private String name;
    private Long price;
    private LocalDateTime time;


    public Share() {

    }

    public Share(String name, Long price, LocalDateTime time) {
            this.name = name;
            this.price = price;
            this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
