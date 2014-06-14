package pl.com.turski.ah.model.view;

/**
 * User: Adam
 */
public enum Step {
    FOLDER_CHOOSE("Krok 1: Wskaż folder ze zdjęciami"),
    GALLERY_CREATE("Krok 2: Generowanie galerii"),
    SEND_TO_FTP("Krok 3: Wysłanie galerii na serwer"), FILL_ATTRIBUTES(""), CHECK_RESULTS("");

    private String stepTitle;

    private Step(String stepTitle) {
        this.stepTitle = stepTitle;
    }

    public String getStepTitle() {
        return stepTitle;
    }
}
