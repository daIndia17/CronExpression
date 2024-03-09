package cronExpression.utility;

import org.springframework.stereotype.Service;

import java.util.Arrays;


/**
 * @author : Arjun Das
 * @since : 08-03-2024
 */

@Service
public class CommonUtilService {

    /**
     * @param expression
     * @return boolean
     */
    public boolean isValidCronExpression(String expression) {
        String[] cronParts = expression.split("\\s+");
        if (cronParts.length < 5) {
            return false; // Invalid number of parts
        }

        return isValidMinute(cronParts[0]) &&
                isValidHour(cronParts[1]) &&
                isValidDayOfMonth(cronParts[2]) &&
                isValidMonth(cronParts[3]) &&
                isValidDayOfWeek(cronParts[4]);
    }

    private static boolean isValidMinute(String minute) {
        if (minute.equals("*")) {
            return true; // Wildcard, always valid
        } else if (minute.startsWith("*/")) {
            try {
                int step = Integer.parseInt(minute.substring(2));
                return step > 0 && step <= 59; // Step must be between 1 and 59
            } catch (NumberFormatException e) {
                return false; // Invalid step
            }
        } else if (minute.contains("/")) {
            try {
                String[] parts = minute.split("/");
                int start = Integer.parseInt(parts[0]);
                int step = Integer.parseInt(parts[1]);
                return start >= 0 && start <= 59 && step > 0 && step <= 59; // Start and step must be valid
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                return false; // Invalid format or missing parts
            }
        } else {
            return isValidField(minute, 0, 59);
        }
    }


    private static boolean isValidHour(String hour) {
        if (hour.equals("*")) {
            return true; // Wildcard, always valid
        } else if (hour.startsWith("*/")) {
            try {
                int step = Integer.parseInt(hour.substring(2));
                return step > 0 && step <= 23; // Step must be between 1 and 23
            } catch (NumberFormatException e) {
                return false; // Invalid step
            }
        } else if (hour.contains("/")) {
            try {
                String[] parts = hour.split("/");
                int start = Integer.parseInt(parts[0]);
                int step = Integer.parseInt(parts[1]);
                return start >= 0 && start <= 23 && step > 0 && step <= 23; // Start and step must be valid
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                return false; // Invalid format or missing parts
            }
        } else {
            return isValidField(hour, 0, 23);
        }

    }

    private static boolean isValidDayOfMonth(String dayOfMonth) {
        if (dayOfMonth.equals("*")) {
            return true; // Wildcard, always valid
        } else if (dayOfMonth.startsWith("*/")) {
            try {
                int step = Integer.parseInt(dayOfMonth.substring(2));
                return step > 0 && step <= 31; // Step must be between 1 and 31
            } catch (NumberFormatException e) {
                return false; // Invalid step
            }
        } else if (dayOfMonth.contains("/")) {
            try {
                String[] parts = dayOfMonth.split("/");
                int start = Integer.parseInt(parts[0]);
                int step = Integer.parseInt(parts[1]);
                return start >= 1 && start <= 31 && step > 0 && step <= 31; // Start and step must be valid
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                return false; // Invalid format or missing parts
            }
        } else {
            return isValidField(dayOfMonth, 1, 31);
        }
    }

    private static boolean isValidMonth(String month) {
        return isValidField(month, 1, 12);
    }

    private static boolean isValidDayOfWeek(String dayOfWeek) {
        return isValidField(dayOfWeek, 0, 6);
    }

    private static boolean isValidField(String field, int min, int max) {
        if (field.equals("*") || field.equals("?")) {
            return true; // Wildcard, always valid
        }

        String[] parts = field.split(",");
        for (String part : parts) {
            if (part.contains("-")) {
                String[] range = part.split("-");
                try {
                    int start = Integer.parseInt(range[0]);
                    int end = Integer.parseInt(range[1]);
                    if (start < min || end > max || start > end) {
                        // Invalid range
                        return false;
                    }
                } catch (NumberFormatException e) {
                    // Invalid range values
                    return false;
                }
            } else {
                try {
                    int value = Integer.parseInt(part);
                    if (value < min || value > max) {
                        // Out of range
                        return false;
                    }
                } catch (NumberFormatException e) {
                    // Not a numeric value, check if it's a valid day of week
                    if (!isValidWeekDay(part)) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    private static boolean isValidWeekDay(String dayOfWeek) {
        String[] daysOfWeek = {"SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"};
        String upperCaseDay = dayOfWeek.toUpperCase();
        return Arrays.asList(daysOfWeek).contains(upperCaseDay);
    }
}
