package com.example.pr2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;

class Student {
    private String name;
    private int age;
    private double averageGrade;

    public Student(String name, int age, double averageGrade) {
        this.name = name;
        this.age = age;
        this.averageGrade = averageGrade;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public double getAverageGrade() {
        return averageGrade;
    }

    public boolean isExcellent() {
        return averageGrade >= 4.5;
    }

    @Override
    public String toString() {
        return String.format("%s (vozrast: %d, sr. ball: %.2f, otlichnik: %s)",
                name, age, averageGrade, isExcellent() ? "da" : "net");
    }
}

public class Task5StudentList {
    public static void main(String[] args) {
        System.out.println("=== ZADANIE 5: Spisok studentov ===");

        ArrayList<Student> group = new ArrayList<>();
        group.add(new Student("Aleksej Morozov", 19, 4.75));
        group.add(new Student("Dar'ya Sokolova", 17, 4.90));
        group.add(new Student("Kirill Volkov", 20, 3.85));
        group.add(new Student("Polina Lebedeva", 18, 4.50));
        group.add(new Student("Artyom Kozlov", 16, 4.10));
        group.add(new Student("Viktoriya Novikova", 21, 4.95));
        group.add(new Student("Maksim Zajcev", 22, 4.20));
        group.add(new Student("Sofiya Pavlova", 19, 4.65));

        System.out.println("Ishodnyj sostav gruppy:");
        for (Student st : group) {
            System.out.println("  • " + st);
        }

        Iterator<Student> it = group.iterator();
        while (it.hasNext()) {
            if (it.next().getAge() < 18) {
                it.remove();
            }
        }

        System.out.println("\nPosle otseva nesovershennoletnih (< 18 let):");
        for (Student st : group) {
            System.out.println("  • " + st);
        }

        if (group.isEmpty()) {
            System.out.println("V gruppe ne ostalos' studentov.");
            return;
        }

        Student leader = group.get(0);
        for (Student st : group) {
            if (st.getAverageGrade() > leader.getAverageGrade()) {
                leader = st;
            }
        }
        System.out.println("\nLider po uspevaemosti: " + leader);

        ArrayList<Student> honorRoll = new ArrayList<>();
        for (Student st : group) {
            if (st.isExcellent()) {
                honorRoll.add(st);
            }
        }

        String[] honorNames = new String[honorRoll.size()];
        for (int i = 0; i < honorRoll.size(); i++) {
            honorNames[i] = honorRoll.get(i).getName();
        }
        System.out.println("Massiv imyon otlichnikov: " + Arrays.toString(honorNames));

        group.sort(Comparator.comparingDouble(Student::getAverageGrade).reversed());
        System.out.println("\nRejting sovershennoletnih studentov po ubyvaniyu srednego balla:");
        for (Student st : group) {
            System.out.println("  • " + st);
        }
    }
}
