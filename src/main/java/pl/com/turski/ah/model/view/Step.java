package pl.com.turski.ah.model.view;

/**
 * User: Adam
 */
public enum Step {
    DIRECTORY_CHOOSE("Krok 1: Wskaż folder ze zdjęciami"),
    GALLERY_CREATE("Krok 2: Generuj galerię"),
    SEND_TO_FTP("Krok 3: Prześlij galerię na serwer"),
    FILL_TEMPLATE("Krok 4: Wypełnij szablon"),
    CHECK_RESULTS("Krok 5: Sprawdź wynik");

    private String stepTitle;

    private Step(String stepTitle) {
        this.stepTitle = stepTitle;
    }

    public String getStepTitle() {
        return stepTitle;
    }
}
