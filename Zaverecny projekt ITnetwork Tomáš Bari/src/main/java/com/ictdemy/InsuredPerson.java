package com.ictdemy;

public class InsuredPerson {
    private String firstName;
    private String lastName;
    private int age;
    private String phoneNumber;

    // konstruktory a validacia udajov
    public InsuredPerson(String firstName, String lastName, int age, String phoneNumber) {
        if (firstName == null || firstName.trim().isEmpty() || lastName == null || lastName.trim().isEmpty()) {
            throw new IllegalArgumentException("Meno a priezvisko nesmú byť prázdne.");
        }
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.phoneNumber = phoneNumber;
    }

    // ziskanie celeho mena
    public String getFullName() {
        return firstName + " " + lastName;
    }

    // vypis informácii o poistenom
    @Override
    public String toString() {
        return "Meno: " + firstName + " " + lastName + ", Vek: " + age + ", Tel.: " + phoneNumber;
    }
}

