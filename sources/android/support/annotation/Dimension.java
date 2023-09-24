package android.support.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD, ElementType.LOCAL_VARIABLE, ElementType.ANNOTATION_TYPE})
@Documented
@Retention(RetentionPolicy.CLASS)
/* loaded from: classes3.dex */
public @interface Dimension {

    /* renamed from: DP */
    public static final int f256DP = 0;

    /* renamed from: PX */
    public static final int f257PX = 1;

    /* renamed from: SP */
    public static final int f258SP = 2;

    int unit() default 1;
}
