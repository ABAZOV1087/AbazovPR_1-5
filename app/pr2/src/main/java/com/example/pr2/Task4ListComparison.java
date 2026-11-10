package com.example.pr2;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class Task4ListComparison {

    static class LinkedStack<E> {
        private final LinkedList<E> storage = new LinkedList<>();

        public void push(E item) {
            storage.addFirst(item);
        }

        public E pop() {
            return storage.isEmpty() ? null : storage.removeFirst();
        }

        public E peek() {
            return storage.peekFirst();
        }

        public boolean isEmpty() {
            return storage.isEmpty();
        }
    }

    public static void main(String[] args) {
        System.out.println("=== ZADANIE 4: Sravnenie ArrayList i LinkedList ===");

        final int baseSize = 100_000;
        final int sampleOps = 5_000;
        Random rng = new Random(42);

        ArrayList<Integer> arrList = new ArrayList<>(baseSize);
        LinkedList<Integer> lnkList = new LinkedList<>();

        for (int i = 0; i < baseSize; i++) {
            arrList.add(i);
            lnkList.add(i);
        }

        long t0 = System.nanoTime();
        for (int i = 0; i < sampleOps; i++) {
            arrList.get(rng.nextInt(arrList.size()));
        }
        double arrGetMs = (System.nanoTime() - t0) / 1_000_000.0;

        t0 = System.nanoTime();
        for (int i = 0; i < sampleOps; i++) {
            lnkList.get(rng.nextInt(lnkList.size()));
        }
        double lnkGetMs = (System.nanoTime() - t0) / 1_000_000.0;

        t0 = System.nanoTime();
        for (int i = 0; i < sampleOps; i++) {
            arrList.add(0, -i);
        }
        double arrInsertHeadMs = (System.nanoTime() - t0) / 1_000_000.0;

        t0 = System.nanoTime();
        for (int i = 0; i < sampleOps; i++) {
            lnkList.add(0, -i);
        }
        double lnkInsertHeadMs = (System.nanoTime() - t0) / 1_000_000.0;

        t0 = System.nanoTime();
        for (int i = 0; i < sampleOps; i++) {
            arrList.remove(0);
        }
        double arrRemoveHeadMs = (System.nanoTime() - t0) / 1_000_000.0;

        t0 = System.nanoTime();
        for (int i = 0; i < sampleOps; i++) {
            lnkList.remove(0);
        }
        double lnkRemoveHeadMs = (System.nanoTime() - t0) / 1_000_000.0;

        System.out.printf("Operaciya get(random) [5000 raz]: ArrayList = %.3f ms | LinkedList = %.3f ms%n",
                arrGetMs, lnkGetMs);
        System.out.printf("Operaciya add(0, val) [5000 raz]: ArrayList = %.3f ms | LinkedList = %.3f ms%n",
                arrInsertHeadMs, lnkInsertHeadMs);
        System.out.printf("Operaciya remove(0)   [5000 raz]: ArrayList = %.3f ms | LinkedList = %.3f ms%n",
                arrRemoveHeadMs, lnkRemoveHeadMs);

        System.out.println("\nAnaliticheskij vyvod:");
        System.out.println("- ArrayList vyigryvaet pri proizvol'nom dostupe po indeksu get(i) za schyot nepreryvnogo massiva v pamyati (slozhnost' O(1)).");
        System.out.println("- LinkedList vyigryvaet pri chastyh vstavkah i udaleniyah v nachale spiska add(0)/remove(0) za schyot pereprivyazki ssylok pervogo uzla (O(1) protiv O(n) sdviga massiva u ArrayList).");

        System.out.println("\nDemonstraciya bonusnogo steka na baze LinkedList:");
        LinkedStack<String> bookStack = new LinkedStack<>();
        bookStack.push("Chistyj kod");
        bookStack.push("Filosofiya Java");
        bookStack.push("Algoritmy i struktury dannyh");

        System.out.println("Vershina steka (peek): " + bookStack.peek());
        System.out.println("Snyatie so steka (pop): " + bookStack.pop());
        System.out.println("Novaya vershina (peek): " + bookStack.peek());
        System.out.println("Stek pust? " + bookStack.isEmpty());
    }
}

