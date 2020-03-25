package mops.controllers.dtos;

import lombok.Data;
import mops.domain.models.datepoll.DatePoll;
import mops.domain.models.user.UserId;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class DashboardItemDto implements Comparable<DashboardItemDto> {

    //Identifier ohne Kontextpfad (also z.B. „j5kl43lk5“)
    private String datePollIdentifier;
    private String title;
    private LocalDateTime endDate;
    private String status;
    private String date;
    private String time;
    private LocalDateTime lastModified;

    public DashboardItemDto(DatePoll datePoll, UserId userId) {
        datePollIdentifier = datePoll.getPollLink().getPollIdentifier();
        title = datePoll.getMetaInf().getTitle();
        endDate = datePoll.getMetaInf().getTimespan().getEndDate();
        status = datePoll.getUserStatus(userId).getIconName();
        date = datePoll.getMetaInf().getTimespan().getEndDate()
                .format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        time = datePoll.getMetaInf().getTimespan().getEndDate()
                .format(DateTimeFormatter.ofPattern("HH:mm"));
        lastModified = datePoll.getRecordAndStatus().getLastModified();
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
