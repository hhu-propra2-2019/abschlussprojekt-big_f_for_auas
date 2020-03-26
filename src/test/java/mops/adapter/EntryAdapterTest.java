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
    public void testValidateDto() {
        dto.setDate(null);
        dto.setStartTime(null);
        dto.setEndTime(null);

        boolean valid = adapter.validateDto(dto, messageContext);

        assertThat(valid).isFalse();
    }

}
