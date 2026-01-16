package com.interviewmate.codingservice.util;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class DefaultTestCaseInputNormalizer implements TestCaseInputNormalizer {

    @Override
    public String normalize(String rawInput) {

        if (rawInput == null || rawInput.isBlank()) {
            return "";
        }

        String input = rawInput.trim();

        // Fast-path: already canonical
        if (!input.contains("=")) {
            return normalizeSpacing(input);
        }

        // Split safely by commas (top-level only)
        List<String> parts = splitTopLevel(input);

        List<String> values = new ArrayList<>();

        for (String part : parts) {
            int idx = part.indexOf('=');
            if (idx == -1) {
                // defensive: treat whole part as value
                values.add(part.trim());
            } else {
                values.add(part.substring(idx + 1).trim());
            }
        }

        return String.join(" ", values);
    }

    /**
     * Splits input by commas, ignoring commas inside [] or ""
     */
    private List<String> splitTopLevel(String input) {

        List<String> result = new ArrayList<>();

        StringBuilder current = new StringBuilder();
        int bracketDepth = 0;
        boolean inQuotes = false;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);

            if (c == '"' && (i == 0 || input.charAt(i - 1) != '\\')) {
                inQuotes = !inQuotes;
            }

            if (!inQuotes) {
                if (c == '[') bracketDepth++;
                else if (c == ']') bracketDepth--;
            }

            if (c == ',' && bracketDepth == 0 && !inQuotes) {
                result.add(current.toString().trim());
                current.setLength(0);
            } else {
                current.append(c);
            }
        }

        if (current.length() > 0) {
            result.add(current.toString().trim());
        }

        return result;
    }

    /**
     * Normalizes multiple spaces to single spaces
     */
    private String normalizeSpacing(String input) {
        return input.replaceAll("\\s+", " ");
    }
}
