package org.learn.spliterator.demo03combinerusage;

/**
 * Created by eduard on 07/09/17.
 */
public class Payment {

    String category;
    Integer amount;


    public Payment(Integer value, String category) {
        this.category = category;
        this.amount = value;
    }

    public String getCategory() {
        return category;
    }

    public Integer getAmount() {
        return amount;
    }
}
