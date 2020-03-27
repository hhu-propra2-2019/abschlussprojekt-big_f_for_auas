package mops.domain.models.util;

import java.util.UUID;
import mops.utils.UUIDEncoderUtil;
import org.junit.jupiter.api.Test;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SuppressWarnings("PMD.AtLeastOneConstructor")
public class UUIDEncoderUtilTest {

    private static final int ENCODED_STRING_LENGTH = 22;

    @Test
    @SuppressWarnings("PMD.LawOfDemeter")
    public void testUtilFunctionIdentity() {
        final UUID uuid = UUID.randomUUID();
        assertThat(UUIDEncoderUtil.decode(UUIDEncoderUtil.encode(uuid))).isEqualTo(uuid);
    }

    @Test
    @SuppressWarnings("PMD.LawOfDemeter")
    public void testInvalidLengthStringNotDecodable() {
        final String invalidLengthString = "YouCantDecodeThisHammerTime";
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> UUIDEncoderUtil.decode(invalidLengthString))
            .withMessageContaining(invalidLengthString);
    }

    @Test
    @SuppressWarnings({"PMD.LawOfDemeter", "PMD.JUnitTestContainsTooManyAsserts"})
    public void testInvalidBase64AlphabetStringIsNotDecodable() {
        final String invalidLengthString = "(oh-oh oh oh oh-oh-oh)";
        assertThat(invalidLengthString.length()).isEqualTo(ENCODED_STRING_LENGTH);
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> UUIDEncoderUtil.decode(invalidLengthString))
            .withMessageContaining(invalidLengthString);
    }
}
