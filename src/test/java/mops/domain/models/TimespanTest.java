package mops.domain.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class TimespanTest { //NOPMD
    private Validation testValidator; //NOPMD

    /**
     * Erstellt leeren validator der in jedem Test angepasst wird.
     */
    @BeforeEach
    public void setValidator() {
        this.testValidator = Validation.noErrors();
    }

    @Test
    @SuppressWarnings({"PMD.LawOfDemeter"}) //NOPMD
    public void nullStartTime() {
        final Timespan timespan = new Timespan(null, LocalDateTime.now());
        this.testValidator = this.testValidator.appendValidation(new Validation(FieldErrorNames.TIMESPAN_START_NULL));
        final Validation validator = timespan.validate();
        assertThat(this.testValidator.getErrorMessages()).isEqualTo(validator.getErrorMessages());
    }

    @Test
    @SuppressWarnings({"PMD.LawOfDemeter"})
    public void nullEndTime() {
        final Timespan timespan = new Timespan(LocalDateTime.now(), null);
        this.testValidator = this.testValidator.appendValidation(new Validation(FieldErrorNames.TIMESPAN_END_NULL));
        final Validation validator = timespan.validate();
        assertThat(this.testValidator.getErrorMessages()).isEqualTo(validator.getErrorMessages());
    }

    @Test
    @SuppressWarnings({"PMD.LawOfDemeter"})
    public void bothNull() {
        final Timespan timespan = new Timespan(null, null);
        this.testValidator = this.testValidator
                .appendValidation(new Validation(FieldErrorNames.TIMESPAN_START_NULL))
                .appendValidation(new Validation(FieldErrorNames.TIMESPAN_END_NULL));
        final Validation validator = timespan.validate();
        assertThat(this.testValidator.getErrorMessages()).isEqualTo(validator.getErrorMessages());
    }

    @Test
    @SuppressWarnings({"PMD.LawOfDemeter"})
    public void swappedTime() {
        final Timespan timespan = new Timespan(LocalDateTime.now().plusMinutes(1), LocalDateTime.now());
        this.testValidator = this.testValidator.appendValidation(new Validation(FieldErrorNames.TIMESPAN_SWAPPED));
        final Validation validator = timespan.validate();
        assertThat(this.testValidator.getErrorMessages()).isEqualTo(validator.getErrorMessages());
    }

    @Test
    @SuppressWarnings({"PMD.LawOfDemeter"})
    public void sameTime() {
        final LocalDateTime time1 = LocalDateTime.now();
        final LocalDateTime time2 = time1;
        final Timespan timespan = new Timespan(time1, time2);
        this.testValidator = this.testValidator.appendValidation(new Validation(FieldErrorNames.TIMESPAN_SAME));
        final Validation validator = timespan.validate();
        assertThat(this.testValidator.getErrorMessages()).isEqualTo(validator.getErrorMessages());
    }

    @Test
    @SuppressWarnings({"PMD.LawOfDemeter"})
    public void validTime() {
        final Timespan timespan = new Timespan(LocalDateTime.now(), LocalDateTime.now().plusMinutes(1));
        final Validation validator = timespan.validate();
        assertThat(this.testValidator.getErrorMessages()).isEqualTo(validator.getErrorMessages());
    }
}
