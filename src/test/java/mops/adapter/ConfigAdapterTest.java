package mops.adapter;

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
}
