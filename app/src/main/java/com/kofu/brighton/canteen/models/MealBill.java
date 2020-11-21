package com.kofu.brighton.canteen.models;

public class MealBill {
    private String StudentRef;
    private int Amount;

    public MealBill(String studentRef, int amount) {
        this.StudentRef = studentRef;
        this.Amount = amount;
    }
}
