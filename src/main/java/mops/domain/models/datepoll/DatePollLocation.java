package mops.domain.models.datepoll;

import lombok.Value;
import mops.controllers.dtos.InputFieldNames;
import mops.domain.models.ValidateAble;
import mops.domain.models.Validation;

@Value
public class DatePollLocation implements ValidateAble {
    //Shall we define standard rules for description / location strings?
    private transient String location;
    //Do we realy need longitude and latitude attributes for a location?
    //private int longitude;
    //private int latitude;

    /**
     * ...
     * @return
     */
    @SuppressWarnings({"PMD.DataflowAnomalyAnalysis"})
    @Override
    public Validation validate() {
        Validation validation = Validation.noErrors();
        final String errorMessage = "No location specified. DATE_POLL_LOCATION is not present.";
        if (location == null || location.equals(" ") || location.length() == 0) {
            validation = new Validation(InputFieldNames.DATE_POLL_LOCATION, errorMessage);
        }
        return validation;
    }
}