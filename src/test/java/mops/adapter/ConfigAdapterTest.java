package mops.adapter;

import mops.domain.models.datepoll.DatePollConfig;
import mops.infrastructure.adapters.webflow.datepoll.ConfigAdapter;
import mops.infrastructure.adapters.webflow.datepoll.webflowdtos.ConfigDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {ConfigAdapter.class})
@SuppressWarnings({"PMD.AtLeastOneConstructor", "PMD.LawOfDemeter"})
public class ConfigAdapterTest {

    private transient ConfigDto dto;

    @Autowired
    private transient ConfigAdapter adapter;

    @BeforeEach
    public final void beforeEach() {
        dto = adapter.initializeDto();
    }

    @Test
    public void testInitializeDto() {
        assertThat(dto).hasNoNullFieldsOrProperties();
    }

    @SuppressWarnings("PMD")
    public final void setAllFalse() {
        dto.setOpen(false);
        dto.setPriorityChoice(false);
        dto.setSingleChoice(false);
        dto.setOpenForOwnEntries(false);
        dto.setAnonymous(false);
        dto.setVoteIsEditable(false);
    }

    @Test
    public void testConvertFalse() {
        setAllFalse();
        final DatePollConfig targetConfig = new DatePollConfig(false, false, false, false, false, false);

        final DatePollConfig config = adapter.build(dto);

        assertThat(config).isEqualTo(targetConfig);
    }

    @Test
    public void testConvertEditable() {
        setAllFalse();
        dto.setVoteIsEditable(true);
        final DatePollConfig targetConfig = new DatePollConfig(true, false, false, false, false, false);

        final DatePollConfig config = adapter.build(dto);

        assertThat(config).isEqualTo(targetConfig);
    }

    @Test
    public void testConvertOwnEntries() {
        setAllFalse();
        dto.setOpenForOwnEntries(true);
        final DatePollConfig targetConfig = new DatePollConfig(false, true, false, false, false, false);

        final DatePollConfig config = adapter.build(dto);

        assertThat(config).isEqualTo(targetConfig);
    }

    @Test
    public void testConvertSingleChoice() {
        setAllFalse();
        dto.setSingleChoice(true);
        final DatePollConfig targetConfig = new DatePollConfig(false, false, true, false, false, false);

        final DatePollConfig config = adapter.build(dto);

        assertThat(config).isEqualTo(targetConfig);
    }

    @Test
    public void testConvertPriorityChoice() {
        setAllFalse();
        dto.setPriorityChoice(true);
        final DatePollConfig targetConfig = new DatePollConfig(false, false, false, true, false, false);

        final DatePollConfig config = adapter.build(dto);

        assertThat(config).isEqualTo(targetConfig);
    }

    @Test
    public void testConvertAnonymous() {
        setAllFalse();
        dto.setAnonymous(true);
        final DatePollConfig targetConfig = new DatePollConfig(false, false, false, false, true, false);

        final DatePollConfig config = adapter.build(dto);

        assertThat(config).isEqualTo(targetConfig);
    }

    @Test
    public void testConvertOpen() {
        setAllFalse();
        dto.setOpen(true);
        final DatePollConfig targetConfig = new DatePollConfig(false, false, false, false, false, true);

        final DatePollConfig config = adapter.build(dto);

        assertThat(config).isEqualTo(targetConfig);
    }
}
