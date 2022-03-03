package model;

import javafx.scene.control.TextField;

public class Validation {

    public static double parseDouble(String value) {
        try {
            return Double.parseDouble(value.trim());
        } catch (NumberFormatException nfe) {
            System.out.println(nfe.getMessage());
        } return 0.0;
    }

    public static int parseInteger(String value) {
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException nfe) {
            System.out.println(nfe.getMessage());
        } return 0;
    }

    /**
     * Restrict a textField to only accept numbers
     * @param textField to restrict
     */
    public static void setOnlyNum(TextField textField){
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                textField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }
}

