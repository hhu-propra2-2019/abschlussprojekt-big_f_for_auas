package mops.adapter;

import mops.infrastructure.adapters.webflow.datepoll.MetaInfAdapter;
import mops.infrastructure.adapters.webflow.datepoll.webflowdtos.MetaInfDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.message.MessageContext;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {MetaInfAdapter.class})
public class MetaInfAdapterTest {

    private MetaInfDto dto;

    @Autowired
    private transient MetaInfAdapter adapter;
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
    public void testValidateFirstStep() {
        dto.setTitle(null);
        dto.setDescription(null);
        dto.setLocation(null);
        dto.setStartDate(null);
        dto.setStartTime(null);
        dto.setEndDate(null);
        dto.setEndTime(null);

        boolean valid = adapter.validateFirstStep(dto, messageContext);

        assertThat(valid).isFalse();
    }

    @Test
    public void testValidateDto() {
        dto.setTitle(null);
        dto.setDescription(null);
        dto.setLocation(null);
        dto.setStartDate(null);
        dto.setStartTime(null);
        dto.setEndDate(null);
        dto.setEndTime(null);

        boolean valid = adapter.validateDto(dto, messageContext);

        assertThat(valid).isFalse();
    }
}
