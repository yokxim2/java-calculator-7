package calculator.tool;

import java.util.ArrayList;
import java.util.List;

public class JavaCalculator {

    public List<String> partitions;

    public JavaCalculator() {
        partitions = new ArrayList<>();
        partitions.add(",");
        partitions.add(":");
    }

    public int solution(String input) {
        if (input == null || input.isEmpty()) {
            return 0;
        }

        if (input.startsWith("//")) {
            input = addCustomPartition(input);
        }

        if (isNumeric(input)) {
            return Integer.parseInt(input);
        }

        List<Integer> numbers = new ArrayList<>();
        splitAndAddNumbers(input, numbers);

        checkIfNegativeNumberExists(numbers);

        return numbers.stream().mapToInt(Integer::intValue).sum();
    }

    private void splitAndAddNumbers(String input, List<Integer> numbers) {
        boolean fullyProcessed = false;
        for (String partition : partitions) {
            String[] rawData = input.split(partition);

            if (rawData.length > 1) {
                fullyProcessed = true;

                for (String rawDatum : rawData) {
                    if (isNumeric(rawDatum)) {
                        numbers.add(Integer.parseInt(rawDatum));
                    } else {
                        splitAndAddNumbers(rawDatum, numbers);
                    }
                }
                break;
            }
        }

        if (!fullyProcessed && !isNumeric(input)) {
            throw new IllegalArgumentException("Inappropriate type");
        }
    }

    private String addCustomPartition(String input) {
        String customPartition = input.charAt(2) + "";
        partitions.add(customPartition);
        return input.substring(5);
    }

    private boolean isNumeric(String rawDatum) {
        try {
            Integer.parseInt(rawDatum);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void checkIfNegativeNumberExists(List<Integer> numbers) {
        for (int number : numbers) {
            if (number < 0) {
                throw new IllegalArgumentException("Negative number not allowed");
            }
        }
    }
}
