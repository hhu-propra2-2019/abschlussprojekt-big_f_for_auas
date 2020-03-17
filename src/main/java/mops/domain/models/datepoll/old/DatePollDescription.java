package mops.domain.models.datepoll.old;


import lombok.Value;
import mops.domain.models.FieldErrorNames;
import mops.domain.models.ValidateAble;
import mops.domain.models.Validation;

@Value
public class DatePollDescription implements ValidateAble {

    private String description;


    /**
     * ....
     *
     * @return ...
     */
    @SuppressWarnings({"PMD.DataflowAnomalyAnalysis"})
    @Override
    public Validation validate() {
        Validation validation = Validation.noErrors();
        if (description == null || description.equals(" ") || description.length() == 0) {
            validation = new Validation(FieldErrorNames.DATE_POLL_LOCATION);
        }
        return validation;
    }
}
