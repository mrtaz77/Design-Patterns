package util;

public interface DoubleToString {
	default String doubleToString(double value) {
        var formattedString = String.format("%.15g", value);
        return formattedString.contains(".") ? formattedString.replaceAll("0*$", "").replaceAll("\\.$", "") : formattedString;
    }
}
