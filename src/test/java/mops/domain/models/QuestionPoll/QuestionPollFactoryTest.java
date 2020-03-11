package mops.domain.models.QuestionPoll;

import mops.controller.DTO.*;
import mops.domain.models.User.UserId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class QuestionPollFactoryTest {

    UserId owner;
    QuestionPollAccessibilityDto accessorDto;
    QuestionPollConfigDto configDto;
    QuestionPollHeaderDto headerDto;
    List<QuestionPollEntryDto> entriesDto;
    QuestionPollLifecycleDto lifecycleDto;
    QuestionPollFactory factory;

    UserId user1 ;
    UserId user2 ;
    UserId user3 ;
    UserId user4 ;

    @BeforeEach
    void setup() {

        owner = new UserId();
        factory = new QuestionPollFactory(owner);

        // arrange Accessibility
        user1 = new UserId();
        user2 = new UserId();
        user3 = new UserId();
        user4 = new UserId();
        Set<UserId> user = new HashSet<UserId>();
        user.add(user1);
        user.add(user2);
        user.add(user3);
        user.add(user4);
        accessorDto = new QuestionPollAccessibilityDto(true, user);

        // arrange config
        configDto = new QuestionPollConfigDto(true, false);

        // arrange header
        headerDto = new QuestionPollHeaderDto("About Nathan", "Wie groß ist Nathans Arm ?", null);

        // arrange entries
        QuestionPollEntryDto entry1 = new QuestionPollEntryDto("20cm", 1);
        QuestionPollEntryDto entry2 = new QuestionPollEntryDto("30cm", 1);
        QuestionPollEntryDto entry3 = new QuestionPollEntryDto("40cm", 1);

        entriesDto = new LinkedList<QuestionPollEntryDto>();
        entriesDto.add(entry1);
        entriesDto.add(entry2);
        entriesDto.add(entry3);

        // arrange Lifecycle
        lifecycleDto = new QuestionPollLifecycleDto(new Date(), new Date());
    }

    @Test
    void testFactoryPipelineAllButAccessorSet() {

        factory.config(configDto);
        factory.entries(entriesDto);
        factory.lifecycle(lifecycleDto);
        factory.header(headerDto);
        Exception exception = assertThrows(IllegalStateException.class , () -> factory.build());
        assertEquals("NOT ALL FIELDS SET CORRECTLY", exception.getMessage());
    }

    @Test
    void testFactoryPipelineAllButConfigSet() {

        factory.accessibility(accessorDto);
        factory.entries(entriesDto);
        factory.lifecycle(lifecycleDto);
        factory.header(headerDto);
        Exception exception = assertThrows(IllegalStateException.class , () -> factory.build());
        assertEquals("NOT ALL FIELDS SET CORRECTLY", exception.getMessage());
    }

    @Test
    void testFactoryPipelineAllButHeaderSet() {

        factory.accessibility(accessorDto);
        factory.entries(entriesDto);
        factory.lifecycle(lifecycleDto);
        factory.config(configDto);
        Exception exception = assertThrows(IllegalStateException.class , () -> factory.build());
        assertEquals("NOT ALL FIELDS SET CORRECTLY", exception.getMessage());
    }


    @Test
    void testFactoryPipelineAllButEntriesSet() {

        factory.accessibility(accessorDto);
        factory.lifecycle(lifecycleDto);
        factory.config(configDto);
        factory.header(headerDto);
        Exception exception = assertThrows(IllegalStateException.class , () -> factory.build());
        assertEquals("NOT ALL FIELDS SET CORRECTLY", exception.getMessage());
    }

    @Test
    void testFactoryPipelineAllSet() {

        factory.accessibility(accessorDto);
        factory.config(configDto);
        factory.entries(entriesDto);
        factory.lifecycle(lifecycleDto);
        factory.header(headerDto);
        try {
            factory.build();
        } catch(Exception e) {
            System.out.println(factory.peekCookieJar());
            fail();
        }
    }

    @Test
    void testRestrictionSetCorrect() {

        factory.accessibility(accessorDto);Exception exception = assertThrows(IllegalStateException.class , () -> factory.build());
        factory.config(configDto);
        factory.entries(entriesDto);
        factory.lifecycle(lifecycleDto);
        factory.header(headerDto);
        QuestionPoll questionPoll = factory.build();

        QuestionPollAccessibility accessor = questionPoll.getAccessor();
        boolean restriction = accessor.getRestrictedAccess();
        boolean restrictionDto = accessorDto.isRestrictedAccess();
        assertEquals(restriction, restrictionDto);
    }

}