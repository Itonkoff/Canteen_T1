package com.kofu.brighton.canteen.models;

public class MealBill {
    private String mStudentRef;
    private double mAmount;
    public MealBill(String studentRef, double amount) {
        this.mStudentRef = studentRef;
        this.mAmount = amount;
    }
}
