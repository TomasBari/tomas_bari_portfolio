package com.ictdemy;

import java.util.*;

public class InsuranceManager {
    private List<InsuredPerson> insuredList = new ArrayList<>();

    // metoda na pridanie poisteneho
    public void addInsuredPerson(InsuredPerson person) {
        insuredList.add(person);  // pridanie osoby do zoznamu
    }

    // metoda na ziskanie vsetkych poistenych
    public List<InsuredPerson> getAllInsured() {
        return insuredList;  // Vráti všetkých poistených
    }

    // metoda na vyhladanie poisteneho podla mena
    public InsuredPerson findByName(String firstName, String lastName) {
        for (InsuredPerson person : insuredList) {
            if (person.getFullName().equalsIgnoreCase(firstName + " " + lastName)) {
                return person;  // najdenie osoby
            }
        }
        return null;  // ak osoba nie je nájdená
    }
}