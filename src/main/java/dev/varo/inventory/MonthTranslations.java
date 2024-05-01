package dev.varo.inventory;

public final class MonthTranslations {
    public static String convertToNumber(String monthString) {
        return switch (monthString) {
            case "January" -> "01";
            case "February" -> "02";
            case "March" -> "03";
            case "April" -> "04";
            case "May" -> "05";
            case "June" -> "06";
            case "July" -> "07";
            case "August" -> "08";
            case "September" -> "09";
            case "October" -> "10";
            case "November" -> "11";
            case "December" -> "12";
            default -> throw new RuntimeException("Given month value is incorrect %s".format(monthString));
        };
    }
}
