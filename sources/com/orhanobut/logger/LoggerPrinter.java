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

/* loaded from: classes5.dex */
class LoggerPrinter implements Printer {
    private static final int JSON_INDENT = 2;
    private final ThreadLocal<String> localTag = new ThreadLocal<>();
    private final List<LogAdapter> logAdapters = new ArrayList();

    LoggerPrinter() {
    }

    @Override // com.orhanobut.logger.Printer
    /* renamed from: t */
    public Printer mo6t(String tag) {
        if (tag != null) {
            this.localTag.set(tag);
        }
        return this;
    }

    @Override // com.orhanobut.logger.Printer
    /* renamed from: d */
    public void mo10d(@NonNull String message, @Nullable Object... args) {
        log(3, (Throwable) null, message, args);
    }

    @Override // com.orhanobut.logger.Printer
    /* renamed from: d */
    public void mo11d(@Nullable Object object) {
        log(3, (Throwable) null, Utils.toString(object), new Object[0]);
    }

    @Override // com.orhanobut.logger.Printer
    /* renamed from: e */
    public void mo9e(@NonNull String message, @Nullable Object... args) {
        mo8e(null, message, args);
    }

    @Override // com.orhanobut.logger.Printer
    /* renamed from: e */
    public void mo8e(@Nullable Throwable throwable, @NonNull String message, @Nullable Object... args) {
        log(6, throwable, message, args);
    }

    @Override // com.orhanobut.logger.Printer
    /* renamed from: w */
    public void mo4w(@NonNull String message, @Nullable Object... args) {
        log(5, (Throwable) null, message, args);
    }

    @Override // com.orhanobut.logger.Printer
    /* renamed from: i */
    public void mo7i(@NonNull String message, @Nullable Object... args) {
        log(4, (Throwable) null, message, args);
    }

    @Override // com.orhanobut.logger.Printer
    /* renamed from: v */
    public void mo5v(@NonNull String message, @Nullable Object... args) {
        log(2, (Throwable) null, message, args);
    }

    @Override // com.orhanobut.logger.Printer
    public void wtf(@NonNull String message, @Nullable Object... args) {
        log(7, (Throwable) null, message, args);
    }

    @Override // com.orhanobut.logger.Printer
    public void json(@Nullable String json) {
        if (Utils.isEmpty(json)) {
            mo11d("Empty/Null json content");
            return;
        }
        try {
            String json2 = json.trim();
            if (json2.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(json2);
                String message = jsonObject.toString(2);
                mo11d(message);
            } else if (json2.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(json2);
                String message2 = jsonArray.toString(2);
                mo11d(message2);
            } else {
                mo9e("Invalid Json", new Object[0]);
            }
        } catch (JSONException e) {
            mo9e("Invalid Json", new Object[0]);
        }
    }

    @Override // com.orhanobut.logger.Printer
    public void xml(@Nullable String xml) {
        if (Utils.isEmpty(xml)) {
            mo11d("Empty/Null xml content");
            return;
        }
        try {
            Source xmlInput = new StreamSource(new StringReader(xml));
            StreamResult xmlOutput = new StreamResult(new StringWriter());
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty("indent", "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.transform(xmlInput, xmlOutput);
            mo11d(xmlOutput.getWriter().toString().replaceFirst(">", ">\n"));
        } catch (TransformerException e) {
            mo9e("Invalid xml", new Object[0]);
        }
    }

    @Override // com.orhanobut.logger.Printer
    public synchronized void log(int priority, @Nullable String tag, @Nullable String message, @Nullable Throwable throwable) {
        if (throwable != null && message != null) {
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

    @Override // com.orhanobut.logger.Printer
    public void clearLogAdapters() {
        this.logAdapters.clear();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.orhanobut.logger.Printer
    public void addAdapter(@NonNull LogAdapter adapter) {
        this.logAdapters.add(Utils.checkNotNull(adapter));
    }

    private synchronized void log(int priority, @Nullable Throwable throwable, @NonNull String msg, @Nullable Object... args) {
        Utils.checkNotNull(msg);
        String tag = getTag();
        String message = createMessage(msg, args);
        log(priority, tag, message, throwable);
    }

    @Nullable
    private String getTag() {
        String tag = this.localTag.get();
        if (tag != null) {
            this.localTag.remove();
            return tag;
        }
        return null;
    }

    @NonNull
    private String createMessage(@NonNull String message, @Nullable Object... args) {
        return (args == null || args.length == 0) ? message : String.format(message, args);
    }
}
