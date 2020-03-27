package mops.utils;

import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Eine UUID hat 128 bit. Base64 encoded je 6 bit zu einem char aus dem base64 alphabet.
 * Da 128 mod 6 = 2 fügt der Base64 Encode 2 bit padding hinzu, welche im String als = dargestellt werden.
 *
 * Der codierte String hat eine Länge von 22 und ist somit 12 chars kürzer als uuid.toString().
 * Und es ist auch definitiv kürzer als die vorherige Encoder Implementation,
 * denn der hat einen String der Länge 48 erzeugt.
 * Dort wurde UUID.toString() mit .getBytes() encodiert.
 * Aber getBytes() benutzt kein hexadezimales Charset wie die UUID.toString Methode,
 * sondern das default Charset.
 * So kriegt man für jeden der 36 chars ein eigenes Byte (UTF-8 Charset) zurück
 * und hat am Ende 36 * 8 = 288 großes Byte Array.
 * Wenn man das durch Base64 jagt kriegt man einen (288 / 6) = 48 char langen String
 * und da hätte man gleich bei der toString() Methode von UUID bleiben können.
 * ~ thank you for coming to my ted talk.
 */
public final class UUIDEncoderUtil {

    private static final Encoder ENCODER = Base64.getUrlEncoder();
    private static final Decoder DECODER = Base64.getUrlDecoder();

    private static final int ENCODED_STRING_LENGTH = 22;

    /**
     * Vergleiche Table 2 RFC 4648. Padding taucht nicht auf, da die encode Methode diese entfernt.
     */
    private static final Set<Character> URL_SAFE_BASE_64_ALPHABET =
        "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_".chars()
            .mapToObj(c -> (char) c)
            .collect(Collectors.toUnmodifiableSet());

    @SuppressWarnings("PMD.LawOfDemeter")
    public static String encode(UUID uuid) {
        final ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES * 2);
        buffer.putLong(uuid.getMostSignificantBits());
        buffer.putLong(uuid.getLeastSignificantBits());
        return ENCODER.encodeToString(buffer.array()).substring(0, ENCODED_STRING_LENGTH); // entfernt padding
    }


    @SuppressWarnings("PMD.LawOfDemeter")
    public static UUID decode(String encoded) {
        if (UUIDEncoderUtil.isValidStringToDecode(encoded)) {
            final ByteBuffer rawDecoded = ByteBuffer.wrap(DECODER.decode(encoded));
            return new UUID(rawDecoded.getLong(), rawDecoded.getLong());
        } else {
            throw new IllegalArgumentException(encoded + " is not a valid base64 encoded uuid");
        }
    }

    @SuppressWarnings("PMD.LawOfDemeter")
    private static boolean isValidStringToDecode(String encoded) {
        return encoded.length() == ENCODED_STRING_LENGTH && encoded.chars()
            .mapToObj(c -> (char) c)
            .allMatch(URL_SAFE_BASE_64_ALPHABET::contains);
    }

    private UUIDEncoderUtil() { }
}
