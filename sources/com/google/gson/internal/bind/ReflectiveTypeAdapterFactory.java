package com.google.gson.internal.bind;

import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.C$Gson$Types;
import com.google.gson.internal.ConstructorConstructor;
import com.google.gson.internal.Excluder;
import com.google.gson.internal.ObjectConstructor;
import com.google.gson.internal.Primitives;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public final class ReflectiveTypeAdapterFactory implements TypeAdapterFactory {
    private final ConstructorConstructor constructorConstructor;
    private final Excluder excluder;
    private final FieldNamingStrategy fieldNamingPolicy;

    public ReflectiveTypeAdapterFactory(ConstructorConstructor constructorConstructor, FieldNamingStrategy fieldNamingPolicy, Excluder excluder) {
        this.constructorConstructor = constructorConstructor;
        this.fieldNamingPolicy = fieldNamingPolicy;
        this.excluder = excluder;
    }

    public boolean excludeField(Field f, boolean serialize) {
        return (this.excluder.excludeClass(f.getType(), serialize) || this.excluder.excludeField(f, serialize)) ? false : true;
    }

    private String getFieldName(Field f) {
        SerializedName serializedName = (SerializedName) f.getAnnotation(SerializedName.class);
        return serializedName == null ? this.fieldNamingPolicy.translateName(f) : serializedName.value();
    }

    @Override // com.google.gson.TypeAdapterFactory
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        Class<? super T> raw = type.getRawType();
        if (Object.class.isAssignableFrom(raw)) {
            ObjectConstructor<T> constructor = this.constructorConstructor.get(type);
            return new Adapter(constructor, getBoundFields(gson, type, raw));
        }
        return null;
    }

    private BoundField createBoundField(final Gson context, final Field field, String name, final TypeToken<?> fieldType, boolean serialize, boolean deserialize) {
        final boolean isPrimitive = Primitives.isPrimitive(fieldType.getRawType());
        return new BoundField(name, serialize, deserialize) { // from class: com.google.gson.internal.bind.ReflectiveTypeAdapterFactory.1
            final TypeAdapter<?> typeAdapter;

            {
                this.typeAdapter = context.getAdapter(fieldType);
            }

            @Override // com.google.gson.internal.bind.ReflectiveTypeAdapterFactory.BoundField
            void write(JsonWriter writer, Object value) throws IOException, IllegalAccessException {
                Object fieldValue = field.get(value);
                TypeAdapter t = new TypeAdapterRuntimeTypeWrapper(context, this.typeAdapter, fieldType.getType());
                t.write(writer, fieldValue);
            }

            @Override // com.google.gson.internal.bind.ReflectiveTypeAdapterFactory.BoundField
            void read(JsonReader reader, Object value) throws IOException, IllegalAccessException {
                Object fieldValue = this.typeAdapter.read(reader);
                if (fieldValue != null || !isPrimitive) {
                    field.set(value, fieldValue);
                }
            }
        };
    }

    private Map<String, BoundField> getBoundFields(Gson context, TypeToken<?> type, Class<?> raw) {
        int i$;
        Map<String, BoundField> result = new LinkedHashMap<>();
        if (raw.isInterface()) {
            return result;
        }
        Type declaredType = type.getType();
        TypeToken<?> type2 = type;
        Class<?> raw2 = raw;
        while (true) {
            Type declaredType2 = declaredType;
            if (raw2 == Object.class) {
                return result;
            }
            Field[] fields = raw2.getDeclaredFields();
            int len$ = fields.length;
            int i$2 = 0;
            while (true) {
                int i$3 = i$2;
                if (i$3 < len$) {
                    Field field = fields[i$3];
                    boolean serialize = excludeField(field, true);
                    boolean deserialize = excludeField(field, false);
                    if (!serialize && !deserialize) {
                        i$ = i$3;
                    } else {
                        field.setAccessible(true);
                        Type fieldType = C$Gson$Types.resolve(type2.getType(), raw2, field.getGenericType());
                        i$ = i$3;
                        BoundField boundField = createBoundField(context, field, getFieldName(field), TypeToken.get(fieldType), serialize, deserialize);
                        BoundField previous = result.put(boundField.name, boundField);
                        if (previous != null) {
                            throw new IllegalArgumentException(declaredType2 + " declares multiple JSON fields named " + previous.name);
                        }
                    }
                    i$2 = i$ + 1;
                }
            }
            type2 = TypeToken.get(C$Gson$Types.resolve(type2.getType(), raw2, raw2.getGenericSuperclass()));
            raw2 = type2.getRawType();
            declaredType = declaredType2;
        }
    }

    /* loaded from: classes4.dex */
    static abstract class BoundField {
        final boolean deserialized;
        final String name;
        final boolean serialized;

        abstract void read(JsonReader jsonReader, Object obj) throws IOException, IllegalAccessException;

        abstract void write(JsonWriter jsonWriter, Object obj) throws IOException, IllegalAccessException;

        protected BoundField(String name, boolean serialized, boolean deserialized) {
            this.name = name;
            this.serialized = serialized;
            this.deserialized = deserialized;
        }
    }

    /* loaded from: classes4.dex */
    public static final class Adapter<T> extends TypeAdapter<T> {
        private final Map<String, BoundField> boundFields;
        private final ObjectConstructor<T> constructor;

        private Adapter(ObjectConstructor<T> constructor, Map<String, BoundField> boundFields) {
            this.constructor = constructor;
            this.boundFields = boundFields;
        }

        @Override // com.google.gson.TypeAdapter
        public T read(JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return null;
            }
            T instance = this.constructor.construct();
            try {
                in.beginObject();
                while (in.hasNext()) {
                    String name = in.nextName();
                    BoundField field = this.boundFields.get(name);
                    if (field != null && field.deserialized) {
                        field.read(in, instance);
                    }
                    in.skipValue();
                }
                in.endObject();
                return instance;
            } catch (IllegalAccessException e) {
                throw new AssertionError(e);
            } catch (IllegalStateException e2) {
                throw new JsonSyntaxException(e2);
            }
        }

        @Override // com.google.gson.TypeAdapter
        public void write(JsonWriter out, T value) throws IOException {
            if (value == null) {
                out.nullValue();
                return;
            }
            out.beginObject();
            try {
                for (BoundField boundField : this.boundFields.values()) {
                    if (boundField.serialized) {
                        out.name(boundField.name);
                        boundField.write(out, value);
                    }
                }
                out.endObject();
            } catch (IllegalAccessException e) {
                throw new AssertionError();
            }
        }
    }
}
