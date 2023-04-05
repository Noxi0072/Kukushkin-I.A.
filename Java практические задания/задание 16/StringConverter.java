public class StringConverter {
    public static String convert(String input) {
        int uppercaseCount = 0;
        int lowercaseCount = 0;
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (Character.isUpperCase(c)) {
                uppercaseCount++;
            } else if (Character.isLowerCase(c)) {
                lowercaseCount++;
            }
        }
        if (uppercaseCount > lowercaseCount) {
            return input.toUpperCase();
        } else if (lowercaseCount > uppercaseCount) {
            return input.toLowerCase();
        } else {
            return input.toLowerCase();
        }
    }
}
