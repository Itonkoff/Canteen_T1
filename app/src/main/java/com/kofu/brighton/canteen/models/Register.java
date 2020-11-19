package com.kofu.brighton.canteen.models;

public class Register {
    public String firstName;
    public String lastName;
    public String email;
    public String phoneNumber;
    public String password;
    public String passwordConfirm;

    public Register(String firstName, String lastName, String email, String phoneNumber, String password, String passwordConfirm) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
    }
}
