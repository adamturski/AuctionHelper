package pl.com.turski.ah.model.view;

/**
 * User: Adam
 */
public enum Step {
    GALLERY_CHOOSE("Krok pierwszy: Wskaż folder ze zdjęciami"), SEND_TO_FTP(""), FILL_ATTRIBUTES(""), CHECK_RESULTS("");

    private String stepTitle;

    private Step(String stepTitle) {
        this.stepTitle = stepTitle;
    }

    public String getStepTitle() {
        return stepTitle;
    }
}
