package mops.adapter;

import mops.infrastructure.adapters.webflow.datepoll.EntryAdapter;
import mops.infrastructure.adapters.webflow.datepoll.webflowdtos.EntryDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.message.MessageContext;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {EntryAdapter.class})
public class EntryAdapterTest {

    private EntryDto dto;

    @Autowired
    private transient EntryAdapter adapter;
    private MessageContext messageContext;

    @BeforeEach
    public final void beforeEach() {
        dto = adapter.initializeDto();
        messageContext = AdapterUtil.getMessageContext();
    }

    @Test
    public void testInitializeDto() {
        assertThat(dto).hasNoNullFieldsOrProperties();
    }

    @Test
    public void testValidateNullDto() {
        dto.setDate(null);
        dto.setStartTime(null);
        dto.setEndTime(null);

        boolean valid = adapter.validateDto(dto, messageContext);

        assertThat(valid).isFalse();
    }

    @Test
    public void testValidateInvalidDateDto() {
        // Falsches Format: DD.MM.YYYY
        dto.setDate("05.03.2040");
        dto.setStartTime("12:00");
        dto.setEndTime("13:00");

        boolean valid = adapter.validateDto(dto, messageContext);

        assertThat(valid).isFalse();
    }

    @Test
    public void testValidateInvalidStartTimeDto() {
        dto.setDate("2020-05-01");
        // Falsches Format: HH-MM
        dto.setStartTime("13-00");
        dto.setEndTime("15:00");

        boolean valid = adapter.validateDto(dto, messageContext);

        assertThat(valid).isFalse();
    }

    @Test
    public void testValidateInvalidEndTimeDto() {
        dto.setDate("2020-05-01");
        dto.setStartTime("13:00");
        // Falsches Format: HH-MM
        dto.setEndTime("15-00");

        boolean valid = adapter.validateDto(dto, messageContext);

        assertThat(valid).isFalse();
    }
}
