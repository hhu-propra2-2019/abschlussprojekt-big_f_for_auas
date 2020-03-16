package mops.domain.models.datepoll;

import org.junit.jupiter.api.BeforeEach;

public class DatePollBuilderTest {
    private DatePollBuilder datePollTestBuilder;

    /**
     * Generiere fuer jeden Test eine neue Builder-Instanz.
     */
    @BeforeEach
    public void setDatePollTestBuilder() {
        this.datePollTestBuilder = new DatePollBuilder();
    }

}
