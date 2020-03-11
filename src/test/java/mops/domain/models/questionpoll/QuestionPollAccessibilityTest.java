package mops.domain.models.questionpoll;

import mops.domain.models.user.UserId;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class QuestionPollAccessibilityTest {


    @Test
    void testReferenceCapsuled() {

        UserId user1 = new UserId();
        UserId user2 = new UserId();
        UserId user3 = new UserId();
        UserId user4 = new UserId();

        Set<UserId> ids = new HashSet<UserId>();
        ids.add(user1);
        ids.add(user2);
        ids.add(user3);
        ids.add(user4);


        QuestionPollAccessibility accessor = new QuestionPollAccessibility(false, ids);

        boolean allOk = ids.stream().allMatch(userId -> accessor.isUserParticipant(userId));
        assertEquals(allOk, true);
    }
}