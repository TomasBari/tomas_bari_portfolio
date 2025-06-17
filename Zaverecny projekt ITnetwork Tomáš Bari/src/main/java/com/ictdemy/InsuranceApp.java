package com.ictdemy;

import java.util.Scanner;

public class InsuranceApp {
    private static Scanner scanner = new Scanner(System.in);
    private static InsuranceManager manager = new InsuranceManager();

    // metoda na zobrazenie menu a spracovanie vyberu uzivatela
    public void showMenu() {
        while (true) {
            printHeader();  // zobrazenie hlavicky
            System.out.println("1 - Pridať poisteného\n2 - Zobraziť všetkých poistených\n3 - Vyhľadať poisteného\n4 - Ukončiť");
            System.out.print("Vyberte možnosť: ");

            String input = scanner.nextLine().trim(); // nacitanie vstupu a odstranenie medzier

            if (input.isEmpty()){
                System.out.println("Neplatná možnosť, skúste znova.");
                continue; // pokracuje v cykle
            }

            try{
                int choice = Integer.parseInt(input);  // nacitanie volby

                switch (choice) {
                    case 1 -> addInsuredPerson();  // pridanie poisteneho
                    case 2 -> displayAllInsured();  // zobrazenie vsetkych poistenych
                    case 3 -> searchInsuredPerson();  // vyhladanie poisteneho
                    case 4 -> {
                        System.out.println("Aplikácia ukončená.");
                        return;  // ukoncenie aplikacie
                    }
                    default -> System.out.println("Neplatná možnosť, skúste znova.");
                }
            } catch (NumberFormatException e) { // ak vstup nie je cislo
                System.out.println("Neplatná možnosť, skúste znova.");
                System.out.println();
            }
        }
    }

    // metoda na zobrazenie hlavicky aplikacie
    private void printHeader() {
        System.out.println("-".repeat(30)); // zobrazenie 30 pomlciek
        System.out.println("Evidencia poistených");
        System.out.println("-".repeat(30)); // zobrazenie 30 pomlciek
    }

    // metoda na pridanie poisteneho
    private void addInsuredPerson() {
        System.out.println("Zadajte meno: ");
        String firstName = scanner.nextLine();
        System.out.println("Zadajte priezvisko: ");
        String lastName = scanner.nextLine();
        System.out.println("Zadajte vek: ");
        int age = Integer.parseInt(scanner.nextLine());
        System.out.println("Zadajte telefónne číslo: ");
        String phoneNumber = scanner.nextLine();

        try {
            // pridanie poisteneho do zoznamu
            manager.addInsuredPerson(new InsuredPerson(firstName, lastName, age, phoneNumber));
            System.out.println("Poistený úspešne pridaný.\n");
        } catch (IllegalArgumentException e) {
            System.out.println("Chyba: " + e.getMessage() + "\n");
        }

        System.out.println("Pokračujte stlačením ľubovoľnej klávesy...");
        scanner.nextLine();  // cakanie na stlacenie klavesy pred navratom do menu (funguje Enter-om)
    }

    // metoda na zobrazenie vsetkych poistenych
    private void displayAllInsured() {
        System.out.println("Zoznam všetkých poistených:");
        manager.getAllInsured().forEach(System.out::println);  // Výpis všetkých poistených
        System.out.println("\nPokračujte stlačením ľubovoľnej klávesy...");
        scanner.nextLine();  // cakanie na stlacenie klavesy (Enter)
    }

    // metoda na vyhladanie poisteneho podla mena
    private void searchInsuredPerson() {
        System.out.print("Zadajte meno: ");
        String firstName = scanner.nextLine();
        System.out.print("Zadajte priezvisko: ");
        String lastName = scanner.nextLine();

        InsuredPerson person = manager.findByName(firstName, lastName);
        if (person != null) {
            System.out.println("Nájdený poistený: " + person + "\n");
        } else {
            System.out.println("Poistený s týmto menom neexistuje.\n");
        }

        System.out.println("Pokračujte stlačením ľubovoľnej klávesy...");
        scanner.nextLine();  // cakanie na stlacenie klavesy (Enter)
    }
}
