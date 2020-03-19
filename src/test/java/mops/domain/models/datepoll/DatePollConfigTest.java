package mops.domain.models.datepoll;

import mops.domain.models.FieldErrorNames;
import mops.domain.models.Validation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings({"PMD.LawOfDemeter"})
public class DatePollConfigTest { //NOPMD
    private Validation testValidator; //NOPMD

    /**
     * Erstellt leeren validator der in jedem Test angepasst wird.
     */
    @BeforeEach
    public void setValidator() {
        this.testValidator = Validation.noErrors();
    }

    @Test
    public void configConflict() {
        final DatePollConfig config = new DatePollConfig(false, true, true, false, false);
        this.testValidator = this.testValidator.appendValidation(
                new Validation(FieldErrorNames.DATE_POLL_CONFIGURATION_CONFLICT));
        final Validation validator = config.validate();
        assertThat(this.testValidator.getErrorMessages()).isEqualTo(validator.getErrorMessages());
    }

    @Test
    public void validConfig1() {
        final DatePollConfig config = new DatePollConfig(false, true, false, false, false);
        final Validation validator = config.validate();
        assertThat(this.testValidator.getErrorMessages()).isEqualTo(validator.getErrorMessages());
    }

    @Test
    public void validConfig2() {
        final DatePollConfig config = new DatePollConfig(false, false, true, false, false);
        final Validation validator = config.validate();
        assertThat(this.testValidator.getErrorMessages()).isEqualTo(validator.getErrorMessages());
    }

    @Test
    public void validConfig3() {
        final DatePollConfig config = new DatePollConfig(false, false, false, false, false);
        final Validation validator = config.validate();
        assertThat(this.testValidator.getErrorMessages()).isEqualTo(validator.getErrorMessages());
    }
}
