package android.net;

import android.net.wifi.WifiScanner;
import com.ibm.icu.text.PluralRules;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;

/* loaded from: classes3.dex */
public final class UriCodec {
    private static final char INVALID_INPUT_CHARACTER = '\ufffd';

    private UriCodec() {
    }

    private static int hexCharToValue(char c) {
        if ('0' <= c && c <= '9') {
            return c - '0';
        }
        if ('a' <= c && c <= 'f') {
            return (c + '\n') - 97;
        }
        if ('A' <= c && c <= 'F') {
            return (c + '\n') - 65;
        }
        return -1;
    }

    private static URISyntaxException unexpectedCharacterException(String uri, String name, char unexpected, int index) {
        String nameString;
        if (name == null) {
            nameString = "";
        } else {
            nameString = " in [" + name + "]";
        }
        return new URISyntaxException(uri, "Unexpected character" + nameString + PluralRules.KEYWORD_RULE_SEPARATOR + unexpected, index);
    }

    private static char getNextCharacter(String uri, int index, int end, String name) throws URISyntaxException {
        String nameString;
        if (index >= end) {
            if (name == null) {
                nameString = "";
            } else {
                nameString = " in [" + name + "]";
            }
            throw new URISyntaxException(uri, "Unexpected end of string" + nameString, index);
        }
        return uri.charAt(index);
    }

    public static String decode(String s, boolean convertPlus, Charset charset, boolean throwOnFailure) {
        StringBuilder builder = new StringBuilder(s.length());
        appendDecoded(builder, s, convertPlus, charset, throwOnFailure);
        return builder.toString();
    }

    private static void appendDecoded(StringBuilder builder, String s, boolean convertPlus, Charset charset, boolean throwOnFailure) {
        CharsetDecoder decoder = charset.newDecoder().onMalformedInput(CodingErrorAction.REPLACE).replaceWith("\ufffd").onUnmappableCharacter(CodingErrorAction.REPORT);
        ByteBuffer byteBuffer = ByteBuffer.allocate(s.length());
        int i = 0;
        while (i < s.length()) {
            char c = s.charAt(i);
            i++;
            if (c != '%') {
                if (c == '+') {
                    flushDecodingByteAccumulator(builder, decoder, byteBuffer, throwOnFailure);
                    builder.append(convertPlus ? ' ' : '+');
                } else {
                    flushDecodingByteAccumulator(builder, decoder, byteBuffer, throwOnFailure);
                    builder.append(c);
                }
            } else {
                byte hexValue = 0;
                int i2 = i;
                int i3 = 0;
                while (true) {
                    if (i3 >= 2) {
                        break;
                    }
                    try {
                        char c2 = getNextCharacter(s, i2, s.length(), null);
                        i2++;
                        int newDigit = hexCharToValue(c2);
                        if (newDigit < 0) {
                            if (throwOnFailure) {
                                throw new IllegalArgumentException(unexpectedCharacterException(s, null, c2, i2 - 1));
                            }
                            flushDecodingByteAccumulator(builder, decoder, byteBuffer, throwOnFailure);
                            builder.append(INVALID_INPUT_CHARACTER);
                        } else {
                            hexValue = (byte) ((hexValue * WifiScanner.PnoSettings.PnoNetwork.FLAG_SAME_NETWORK) + newDigit);
                            i3++;
                        }
                    } catch (URISyntaxException e) {
                        if (throwOnFailure) {
                            throw new IllegalArgumentException(e);
                        }
                        flushDecodingByteAccumulator(builder, decoder, byteBuffer, throwOnFailure);
                        builder.append(INVALID_INPUT_CHARACTER);
                        return;
                    }
                }
                byteBuffer.put(hexValue);
                i = i2;
            }
        }
        flushDecodingByteAccumulator(builder, decoder, byteBuffer, throwOnFailure);
    }

    private static void flushDecodingByteAccumulator(StringBuilder builder, CharsetDecoder decoder, ByteBuffer byteBuffer, boolean throwOnFailure) {
        if (byteBuffer.position() == 0) {
            return;
        }
        byteBuffer.flip();
        try {
            try {
                builder.append((CharSequence) decoder.decode(byteBuffer));
            } catch (CharacterCodingException e) {
                if (throwOnFailure) {
                    throw new IllegalArgumentException(e);
                }
                builder.append(INVALID_INPUT_CHARACTER);
            }
        } finally {
            byteBuffer.flip();
            byteBuffer.limit(byteBuffer.capacity());
        }
    }
}
