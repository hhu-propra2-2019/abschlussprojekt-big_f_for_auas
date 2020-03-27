package mops.adapter;

import mops.domain.models.Timespan;
import mops.domain.models.datepoll.DatePollMetaInf;
import mops.infrastructure.adapters.webflow.datepoll.MetaInfAdapter;
import mops.infrastructure.adapters.webflow.datepoll.webflowdtos.MetaInfDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.message.MessageContext;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {MetaInfAdapter.class})
@SuppressWarnings("checkstyle:MagicNumber")
public class MetaInfAdapterTest {

    private MetaInfDto dto;

    @Autowired
    private transient MetaInfAdapter adapter;
    private MessageContext messageContext;

    @BeforeEach
    public final void beforeEach() {
        dto = adapter.initializeDto();
        messageContext = AdapterUtil.getMessageContext();
        Mockito.mock(DatePollMetaInf.class);
    }

    private void nullDto() {
        dto.setTitle(null);
        dto.setDescription(null);
        dto.setLocation(null);
        dto.setStartDate(null);
        dto.setStartTime(null);
        dto.setEndDate(null);
        dto.setEndTime(null);
    }

    @Test
    public void testInitializeDto() {
        assertThat(dto).hasNoNullFieldsOrProperties();
    }

    @Test
    public void testValidateFirstStepNull() {
        nullDto();

        boolean valid = adapter.validateFirstStep(dto, messageContext);

        assertThat(valid).isFalse();
    }

    @Test
    public void testValidateDtoNull() {
        nullDto();

        boolean valid = adapter.validateDto(dto, messageContext);

        assertThat(valid).isFalse();
    }

    @Test
    public void testConvert() {
        dto.setTitle("1234");
        dto.setDescription("diesdas");
        dto.setLocation("hier");
        dto.setStartDate("2020-05-03");
        dto.setStartTime("10:00");
        dto.setEndDate("2020-06-01");
        dto.setEndTime("13:00");

        DatePollMetaInf targetMetaInf =
                new DatePollMetaInf("1234", "diesdas", "hier",
                        new Timespan(LocalDateTime.of(2020, 5, 3, 10, 0),
                                LocalDateTime.of(2020, 6, 1, 13, 0)));

        DatePollMetaInf metaInf = adapter.convert(dto);

        assertThat(metaInf).isEqualToComparingFieldByField(targetMetaInf);
    }

}
