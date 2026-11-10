package com.example.pr2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

public class Task3SafeDelete {
    public static void main(String[] args) {
        System.out.println("=== ZADANIE 3: Bezopasnoe udalenie cherez Iterator ===");

        ArrayList<Integer> seq = new ArrayList<>();
        for (int n = 1; n <= 30; n++) {
            seq.add(n);
        }

        Iterator<Integer> seqIt = seq.iterator();
        while (seqIt.hasNext()) {
            if (seqIt.next() % 2 == 0) {
                seqIt.remove();
            }
        }
        System.out.println("Nechyotnye chisla od 1 do 30: " + seq);

        ArrayList<String> firstNames = new ArrayList<>(
                Arrays.asList("Ilya", "Aleksandr", "Yan", "Ekaterina", "Oleg", "Dmitrij", "Eva", "Veronika")
        );

        Iterator<String> nameIt = firstNames.iterator();
        while (nameIt.hasNext()) {
            if (nameIt.next().length() <= 4) {
                nameIt.remove();
            }
        }
        System.out.println("Imena dlinnee 4 simvolov (cherez Iterator): " + firstNames);

        System.out.println("\nDemonstraciya ConcurrentModificationException pri udalenii v for-each:");
        ArrayList<String> demoSample = new ArrayList<>(Arrays.asList("Alpha", "Beta", "Gamma", "Delta"));
        try {
            for (String item : demoSample) {
                if (item.equals("Beta")) {
                    demoSample.remove(item);
                }
            }
        } catch (ConcurrentModificationException ex) {
            System.out.println("Pojmano isklyuchenie: " + ex.getClass().getSimpleName()
                    + " (modifikaciya kollekcii napryamuyu vo vremya iteracii for-each narushaet schetchik modCount).");
        }

        ArrayList<String> altNames = new ArrayList<>(
                Arrays.asList("Ilya", "Aleksandr", "Yan", "Ekaterina", "Oleg", "Dmitrij", "Eva", "Veronika")
        );
        altNames.removeIf(s -> s.length() <= 4);
        System.out.println("Imena dlinnee 4 simvolov (cherez removeIf): " + altNames);
    }
}
