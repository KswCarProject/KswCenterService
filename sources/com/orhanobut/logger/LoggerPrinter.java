package com.orhanobut.logger;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class LoggerPrinter implements Printer {
    private static final int JSON_INDENT = 2;
    private final ThreadLocal<String> localTag = new ThreadLocal<>();
    private final List<LogAdapter> logAdapters = new ArrayList();

    LoggerPrinter() {
    }

    public Printer t(String tag) {
        if (tag != null) {
            this.localTag.set(tag);
        }
        return this;
    }

    public void d(@NonNull String message, @Nullable Object... args) {
        log(3, (Throwable) null, message, args);
    }

    public void d(@Nullable Object object) {
        log(3, (Throwable) null, Utils.toString(object), new Object[0]);
    }

    public void e(@NonNull String message, @Nullable Object... args) {
        e((Throwable) null, message, args);
    }

    public void e(@Nullable Throwable throwable, @NonNull String message, @Nullable Object... args) {
        log(6, throwable, message, args);
    }

    public void w(@NonNull String message, @Nullable Object... args) {
        log(5, (Throwable) null, message, args);
    }

    public void i(@NonNull String message, @Nullable Object... args) {
        log(4, (Throwable) null, message, args);
    }

    public void v(@NonNull String message, @Nullable Object... args) {
        log(2, (Throwable) null, message, args);
    }

    public void wtf(@NonNull String message, @Nullable Object... args) {
        log(7, (Throwable) null, message, args);
    }

    public void json(@Nullable String json) {
        if (Utils.isEmpty(json)) {
            d("Empty/Null json content");
            return;
        }
        try {
            String json2 = json.trim();
            if (json2.startsWith("{")) {
                d(new JSONObject(json2).toString(2));
            } else if (json2.startsWith("[")) {
                d(new JSONArray(json2).toString(2));
            } else {
                e("Invalid Json", new Object[0]);
            }
        } catch (JSONException e) {
            e("Invalid Json", new Object[0]);
        }
    }

    public void xml(@Nullable String xml) {
        if (Utils.isEmpty(xml)) {
            d("Empty/Null xml content");
            return;
        }
        try {
            Source xmlInput = new StreamSource(new StringReader(xml));
            StreamResult xmlOutput = new StreamResult(new StringWriter());
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty("indent", "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.transform(xmlInput, xmlOutput);
            d(xmlOutput.getWriter().toString().replaceFirst(">", ">\n"));
        } catch (TransformerException e) {
            e("Invalid xml", new Object[0]);
        }
    }

    public synchronized void log(int priority, @Nullable String tag, @Nullable String message, @Nullable Throwable throwable) {
        if (!(throwable == null || message == null)) {
            try {
                message = message + " : " + Utils.getStackTraceString(throwable);
            } catch (Throwable th) {
                throw th;
            }
        }
        if (throwable != null && message == null) {
            message = Utils.getStackTraceString(throwable);
        }
        if (Utils.isEmpty(message)) {
            message = "Empty/NULL log message";
        }
        for (LogAdapter adapter : this.logAdapters) {
            if (adapter.isLoggable(priority, tag)) {
                adapter.log(priority, tag, message);
            }
        }
    }

    public void clearLogAdapters() {
        this.logAdapters.clear();
    }

    public void addAdapter(@NonNull LogAdapter adapter) {
        this.logAdapters.add(Utils.checkNotNull(adapter));
    }

    private synchronized void log(int priority, @Nullable Throwable throwable, @NonNull String msg, @Nullable Object... args) {
        Utils.checkNotNull(msg);
        log(priority, getTag(), createMessage(msg, args), throwable);
    }

    @Nullable
    private String getTag() {
        String tag = this.localTag.get();
        if (tag == null) {
            return null;
        }
        this.localTag.remove();
        return tag;
    }

    @NonNull
    private String createMessage(@NonNull String message, @Nullable Object... args) {
        return (args == null || args.length == 0) ? message : String.format(message, args);
    }
}
