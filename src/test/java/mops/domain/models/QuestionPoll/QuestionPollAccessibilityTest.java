package mops.domain.models.QuestionPoll;

import mops.domain.models.User.User;
import mops.domain.models.User.UserId;
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

        boolean allOk = checkIfAllUserAreParticipants(accessor, user1, user2, user3, user4);

        assertEquals(allOk, true);
    }

    public boolean checkIfAllUserAreParticipants(QuestionPollAccessibility accessor, UserId ... ids) {

        boolean erg = true;
        for(UserId id : ids) {
            if(accessor.isUserParticipant(id) == false)
                erg = false;
        }
        return erg;
    }
}