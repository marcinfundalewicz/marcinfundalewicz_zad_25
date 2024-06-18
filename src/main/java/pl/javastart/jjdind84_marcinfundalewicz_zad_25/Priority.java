package pl.javastart.jjdind84_marcinfundalewicz_zad_25;

public enum Priority {
    HIGH("Wysoki"),
    MEDIUM("Średni"),
    LOW("Niski");

    private String polishTranslation;

    Priority(String polishTranslation) {
        this.polishTranslation = polishTranslation;
    }

    public String getPolishTranslation() {
        return polishTranslation;
    }

    public void setPolishTranslation(String polishTranslation) {
        this.polishTranslation = polishTranslation;
    }
}
