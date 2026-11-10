package com.example.pr3;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class PracticalWork3 {

    private static final Random RNG = new Random();
    private static int previousGeneratedNumber = Integer.MIN_VALUE;

    public static void main(String[] args) {
        checkSign(-42);
        printStringLength("Programmirovanie");
        printLastCharacter("Algoritm");
        checkParity(18);
        compareFirstLetters("Vostok", "veter");
        printFirstDigit(-9481);
        printLastDigit(-9481);
        printSumOfBoundaryDigits(7395);
        printDigitCount(-10500);
        checkSameLeadingDigits(841, 8999);
        printOneToHundred();
        printPenultimateChar("Konsol'");
        printRemainder(29, 6);
        printSeasonByMonth((byte) 4);
        printMultiplesOfFive(new int[]{10, 14, -25, 3, 50, 0, 7});
        printFirstThreeChars("abcdef");
        checkCharCase('G');
        sliceFirstThree(new int[]{1, 2, 3, 4, 5, 6});
        findFirstZeroIndex(new int[]{1, 2, 3, 0, 4, 5});
        replaceHyphensWithSpaces("123-456-789");
        filterHttpLinks(new String[]{"http://site.org", "https://secure.net", "http://docs.io", "ftp://files"});
        findFirstZeroInString("1203405");
        printLastThreeChars("abcdef");
        sliceMiddleThree(new int[]{1, 2, 3, 4, 5, 6});
        splitIntoWords("abc def jhi");

        checkDuplicateDigits(133);
        printMatrixElements(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}});
        printPyramid();
        checkDigitsOnly("908172");
        checkFractionString("45.678");

        System.out.println("Vtoroe po velichine: " + findSecondLargest(new int[]{12, 45, 7, 45, 39, 18}));
        System.out.println("Massiv v diapazone: " + Arrays.toString(buildRangeArray(9, 2)));
        System.out.println("Sluchajnye latinskie bukvy: " + Arrays.toString(generateRandomLatinArray(7)));
        System.out.println("Summa pervyh 6 chisel Fibonachchi: " + sumFibonacciNumbers(6));
        System.out.println("Data 2025-02-29 korrektna? " + isValidIsoDate("2025-02-29"));
        System.out.println("Sutok v 259200 sekundah: " + convertSecondsToDays(259200));
        System.out.println("Sluchajnaya latinskaya stroka: " + randomLatinString(10));
        System.out.println("Abbreviatura: " + makeAcronym("ob\"ektno orientirovannoe programmirovanie"));
        System.out.println("Deliteli elementov: " + Arrays.deepToString(replaceWithDivisors(new int[]{12, 15})));
        System.out.println("Slova po alfavitu: " + sortWordsAlphabetically("grusha yabloko abrikos sliva banan"));
        System.out.println("Obshchie elementy massivov: " + Arrays.toString(intersectArrays(new int[]{2, 4, 6, 8}, new int[]{1, 4, 8, 9})));

        System.out.print("Sluchajnye chisla bez povtorov podryad: ");
        for (int i = 0; i < 6; i++) {
            System.out.print(nextNonRepeatingRandom() + " ");
        }
        System.out.println();

        System.out.println("Fil'traciya chastyh dublej (>3): "
                + Arrays.toString(removeFrequentDuplicates(new int[]{7, 7, 7, 7, 2, 2, 2, 9, 9})));
        System.out.println("Samoe dlinnoe slovo: " + findLongestWord("Razrabotka mnogoklassovogo konsol'nogo prilozheniya"));

        System.out.println("Daty s chetyr'mya dvojkami v tekushchem stoletii:");
        printDatesWithFourTwos();

        System.out.println("Kolichestvo pyatnic 13-e v tekushchem godu: " + countFridayThe13ths(LocalDate.now().getYear()));
        buildRepeatedDigitsString();
        System.out.println("Mozhno sobrat' schastlivyj bilet: "
                + canFormLuckyTicket(new int[]{1, 4, 2, 3, 5, 1}));
        System.out.println("Sluchajnoe slovo na bukvu 'k': "
                + pickRandomWordByLetter(new String[]{"kod", "massiv", "kompilyator", "klass", "stroka"}, 'k'));
        System.out.println("Mnozhestvennoe chislo: city -> " + toEnglishPlural("city")
                + ", bus -> " + toEnglishPlural("bus") + ", book -> " + toEnglishPlural("book"));

        System.out.println("Druzhstvennye chisla do 3000:");
        printAmicablePairs(1, 3000);

        System.out.println("Razlozhenie chisla 360 na prostye mnozhiteli:");
        printSchoolPrimeFactorization(360);

        zeroOutMainDiagonal(new int[][]{
                {11, 12, 13, 14, 15},
                {21, 22, 23, 24, 25},
                {31, 32, 33, 34, 35},
                {41, 42, 43, 44, 45},
                {51, 52, 53, 54, 55}
        });

        System.out.println(func(1, "yabloko", "yabloka", "yablok"));
        System.out.println(func(2, "yabloko", "yabloka", "yablok"));
        System.out.println(func(5, "yabloko", "yabloka", "yablok"));
        System.out.println(func(11, "yabloko", "yabloka", "yablok"));
        System.out.println(func(21, "yabloko", "yabloka", "yablok"));
        System.out.println(func(1223421, "yabloko", "yabloka", "yablok"));

        sumTwoConsoleNumbers();
    }

    // 1. Проверка знака числа
    public static void checkSign(int number) {
        System.out.println(number < 0 ? "Otricatel'noe" : "Neotricatel'noe");
    }

    // 2. Длина строки
    public static void printStringLength(String text) {
        System.out.println("Dlina stroki: " + (text == null ? 0 : text.length()));
    }

    // 3. Последний символ строки
    public static void printLastCharacter(String text) {
        if (text != null && !text.isEmpty()) {
            System.out.println("Poslednij simvol: " + text.charAt(text.length() - 1));
        }
    }

    // 4. Чётность числа
    public static void checkParity(int number) {
        System.out.println((number & 1) == 0 ? "Chyotnoe" : "Nechyotnoe");
    }

    // 5. Совпадение первых букв двух слов
    public static void compareFirstLetters(String firstWord, String secondWord) {
        if (firstWord != null && secondWord != null && !firstWord.isEmpty() && !secondWord.isEmpty()) {
            boolean same = Character.toLowerCase(firstWord.charAt(0)) == Character.toLowerCase(secondWord.charAt(0));
            System.out.println("Pervye Live bukvy sovpadayut: " + same);
        }
    }

    // 6. Первая цифра целого числа (математически)
    public static void printFirstDigit(int number) {
        long n = Math.abs((long) number);
        while (n >= 10) {
            n /= 10;
        }
        System.out.println("Pervaya cifra: " + n);
    }

    // 7. Последняя цифра целого числа
    public static void printLastDigit(int number) {
        System.out.println("Poslednyaya cifra: " + Math.abs(number % 10));
    }

    // 8. Сумма первой и последней цифры
    public static void printSumOfBoundaryDigits(int number) {
        long n = Math.abs((long) number);
        int last = (int) (n % 10);
        while (n >= 10) {
            n /= 10;
        }
        System.out.println("Summa pervoj i poslednej cifry: " + (n + last));
    }

    // 9. Количество цифр в числе
    public static void printDigitCount(int number) {
        long n = Math.abs((long) number);
        int digits = 1;
        while (n >= 10) {
            n /= 10;
            digits++;
        }
        System.out.println("Kolichestvo cifr: " + digits);
    }

    // 10. Совпадение первых цифр двух чисел
    public static void checkSameLeadingDigits(int a, int b) {
        long firstA = Math.abs((long) a);
        while (firstA >= 10) firstA /= 10;
        long firstB = Math.abs((long) b);
        while (firstB >= 10) firstB /= 10;
        System.out.println("Pervye cifry sovpadayut: " + (firstA == firstB));
    }

    // 11. Числа от 1 до 100
    public static void printOneToHundred() {
        for (int i = 1; i <= 100; i++) {
            System.out.print(i + (i == 100 ? "\n" : " "));
        }
    }

    // 12. Предпоследний символ строки
    public static void printPenultimateChar(String text) {
        if (text != null && text.length() > 1) {
            System.out.println("Predposlednij simvol: " + text.charAt(text.length() - 2));
        }
    }

    // 13. Остаток от деления
    public static void printRemainder(int dividend, int divisor) {
        if (divisor != 0) {
            System.out.println("Ostatok: " + (dividend % divisor));
        }
    }

    // 14. Пора года по номеру месяца
    public static void printSeasonByMonth(byte month) {
        String season;
        if (month == 12 || month == 1 || month == 2) season = "Zima";
        else if (month >= 3 && month <= 5) season = "Vesna";
        else if (month >= 6 && month <= 8) season = "Leto";
        else if (month >= 9 && month <= 11) season = "Osen'";
        else season = "Nekorrektnyj mesyac";
        System.out.println("Pora goda: " + season);
    }

    // 15. Числа массива, кратные 5
    public static void printMultiplesOfFive(int[] values) {
        System.out.print("Delyatsya na 5: ");
        for (int v : values) {
            if (v % 5 == 0) System.out.print(v + " ");
        }
        System.out.println();
    }

    // 16. Первые три символа строки
    public static void printFirstThreeChars(String str) {
        if (str != null && str.length() >= 3) {
            System.out.println(str.substring(0, 3));
        }
    }

    // 17. Регистр символа
    public static void checkCharCase(char ch) {
        if (Character.isUpperCase(ch)) System.out.println("Verhnij registr");
        else if (Character.isLowerCase(ch)) System.out.println("Nizhnij registr");
        else System.out.println("Simvol vne registra bukv");
    }

    // 18. Срез первых трёх элементов
    public static void sliceFirstThree(int[] arr) {
        int[] slice = Arrays.copyOfRange(arr, 0, Math.min(3, arr.length));
        System.out.println(Arrays.toString(slice));
    }

    // 19. Позиция первого нуля в массиве
    public static void findFirstZeroIndex(int[] arr) {
        int foundIndex = -1;
        for (int i = 0; i < arr.length; i++) {
if (arr[i] == 0) {
        foundIndex = i;
        break;
    }
}
System.out.println("Poziciya pervogo nulya: " + foundIndex);
}
// 20. Замена дефисов на пробелы
public static void replaceHyphensWithSpaces(String str) {
    System.out.println(str.replace('-', ' '));
}
// 21. Строки, начинающиеся на http://
public static void filterHttpLinks(String[] links) {
    for (String link : links) {
        if (link != null && link.startsWith("http://")) {
            System.out.println(link);
        }
    }
}
// 22. Позиция первого нуля в строке
public static void findFirstZeroInString(String str) {
    System.out.println("Indeks '0': " + str.indexOf('0'));
}
// 23. Три последних символа строки
public static void printLastThreeChars(String str) {
    if (str != null && str.length() >= 3) {
        System.out.println(str.substring(str.length() - 3));
    }
}
// 24. Срез [3, 4, 5] из массива [1, 2, 3, 4, 5, 6]
public static void sliceMiddleThree(int[] arr) {
    if (arr.length >= 5) {
        System.out.println(Arrays.toString(Arrays.copyOfRange(arr, 2, 5)));
    }
}
// 25. Массив слов из строки
public static void splitIntoWords(String str) {
    String[] parts = str.trim().split("\\s+");
    System.out.println(Arrays.toString(parts));
}
// 26. Сумма двух введённых чисел
public static void sumTwoConsoleNumbers() {
    Scanner sc = new Scanner(System.in);
    System.out.print("Vvedite pervoe slagaemoe: ");
    if (!sc.hasNextDouble()) return;
    double x = sc.nextDouble();
    System.out.print("Vvedite vtoroe slagaemoe: ");
    if (!sc.hasNextDouble()) return;
    double y = sc.nextDouble();
    System.out.println("Summa vvedyonnyh chisel: " + (x + y));
}
// 27. Проверка одинаковых цифр в числе
public static void checkDuplicateDigits(int num) {
    int mask = 0;
    long n = Math.abs((long) num);
    boolean duplicateFound = false;
    if (n == 0) {
        duplicateFound = false;
    } else {
        while (n > 0) {
            int digit = (int) (n % 10);
            if ((mask & (1 << digit)) != 0) {
                duplicateFound = true;
                break;
            }
            mask |= (1 << digit);
            n /= 10;
        }
    }
    System.out.println("Est' odinakovye cifry v " + num + ": " + duplicateFound);
}
// 28. Вывод двумерного массива
public static void printMatrixElements(int[][] matrix) {
    for (int[] row : matrix) {
        for (int cell : row) {
            System.out.print(cell + " ");
        }
        System.out.println();
    }
}
// 29. Вывод пирамидки из 'x'
public static void printPyramid() {
    for (int row = 1; row <= 5; row++) {
        for (int k = 0; k < row * 3; k++) {
            System.out.print('x');
        }
        System.out.println();
    }
}
// 30. Строка состоит только из цифр
public static void checkDigitsOnly(String str) {
    boolean allDigits = str != null && !str.isEmpty();
    if (allDigits) {
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                allDigits = false;
                break;
            }
        }
    }
    System.out.println("Tol'ko cifry: " + allDigits);
}
// 31. Строка представляет собой дробь
public static void checkFractionString(String str) {
    boolean isFraction = str != null && str.matches("[-+]?\\d+[.,/\\\\]\\d+");
    System.out.println("Yavlyaetsya drobyu: " + isFraction);
}
// 32. Второе по величине число в массиве
public static int findSecondLargest(int[] arr) {
    int max1 = Integer.MIN_VALUE;
    int max2 = Integer.MIN_VALUE;
    for (int val : arr) {
        if (val > max1) {
            max2 = max1;
            max1 = val;
        } else if (val > max2 && val < max1) {
            max2 = val;
        }
    }
    return max2;
}
// 33. Массив целых чисел от min до max
public static int[] buildRangeArray(int p1, int p2) {
    int low = Math.min(p1, p2);
    int high = Math.max(p1, p2);
    int[] seq = new int[high - low + 1];
    for (int i = 0; i < seq.length; i++) {
        seq[i] = low + i;
    }
    return seq;
}
// 34. Заполнение массива случайными латинскими буквами
public static char[] generateRandomLatinArray(int size) {
    char[] letters = new char[size];
    for (int i = 0; i < size; i++) {
        letters[i] = (char) ('a' + RNG.nextInt(26));
    }
    return letters;
}
// 35. Сумма N первых чисел Фибоначчи
public static long sumFibonacciNumbers(int n) {
    if (n <= 0) return 0;
    long prev = 0;
    long curr = 1;
    long total = 0;
    for (int i = 1; i <= n; i++) {
        total += curr;
        long next = prev + curr;
        prev = curr;
        curr = next;
    }
    return total;
}
// 36. Проверка существования даты год-месяц-день
public static boolean isValidIsoDate(String dateText) {
    try {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("uuuu-MM-dd")
                .withResolverStyle(ResolverStyle.STRICT);
        LocalDate.parse(dateText, fmt);
        return true;
    } catch (Exception e) {
        return false;
    }
}
// 37. Перевод секунд в сутки
public static double convertSecondsToDays(long seconds) {
    return seconds / 86_400.0;
}
// 38. Генерация случайной строки заданной длины
public static String randomLatinString(int length) {
    StringBuilder sb = new StringBuilder(length);
    for (int i = 0; i < length; i++) {
        sb.append((char) ('a' + RNG.nextInt(26)));
    }
    return sb.toString();
}
// 39. Верхний регистр из первых букв слов
public static String makeAcronym(String phrase) {
    if (phrase == null || phrase.trim().isEmpty()) return "";
    String[] tokens = phrase.trim().split("\\s+");
    StringBuilder out = new StringBuilder();
    for (String token : tokens) {
        out.append(Character.toUpperCase(token.charAt(0)));
    }
    return out.toString();
}
// 40. Замена каждого числа на массив его делителей
public static int[][] replaceWithDivisors(int[] numbers) {
    int[][] output = new int[numbers.length][];
    for (int i = 0; i < numbers.length; i++) {
        int target = Math.abs(numbers[i]);
        int count = 0;
        for (int d = 1; d <= target; d++) {
            if (target % d == 0) count++;
        }
        int[] divs = new int[count];
        int idx = 0;
        for (int d = 1; d <= target; d++) {
            if (target % d == 0) divs[idx++] = d;
        }
        output[i] = divs;
    }
    return output;
}
// 41. Сортировка слов текста в алфавитном порядке
public static String sortWordsAlphabetically(String text) {
    if (text == null || text.trim().isEmpty()) return "";
    String[] words = text.trim().split("\\s+");
    Arrays.sort(words, String.CASE_INSENSITIVE_ORDER);
    return String.join(" ", words);
}
// 42. Массив общих элементов двух массивов
public static int[] intersectArrays(int[] first, int[] second) {
    int[] temp = new int[Math.min(first.length, second.length)];
    int count = 0;
    for (int a : first) {
        boolean inSecond = false;
        for (int b : second) {
            if (a == b) {
                inSecond = true;
                break;
            }
        }
        if (inSecond) {
            boolean alreadyAdded = false;
            for (int k = 0; k < count; k++) {
                if (temp[k] == a) {
                    alreadyAdded = true;
                    break;
                }
            }
            if (!alreadyAdded) {
                temp[count++] = a;
            }
        }
    }
    return Arrays.copyOf(temp, count);
}
// 43. Случайное число без повторения два раза подряд
public static int nextNonRepeatingRandom() {
    int candidate;
    do {
        candidate = RNG.nextInt(50);
    } while (candidate == previousGeneratedNumber);
    previousGeneratedNumber = candidate;
    return candidate;
}
// 44. Удаление дублей, которые встречаются больше трёх раз
public static int[] removeFrequentDuplicates(int[] source) {
    int[] temp = new int[source.length];
    int idx = 0;
    for (int candidate : source) {
        int freq = 0;
        for (int item : source) {
            if (item == candidate) freq++;
        }
        if (freq <= 3) {
            temp[idx++] = candidate;
        }
    }
    return Arrays.copyOf(temp, idx);
}
// 45. Самое длинное слово из строки
public static String findLongestWord(String sentence) {
    if (sentence == null || sentence.trim().isEmpty()) return "";
    String[] words = sentence.trim().split("\\s+");
    String best = words[0];
    for (String w : words) {
        if (w.length() > best.length()) {
            best = w;
        }
    }
    return best;
}
// 46. Все даты с 4 двойками в текущем столетии
public static void printDatesWithFourTwos() {
    LocalDate cursor = LocalDate.of(2001, 1, 1);
    LocalDate end = LocalDate.of(2100, 12, 31);
    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    int printed = 0;
    while (!cursor.isAfter(end)) {
        String formatted = cursor.format(fmt);
        int twos = 0;
        for (int i = 0; i < formatted.length(); i++) {
            if (formatted.charAt(i) == '2') twos++;
        }
        if (twos == 4) {
            System.out.print(formatted + "  ");
            printed++;
            if (printed % 8 == 0) System.out.println();
        }
        cursor = cursor.plusDays(1);
    }
    System.out.println("\nVsego najdeno dat: " + printed);
}
// 47. Количество пятниц 13-е в году
public static int countFridayThe13ths(int year) {
    int total = 0;
    for (int m = 1; m <= 12; m++) {
        if (LocalDate.of(year, m, 13).getDayOfWeek() == DayOfWeek.FRIDAY) {
            total++;
        }
    }
    return total;
}
// 48. Формирование строки по шаблону
public static void buildRepeatedDigitsString() {
    StringBuilder sb = new StringBuilder();
    for (int i = 1; i <= 9; i++) {
        sb.append(i);
        if (i < 9) sb.append('-');
    }
    System.out.println("Sformirovannaya stroka: " + sb);
}
// 49. Проверка возможности собрать счастливый билет из 6 цифр
public static boolean canFormLuckyTicket(int[] sixDigits) {
    if (sixDigits == null || sixDigits.length != 6) return false;
    int totalSum = 0;
    for (int d : sixDigits) totalSum += d;
    if (totalSum % 2 != 0) return false;
    int half = totalSum / 2;
    for (int i = 0; i < 4; i++) {
        for (int j = i + 1; j < 5; j++) {
            for (int k = j + 1; k < 6; k++) {
                if (sixDigits[i] + sixDigits[j] + sixDigits[k] == half) {
                    return true;
                }
            }
        }
    }
    return false;
}
// 50. Случайное слово из массива на заданную букву
public static String pickRandomWordByLetter(String[] dictionary, char prefix) {
    String[] matches = new String[dictionary.length];
    int count = 0;
    char target = Character.toLowerCase(prefix);
    for (String word : dictionary) {
        if (word != null && !word.isEmpty() && Character.toLowerCase(word.charAt(0)) == target) {
            matches[count++] = word;
        }
    }
    return count == 0 ? null : matches[RNG.nextInt(count)];
}
// 51. Английское существительное во множественном числе
public static String toEnglishPlural(String singular) {
    if (singular == null || singular.isEmpty()) return singular;
    String lower = singular.toLowerCase();
    if (lower.endsWith("s") || lower.endsWith("ss") || lower.endsWith("sh")
            || lower.endsWith("ch") || lower.endsWith("x") || lower.endsWith("z")) {
        return singular + "es";
    }
    if (lower.endsWith("y") && singular.length() > 1) {
        char beforeY = lower.charAt(lower.length() - 2);
        if ("aeiou".indexOf(beforeY) == -1) {
            return singular.substring(0, singular.length() - 1) + "ies";
        }
    }
    return singular + "s";
}
// 52. Пары дружественных чисел в промежутке
public static void printAmicablePairs(int from, int to) {
    for (int a = Math.max(2, from); a <= to; a++) {
        int b = sumProperDivisors(a);
        if (b > a && b <= to && sumProperDivisors(b) == a) {
            System.out.println("Druzhstvennaya para: " + a + " i " + b);
        }
    }
}
private static int sumProperDivisors(int n) {
    if (n <= 1) return 0;
    int sum = 1;
    int limit = (int) Math.sqrt(n);
    for (int d = 2; d <= limit; d++) {
        if (n % d == 0) {
            sum += d;
            int pair = n / d;
            if (pair != d) sum += pair;
        }
    }
    return sum;
}
// 53. Разложение числа на простые множители столбиком (как в школе)
public static void printSchoolPrimeFactorization(int number) {
    int current = Math.abs(number);
    int factor = 2;
    while (current > 1) {
        if (current % factor == 0) {
            System.out.printf("%6d | %d%n", current, factor);
            current /= factor;
        } else {
            factor++;
        }
    }
    System.out.printf("%6d |%n", 1);
}
// 54. Обнуление главной диагонали двумерного массива
public static void zeroOutMainDiagonal(int[][] matrix) {
    for (int i = 0; i < matrix.length; i++) {
        if (i < matrix[i].length) {
            matrix[i][i] = 0;
        }
    }
    System.out.println("Matrica s obnulyonnoj diagonal'yu: " + Arrays.deepToString(matrix));
}
// 55. Склонение существительного после числа
public static String func(long number, String oneForm, String twoFourForm, String fiveForm) {
    long mod100 = Math.abs(number) % 100;
    long mod10 = mod100 % 10;
    String chosen;
    if (mod100 >= 11 && mod100 <= 19) {
        chosen = fiveForm;
    } else if (mod10 == 1) {
        chosen = oneForm;
    } else if (mod10 >= 2 && mod10 <= 4) {
        chosen = twoFourForm;
    } else {
        chosen = fiveForm;
    }
    return number + " " + chosen;
}
}