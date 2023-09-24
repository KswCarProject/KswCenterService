package com.android.framework.protobuf;

import android.telecom.Logging.Session;
import com.android.framework.protobuf.GeneratedMessageLite;
import com.ibm.icu.text.PluralRules;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/* loaded from: classes4.dex */
final class MessageLiteToString {
    private static final String BUILDER_LIST_SUFFIX = "OrBuilderList";
    private static final String BYTES_SUFFIX = "Bytes";
    private static final String LIST_SUFFIX = "List";

    MessageLiteToString() {
    }

    static String toString(MessageLite messageLite, String commentString) {
        StringBuilder buffer = new StringBuilder();
        buffer.append("# ");
        buffer.append(commentString);
        reflectivePrintWithIndent(messageLite, buffer, 0);
        return buffer.toString();
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static void reflectivePrintWithIndent(MessageLite messageLite, StringBuilder buffer, int indent) {
        Method[] declaredMethods;
        Map<String, Method> nameToNoArgMethod = new HashMap<>();
        Map<String, Method> nameToMethod = new HashMap<>();
        Set<String> getters = new TreeSet<>();
        int i = 0;
        for (Method method : messageLite.getClass().getDeclaredMethods()) {
            nameToMethod.put(method.getName(), method);
            if (method.getParameterTypes().length == 0) {
                nameToNoArgMethod.put(method.getName(), method);
                if (method.getName().startsWith("get")) {
                    getters.add(method.getName());
                }
            }
        }
        for (String getter : getters) {
            String suffix = getter.replaceFirst("get", "");
            boolean hasValue = true;
            if (suffix.endsWith(LIST_SUFFIX) && !suffix.endsWith(BUILDER_LIST_SUFFIX)) {
                String camelCase = suffix.substring(i, 1).toLowerCase() + suffix.substring(1, suffix.length() - LIST_SUFFIX.length());
                Method listMethod = nameToNoArgMethod.get("get" + suffix);
                if (listMethod != null) {
                    printField(buffer, indent, camelCaseToSnakeCase(camelCase), GeneratedMessageLite.invokeOrDie(listMethod, messageLite, new Object[i]));
                }
            }
            Method setter = nameToMethod.get("set" + suffix);
            if (setter != null) {
                if (suffix.endsWith(BYTES_SUFFIX)) {
                    if (nameToNoArgMethod.containsKey("get" + suffix.substring(i, suffix.length() - BYTES_SUFFIX.length()))) {
                    }
                }
                String camelCase2 = suffix.substring(i, 1).toLowerCase() + suffix.substring(1);
                Method getMethod = nameToNoArgMethod.get("get" + suffix);
                Method hasMethod = nameToNoArgMethod.get("has" + suffix);
                if (getMethod != null) {
                    Object value = GeneratedMessageLite.invokeOrDie(getMethod, messageLite, new Object[i]);
                    if (hasMethod != null) {
                        hasValue = ((Boolean) GeneratedMessageLite.invokeOrDie(hasMethod, messageLite, new Object[i])).booleanValue();
                    } else if (isDefaultValue(value)) {
                        hasValue = i;
                    }
                    if (hasValue) {
                        printField(buffer, indent, camelCaseToSnakeCase(camelCase2), value);
                    }
                }
                i = 0;
            }
        }
        if (messageLite instanceof GeneratedMessageLite.ExtendableMessage) {
            Iterator<Map.Entry<GeneratedMessageLite.ExtensionDescriptor, Object>> iter = ((GeneratedMessageLite.ExtendableMessage) messageLite).extensions.iterator();
            while (iter.hasNext()) {
                Map.Entry<GeneratedMessageLite.ExtensionDescriptor, Object> entry = iter.next();
                printField(buffer, indent, "[" + entry.getKey().getNumber() + "]", entry.getValue());
            }
        }
        if (((GeneratedMessageLite) messageLite).unknownFields != null) {
            ((GeneratedMessageLite) messageLite).unknownFields.printWithIndent(buffer, indent);
        }
    }

    private static boolean isDefaultValue(Object o) {
        if (o instanceof Boolean) {
            return !((Boolean) o).booleanValue();
        }
        if (o instanceof Integer) {
            return ((Integer) o).intValue() == 0;
        } else if (o instanceof Float) {
            return ((Float) o).floatValue() == 0.0f;
        } else if (o instanceof Double) {
            return ((Double) o).doubleValue() == 0.0d;
        } else if (o instanceof String) {
            return o.equals("");
        } else {
            if (o instanceof ByteString) {
                return o.equals(ByteString.EMPTY);
            }
            return o instanceof MessageLite ? o == ((MessageLite) o).getDefaultInstanceForType() : (o instanceof Enum) && ((Enum) o).ordinal() == 0;
        }
    }

    static final void printField(StringBuilder buffer, int indent, String name, Object object) {
        if (object instanceof List) {
            List<?> list = (List) object;
            for (Object entry : list) {
                printField(buffer, indent, name, entry);
            }
            return;
        }
        buffer.append('\n');
        for (int i = 0; i < indent; i++) {
            buffer.append(' ');
        }
        buffer.append(name);
        if (object instanceof String) {
            buffer.append(": \"");
            buffer.append(TextFormatEscaper.escapeText((String) object));
            buffer.append('\"');
        } else if (object instanceof ByteString) {
            buffer.append(": \"");
            buffer.append(TextFormatEscaper.escapeBytes((ByteString) object));
            buffer.append('\"');
        } else if (object instanceof GeneratedMessageLite) {
            buffer.append(" {");
            reflectivePrintWithIndent((GeneratedMessageLite) object, buffer, indent + 2);
            buffer.append("\n");
            for (int i2 = 0; i2 < indent; i2++) {
                buffer.append(' ');
            }
            buffer.append("}");
        } else {
            buffer.append(PluralRules.KEYWORD_RULE_SEPARATOR);
            buffer.append(object.toString());
        }
    }

    private static final String camelCaseToSnakeCase(String camelCase) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < camelCase.length(); i++) {
            char ch = camelCase.charAt(i);
            if (Character.isUpperCase(ch)) {
                builder.append(Session.SESSION_SEPARATION_CHAR_CHILD);
            }
            builder.append(Character.toLowerCase(ch));
        }
        return builder.toString();
    }
}
