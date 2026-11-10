package com.example.pr2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class Task2ArrayListBasics {
    public static void main(String[] args) {
        System.out.println("=== ZADANIE 2: Bazovye operacii s ArrayList ===");

        ArrayList<String> cities = new ArrayList<>();
        cities.add("Vil'nyus");
        cities.add("Riga");
        cities.add("Tallin");
        cities.add("Varshava");
        cities.add("Berlin");
        cities.add("Praga");
        cities.add("Vena");
        cities.add("Stokgol'm");
        cities.add("Hel'sinki");
        cities.add("Oslo");

        System.out.print("Obhod cherez indeksnyj for: ");
        for (int i = 0; i < cities.size(); i++) {
            System.out.print(cities.get(i) + (i < cities.size() - 1 ? ", " : "\n"));
        }

        System.out.print("Obhod cherez for-each: ");
        for (String city : cities) {
            System.out.print(city + " | ");
        }
        System.out.println();

        System.out.print("Obhod cherez Iterator: ");
        Iterator<String> cityIter = cities.iterator();
        while (cityIter.hasNext()) {
            System.out.print(cityIter.next() + " ");
        }
        System.out.println();

        cities.add(0, "Kopengagen");
        cities.add(cities.size() / 2, "Amsterdam");
        cities.add("Lissabon");
        System.out.println("Spisok posle vstavok (nachalo, seredina, konec): " + cities);

        if (cities.size() > 3) {
            cities.set(3, "Bryussel'");
            System.out.println("Spisok posle zameny elementa s indeksom 3: " + cities);
        }

        String targetCity = "Berlin";
        System.out.println("Est' li '" + targetCity + "' v spiske? " + cities.contains(targetCity));
        System.out.println("Indeks pervogo vhozhdeniya '" + targetCity + "': " + cities.indexOf(targetCity));

        if (!cities.isEmpty()) {
            cities.remove(0);
        }
        cities.remove("Oslo");
        System.out.println("Posle udaleniya po indeksu 0 i po znacheniyu 'Oslo': " + cities);

        System.out.println("Tekushchij razmer: " + cities.size() + " | Spisok pust? " + cities.isEmpty());

        String[] cityArray = cities.toArray(new String[0]);
        System.out.println("Vygruzka v massiv String[]: " + Arrays.toString(cityArray));
    }
}
