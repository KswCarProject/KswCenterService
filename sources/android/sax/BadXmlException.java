package android.sax;

import com.ibm.icu.text.PluralRules;
import org.xml.sax.Locator;
import org.xml.sax.SAXParseException;

/* loaded from: classes3.dex */
class BadXmlException extends SAXParseException {
    public BadXmlException(String message, Locator locator) {
        super(message, locator);
    }

    @Override // org.xml.sax.SAXException, java.lang.Throwable
    public String getMessage() {
        return "Line " + getLineNumber() + PluralRules.KEYWORD_RULE_SEPARATOR + super.getMessage();
    }
}
