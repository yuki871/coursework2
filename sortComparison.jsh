import java.util.*;
import java.nio.file.*;
import java.io.*;

ArrayList<String> loadCards(String fileName) {
    try {
        return new ArrayList<>(Files.readAllLines(Paths.get(fileName)));
    } catch (IOException e) {
        System.out.println("Error reading file " + fileName + ": " + e.getMessage());
        return new ArrayList<>(); // Return empty list on error
    }
}


ArrayList<String> bubbleSort(ArrayList<String> list) {
    int n = list.size();
    for (int i = 0; i < n - 1; i++) {
        for (int j = 0; j < n - i - 1; j++) {
            if (cardCompare(list.get(j), list.get(j + 1)).get(0) > 0) {
                Collections.swap(list, j, j + 1);
            }
        }
    }
    return list;
}


ArrayList<String> mergeSort(ArrayList<String> list) {
    if (list.size() <= 1) {
        return list;
    }
    int mid = list.size() / 2;
    ArrayList<String> left = new ArrayList<>(list.subList(0, mid));
    ArrayList<String> right = new ArrayList<>(list.subList(mid, list.size()));

    left = mergeSort(left);
    right = mergeSort(right);
    return merge(left, right);
}

ArrayList<String> merge(ArrayList<String> left, ArrayList<String> right) {
    ArrayList<String> merged = new ArrayList<>();
    int i = 0, j = 0;

    while (i < left.size() && j < right.size()) {
        if (cardCompare(left.get(i), right.get(j)).get(0) <= 0) {
            merged.add(left.get(i));
            i++;
        } else {
            merged.add(right.get(j));
            j++;
        }
    }
    while (i < left.size()) {
        merged.add(left.get(i));
        i++;
    }
    while (j < right.size()) {
        merged.add(right.get(j));
        j++;
    }
    return merged;
}


ArrayList<Integer> cardCompare(String card1, String card2) {
    String suits = "HCDS";
    ArrayList<Integer> result = new ArrayList<>();
    char suit1 = card1.charAt(card1.length() - 1);
    int number1 = Integer.parseInt(card1.substring(0, card1.length() - 1));
    char suit2 = card2.charAt(card2.length() - 1);
    int number2 = Integer.parseInt(card2.substring(0, card2.length() - 1));

    if (suits.indexOf(suit1) < suits.indexOf(suit2)) {
        result.add(-1);
    } else if (suits.indexOf(suit1) > suits.indexOf(suit2)) {
        result.add(1);
    } else {
        if (number1 < number2) {
            result.add(-1);
        } else if (number1 > number2) {
            result.add(1);
        } else {
            result.add(0);
        }
    }
    return result;
}


long measureBubbleSort(String fileName) {
    ArrayList<String> cards = loadCards(fileName);
    long startTime = System.currentTimeMillis();
    bubbleSort(cards);
    long endTime = System.currentTimeMillis();
    return endTime - startTime;
}


long measureMergeSort(String fileName) {
    ArrayList<String> cards = loadCards(fileName);
    long startTime = System.currentTimeMillis();
    mergeSort(cards);
    long endTime = System.currentTimeMillis();
    return endTime - startTime;
}

void sortComparison(String[] fileNames) {
    ArrayList<Integer> cardCounts = new ArrayList<>();
    ArrayList<Long> bubbleSortTimes = new ArrayList<>();
    ArrayList<Long> mergeSortTimes = new ArrayList<>();

    for (String fileName : fileNames) {
        try {
            ArrayList<String> cards = loadCards(fileName);
            cardCounts.add(cards.size());

            long bubbleTime = measureBubbleSort(fileName);
            long mergeTime = measureMergeSort(fileName);

            bubbleSortTimes.add(bubbleTime);
            mergeSortTimes.add(mergeTime);
        } catch (Exception e) {
            System.out.println("Error processing file " + fileName + ": " + e.getMessage());
            cardCounts.add(0);
            bubbleSortTimes.add(-1L);
            mergeSortTimes.add(-1L);
        }
    }

    System.out.print(", ");
    for (int count : cardCounts) {
        System.out.print(count + ", ");
    }
    System.out.println();

    System.out.print("bubbleSort, ");
    for (long time : bubbleSortTimes) {
        System.out.print(time + ", ");
    }
    System.out.println();

    System.out.print("mergeSort, ");
    for (long time : mergeSortTimes) {
        System.out.print(time + ", ");
    }
    System.out.println();
}



