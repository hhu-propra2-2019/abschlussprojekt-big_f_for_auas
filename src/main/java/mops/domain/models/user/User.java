package mops.domain.models.user;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import mops.domain.models.ValidateAble;
import mops.domain.models.Validation;
import org.springframework.lang.NonNull;

@AllArgsConstructor
@SuppressFBWarnings(value = "RCN_REDUNDANT_NULLCHECK_OF_NONNULL_VALUE", justification = "Code von Lombok")
@EqualsAndHashCode
public class User implements ValidateAble {

    @NonNull
    @Getter
    private final UserId id;

    /**
     * TODO: Validierung hinzufügen!
     * @return ...
     */
    @Override
    public Validation validate() {
        return Validation.noErrors();
    }
}
