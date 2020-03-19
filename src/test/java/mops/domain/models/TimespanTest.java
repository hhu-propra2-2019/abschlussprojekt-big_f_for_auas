package mops.domain.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class TimespanTest {
    private Validation testValidator;

    /**
     * Erstellt leeren validator der in jedem Test angepasst wird.
     */
    @BeforeEach
    public void setValidator() {
        this.testValidator = Validation.noErrors();
    }

    @Test
    public void nullStartTime() {
        final Timespan timespan = new Timespan(null, LocalDateTime.now());
        this.testValidator = this.testValidator.appendValidation(new Validation(FieldErrorNames.TIMESPAN_START_NULL));
        Validation validator = timespan.validate();
        assertThat(this.testValidator.getErrorMessages()).isEqualTo(validator.getErrorMessages());
    }

    @Test
    public void nullEndTime() {
        final Timespan timespan = new Timespan(LocalDateTime.now(), null);
        this.testValidator = this.testValidator.appendValidation(new Validation(FieldErrorNames.TIMESPAN_END_NULL));
        Validation validator = timespan.validate();
        assertThat(this.testValidator.getErrorMessages()).isEqualTo(validator.getErrorMessages());
    }

    @Test
    public void bothNull() {
        final Timespan timespan = new Timespan(null, null);
        this.testValidator = this.testValidator.appendValidation(new Validation(FieldErrorNames.TIMESPAN_START_NULL));
        this.testValidator = this.testValidator.appendValidation(new Validation(FieldErrorNames.TIMESPAN_END_NULL));
        Validation validator = timespan.validate();
        assertThat(this.testValidator.getErrorMessages()).isEqualTo(validator.getErrorMessages());
    }

    @Test
    public void swappedTime() {
        final Timespan timespan = new Timespan(LocalDateTime.now().plusMinutes(1), LocalDateTime.now());
        this.testValidator = this.testValidator.appendValidation(new Validation(FieldErrorNames.TIMESPAN_SWAPPED));
        Validation validator = timespan.validate();
        assertThat(this.testValidator.getErrorMessages()).isEqualTo(validator.getErrorMessages());
    }

    @Test
    public void sameTime() {
        final Timespan timespan = new Timespan(LocalDateTime.now(), LocalDateTime.now());
        this.testValidator = this.testValidator.appendValidation(new Validation(FieldErrorNames.TIMESPAN_SAME));
        Validation validator = timespan.validate();
        assertThat(this.testValidator.getErrorMessages()).isEqualTo(validator.getErrorMessages());
    }

    @Test
    public void validTime() {
        final Timespan timespan = new Timespan(LocalDateTime.now(), LocalDateTime.now().plusMinutes(1));
        Validation validator = timespan.validate();
        assertThat(this.testValidator.getErrorMessages()).isEqualTo(validator.getErrorMessages());
    }
}
