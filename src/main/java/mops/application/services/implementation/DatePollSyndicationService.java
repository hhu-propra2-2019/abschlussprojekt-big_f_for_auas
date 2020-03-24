package mops.application.services.implementation;

import mops.domain.models.PollLink;
import mops.domain.models.datepoll.DatePoll;
import mops.domain.models.datepoll.DatePollBuilder;
import mops.domain.repositories.DatePollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class DatePollSyndicationService {

    public static final String LINK_ALREADY_TAKEN = "Link already taken";
    private final transient DatePollRepository datePollRepository;
    //private final transient GroupRepository groupRepository;

    @Autowired
    public DatePollSyndicationService(DatePollRepository datePollRepository) {
        this.datePollRepository = datePollRepository;
        //this.groupRepository = groupRepository;
    }

    /**
     * Beendet den Erstellungsprozess und speichert die erstellte Terminfindung.
     *
     * @param builder Um an den datePollConfigDto
     * @return DatePoll Objekt.
     */
    @Transactional
    @SuppressWarnings({"PMD.LawOfDemeter"})
    public DatePoll publishDatePoll(final DatePollBuilder builder) {
        final DatePoll created = builder.build();
        datePollRepository.load(created.getPollLink()).ifPresent(datePoll -> {
            throw new IllegalArgumentException(LINK_ALREADY_TAKEN);
        });
        datePollRepository.save(created);
        return created;
    }

    /**
     * Wenn der Link noch nicht vergeben wurde wird dieser als Veröffentlichungslink gesetzt.
     *
     * @param builder Builder für DatePoll
     * @param link    link zur späteren Veröffentlichung
     */
    @SuppressWarnings({"PMD.LawOfDemeter"})
    /* Optional check stellt keine wirkliche Verletzungs des Prinzip von LawOfDemeter da*/
    public void requestLink(final DatePollBuilder builder, final PollLink link) {
        datePollRepository.load(link).ifPresent(datePoll -> {
            throw new IllegalArgumentException(LINK_ALREADY_TAKEN);
        });
        builder.datePollLink(link);
    }

    /*/**
     * fügt zum übergebenen Builder die Information hinzuz, sodass sie für die User einer
     * bestimmten Gruppe bestimmt ist.
     *
     * @param builder Builder für DatePoll
     * @param groupID Id der betreffenden Gruppe
     */
    /*public void forGroup(final DatePollBuilder builder, final GroupId groupID) {
        final Set<UserId> userSet = groupRepository.getUsersFromGroupByGroupId(groupID);
        forCertainUsers(builder, userSet);
    }*/

    /*/**
     * fügt zum übergebenen Builder die Information hinzuz, sodass er nun alle übergebenen User als
     * Teilnehmer enthält.
     *
     * @param builder      Builder für DatePoll
     * @param participants Liste der betreffenden User
     */
    /*public void forCertainUsers(final DatePollBuilder builder, final Set<UserId> participants) {
        builder.participants(participants);
    }*/

    /*/**
     * fügt zum übergebenen Builder die Information hinzuz, sodass er nun den übergebenen User als
     * Teilnehmer enthält.
     *
     * @param builder     Builder für DatePoll
     * @param participant Set der betreffenden User
     */
    /*public void forCertainUser(final DatePollBuilder builder, final UserId participant) {
        forCertainUsers(builder, Set.of(participant));
    }*/

}
