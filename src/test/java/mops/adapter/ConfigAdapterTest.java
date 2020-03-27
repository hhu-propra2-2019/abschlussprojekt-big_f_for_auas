package mops.adapter;

import mops.domain.models.datepoll.DatePollConfig;
import mops.infrastructure.adapters.webflow.datepoll.ConfigAdapter;
import mops.infrastructure.adapters.webflow.datepoll.webflowdtos.ConfigDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.message.MessageContext;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {ConfigAdapter.class})
public class ConfigAdapterTest {

    private ConfigDto dto;

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
        DatePollConfig targetConfig = new DatePollConfig(false, false, false, false, false, false);

        DatePollConfig config = adapter.build(dto);

        assertThat(config).isEqualTo(targetConfig);
    }

    @Test
    public void testConvertEditable() {
        setAllFalse();
        dto.setVoteIsEditable(true);
        DatePollConfig targetConfig = new DatePollConfig(true, false, false, false, false, false);

        DatePollConfig config = adapter.build(dto);

        assertThat(config).isEqualTo(targetConfig);
    }

    @Test
    public void testConvertOwnEntries() {
        setAllFalse();
        dto.setOpenForOwnEntries(true);
        DatePollConfig targetConfig = new DatePollConfig(false, true, false, false, false, false);

        DatePollConfig config = adapter.build(dto);

        assertThat(config).isEqualTo(targetConfig);
    }

    @Test
    public void testConvertSingleChoice() {
        setAllFalse();
        dto.setSingleChoice(true);
        DatePollConfig targetConfig = new DatePollConfig(false, false, true, false, false, false);

        DatePollConfig config = adapter.build(dto);

        assertThat(config).isEqualTo(targetConfig);
    }

    @Test
    public void testConvertPriorityChoice() {
        setAllFalse();
        dto.setPriorityChoice(true);
        DatePollConfig targetConfig = new DatePollConfig(false, false, false, true, false, false);

        DatePollConfig config = adapter.build(dto);

        assertThat(config).isEqualTo(targetConfig);
    }

    @Test
    public void testConvertAnonymous() {
        setAllFalse();
        dto.setAnonymous(true);
        DatePollConfig targetConfig = new DatePollConfig(false, false, false, false, true, false);

        DatePollConfig config = adapter.build(dto);

        assertThat(config).isEqualTo(targetConfig);
    }

    @Test
    public void testConvertOpen() {
        setAllFalse();
        dto.setOpen(true);
        DatePollConfig targetConfig = new DatePollConfig(false, false, false, false, false, true);

        DatePollConfig config = adapter.build(dto);

        assertThat(config).isEqualTo(targetConfig);
    }
}
