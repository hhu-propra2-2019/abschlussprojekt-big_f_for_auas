package mops.infrastructure.controllers.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import mops.domain.models.questionpoll.QuestionPoll;
import mops.domain.models.user.UserId;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
public class DashboardItemDto implements Comparable<DashboardItemDto> {

    //Identifier ohne Kontextpfad (also z.B. „j5kl43lk5“)
    private String datePollIdentifier;
    private String title;
    private LocalDateTime endDate;
    private String status;
    private String date;
    private String time;
    private LocalDateTime lastModified;
    private String pollType;

    public static final String QUESTIONPOLL_TYPE = "qp";
    public static final String DATEPOLL_TYPE = "dp";

    public DashboardItemDto(QuestionPoll questionPoll, UserId userId) {
        datePollIdentifier = questionPoll.getPollLink().getPollIdentifier();
        title = questionPoll.getMetaInf().getTitle();
        endDate = questionPoll.getMetaInf().getTimespan().getEndDate();
        status = questionPoll.getUserStatus(userId).getIconName();
        date = questionPoll.getMetaInf().getTimespan().getEndDate()
            .format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        time = questionPoll.getMetaInf().getTimespan().getEndDate()
            .format(DateTimeFormatter.ofPattern("HH:mm"));
        lastModified = questionPoll.getRecordAndStatus().getLastModified();
        pollType = QUESTIONPOLL_TYPE;
    }

        /** vergleicht 2 DashboardItemDtos mit lastModified.
     *
     * @param other
     * @return
     */
    @Override
    public int compareTo(DashboardItemDto other) {
        return lastModified.compareTo(other.getLastModified());
    }
}
