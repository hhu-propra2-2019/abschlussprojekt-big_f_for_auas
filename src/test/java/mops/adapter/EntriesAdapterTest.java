package mops.adapter;

import mops.infrastructure.adapters.webflow.datepoll.EntriesAdapter;
import mops.infrastructure.adapters.webflow.datepoll.EntryAdapter;
import mops.infrastructure.adapters.webflow.datepoll.webflowdtos.EntriesDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.message.MessageContext;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {EntriesAdapter.class, EntryAdapter.class})
public class EntriesAdapterTest {

    private EntriesDto dto;

    @Autowired
    private transient EntriesAdapter adapter;
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
        dto.setProposedEntry(null);
        dto.setEntries(null);

        boolean valid = adapter.validateDto(dto, messageContext);

        assertThat(valid).isFalse();
    }
}
