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
    QuestionPollBallotDto ballotDto;
    QuestionPollConfigDto configDto;
    QuestionPollHeaderDto headerDto;
    List<QuestionPollEntryDto> entriesDto;
    QuestionPollLifecycleDto lifecycleDto;
    QuestionPollFactory factory;

    @BeforeEach
    void setup() {

        owner = new UserId();
        factory = new QuestionPollFactory(owner);

        // arrange Accessibility
        UserId user1 = new UserId();
        UserId user2 = new UserId();
        UserId user3 = new UserId();
        UserId user4 = new UserId();
        Set<UserId> user = new HashSet<UserId>();
        user.add(user1);
        user.add(user2);
        user.add(user3);
        user.add(user4);
        accessorDto = new QuestionPollAccessibilityDto(true, user);


        // arrange ballot
        ballotDto = new QuestionPollBallotDto(Collections.emptyMap());


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
    void testFactoryPipelineBallotNotSet() {

        factory.accessibility(accessorDto);
        factory.config(configDto);
        factory.entries(entriesDto);
        //factory.ballot(ballotDto);
        factory.lifecycle(lifecycleDto);
        factory.header(headerDto);
        Exception exception = assertThrows(IllegalStateException.class , () -> factory.build());
        assertEquals("NOT ALL FIELDS SET CORRECTLY", exception.getMessage());
    }

    @Test
    void testFactoryPipelineAccessorNotSet() {


        //factory.accessibility(accessorDto);
        factory.config(configDto);
        factory.entries(entriesDto);
        factory.ballot(ballotDto);
        factory.lifecycle(lifecycleDto);
        factory.header(headerDto);
        Exception exception = assertThrows(IllegalStateException.class , () -> factory.build());
        assertEquals("NOT ALL FIELDS SET CORRECTLY", exception.getMessage());
    }

    @Test
    void testRestrictionSetCorrect() {

        factory.accessibility(accessorDto);
        factory.config(configDto);
        factory.entries(entriesDto);
        factory.ballot(ballotDto);
        factory.lifecycle(lifecycleDto);
        factory.header(headerDto);
        QuestionPoll questionPoll = factory.build();

        QuestionPollAccessibility accessor = questionPoll.getAccessor();
        boolean restriction = accessor.isRestrictedAccess();
        boolean restrictionDto = accessorDto.isRestrictedAccess();
        assertEquals(restriction, restrictionDto);
    }

    @Test
    void testParticipantsSetCorrect() {

        factory.accessibility(accessorDto);
        factory.config(configDto);
        factory.entries(entriesDto);
        factory.ballot(ballotDto);
        factory.lifecycle(lifecycleDto);
        factory.header(headerDto);
        QuestionPoll questionPoll = factory.build();

        QuestionPollAccessibility accessor = questionPoll.getAccessor();
        Set<UserId> participants = accessor.getParticipants();
        Set<UserId> participantsDto = accessorDto.getParticipants();
        assertEquals(participants, participantsDto);
    }

    @Disabled
    void testAddUserMethod() {

        factory.accessibility(accessorDto);
        factory.config(configDto);
        factory.entries(entriesDto);
        factory.ballot(ballotDto);
        factory.lifecycle(lifecycleDto);
        factory.header(headerDto);

        UserId user5 = new UserId();
        UserId user6 = new UserId();
        UserId user7 = new UserId();


        factory.accessibilityAddUser(user5, user6, user7);

        //Set<UserId> participantsDto = accessorDto.getParticipants();
        //participantsDto.add(user5);
        //participantsDto.add(user6);
        //participantsDto.add(user7);

        System.out.println("DEBUG : "  + accessorDto.getParticipants().size());

        QuestionPoll questionPoll = factory.build();
        QuestionPollAccessibility accessor = questionPoll.getAccessor();
        Set<UserId> participants = accessor.getParticipants();
        System.out.println(participants.size());
        //assertEquals(participants, participantsDto);
    }
}