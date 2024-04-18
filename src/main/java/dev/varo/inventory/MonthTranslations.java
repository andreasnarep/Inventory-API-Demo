package dev.varo.inventory;

public final class MonthTranslations {
    public static String translateToEnglish(String monthEst) {
        return switch (monthEst) {
            case "Jaanuar" -> "01";
            case "Veebruar" -> "02";
            case "Märts" -> "03";
            case "Aprill" -> "04";
            case "Mai" -> "05";
            case "Juuni" -> "06";
            case "Juuli" -> "07";
            case "August" -> "08";
            case "September" -> "09";
            case "Oktoober" -> "10";
            case "November" -> "11";
            case "Detsember" -> "12";
            default -> throw new RuntimeException("Given month value is incorrect %s".format(monthEst));
        };
    }
}
