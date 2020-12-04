package android.app.admin;

import android.app.admin.SystemUpdatePolicy;
import android.util.Pair;
import java.time.LocalDate;
import java.time.MonthDay;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class FreezePeriod {
    static final int DAYS_IN_YEAR = 365;
    private static final int DUMMY_YEAR = 2001;
    private static final String TAG = "FreezePeriod";
    private final MonthDay mEnd;
    private final int mEndDay;
    private final MonthDay mStart;
    private final int mStartDay;

    public FreezePeriod(MonthDay start, MonthDay end) {
        this.mStart = start;
        this.mStartDay = this.mStart.atYear(2001).getDayOfYear();
        this.mEnd = end;
        this.mEndDay = this.mEnd.atYear(2001).getDayOfYear();
    }

    public MonthDay getStart() {
        return this.mStart;
    }

    public MonthDay getEnd() {
        return this.mEnd;
    }

    private FreezePeriod(int startDay, int endDay) {
        this.mStartDay = startDay;
        this.mStart = dayOfYearToMonthDay(startDay);
        this.mEndDay = endDay;
        this.mEnd = dayOfYearToMonthDay(endDay);
    }

    /* access modifiers changed from: package-private */
    public int getLength() {
        return (getEffectiveEndDay() - this.mStartDay) + 1;
    }

    /* access modifiers changed from: package-private */
    public boolean isWrapped() {
        return this.mEndDay < this.mStartDay;
    }

    /* access modifiers changed from: package-private */
    public int getEffectiveEndDay() {
        if (!isWrapped()) {
            return this.mEndDay;
        }
        return this.mEndDay + 365;
    }

    /* access modifiers changed from: package-private */
    public boolean contains(LocalDate localDate) {
        int daysOfYear = dayOfYearDisregardLeapYear(localDate);
        if (!isWrapped()) {
            if (this.mStartDay > daysOfYear || daysOfYear > this.mEndDay) {
                return false;
            }
            return true;
        } else if (this.mStartDay <= daysOfYear || daysOfYear <= this.mEndDay) {
            return true;
        } else {
            return false;
        }
    }

    /* access modifiers changed from: package-private */
    public boolean after(LocalDate localDate) {
        return this.mStartDay > dayOfYearDisregardLeapYear(localDate);
    }

    /* access modifiers changed from: package-private */
    public Pair<LocalDate, LocalDate> toCurrentOrFutureRealDates(LocalDate now) {
        int endYearAdjustment;
        int startYearAdjustment;
        int nowDays = dayOfYearDisregardLeapYear(now);
        if (contains(now)) {
            if (this.mStartDay <= nowDays) {
                startYearAdjustment = 0;
                endYearAdjustment = isWrapped();
            } else {
                startYearAdjustment = -1;
                endYearAdjustment = 0;
            }
        } else if (this.mStartDay > nowDays) {
            startYearAdjustment = 0;
            endYearAdjustment = isWrapped();
        } else {
            startYearAdjustment = 1;
            endYearAdjustment = 1;
        }
        return new Pair<>(LocalDate.ofYearDay(2001, this.mStartDay).withYear(now.getYear() + startYearAdjustment), LocalDate.ofYearDay(2001, this.mEndDay).withYear(now.getYear() + ((int) endYearAdjustment)));
    }

    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd");
        return LocalDate.ofYearDay(2001, this.mStartDay).format(formatter) + " - " + LocalDate.ofYearDay(2001, this.mEndDay).format(formatter);
    }

    private static MonthDay dayOfYearToMonthDay(int dayOfYear) {
        LocalDate date = LocalDate.ofYearDay(2001, dayOfYear);
        return MonthDay.of(date.getMonth(), date.getDayOfMonth());
    }

    private static int dayOfYearDisregardLeapYear(LocalDate date) {
        return date.withYear(2001).getDayOfYear();
    }

    public static int distanceWithoutLeapYear(LocalDate first, LocalDate second) {
        return (dayOfYearDisregardLeapYear(first) - dayOfYearDisregardLeapYear(second)) + ((first.getYear() - second.getYear()) * 365);
    }

    static List<FreezePeriod> canonicalizePeriods(List<FreezePeriod> intervals) {
        boolean[] taken = new boolean[365];
        for (FreezePeriod interval : intervals) {
            for (int i = interval.mStartDay; i <= interval.getEffectiveEndDay(); i++) {
                taken[(i - 1) % 365] = true;
            }
        }
        List<FreezePeriod> result = new ArrayList<>();
        int i2 = 0;
        while (i2 < 365) {
            if (!taken[i2]) {
                i2++;
            } else {
                int intervalStart = i2 + 1;
                while (i2 < 365 && taken[i2]) {
                    i2++;
                }
                result.add(new FreezePeriod(intervalStart, i2));
            }
        }
        int lastIndex = result.size() - 1;
        if (lastIndex > 0 && result.get(lastIndex).mEndDay == 365 && result.get(0).mStartDay == 1) {
            result.set(lastIndex, new FreezePeriod(result.get(lastIndex).mStartDay, result.get(0).mEndDay));
            result.remove(0);
        }
        return result;
    }

    static void validatePeriods(List<FreezePeriod> periods) {
        FreezePeriod previous;
        int separation;
        List<FreezePeriod> allPeriods = canonicalizePeriods(periods);
        if (allPeriods.size() == periods.size()) {
            int i = 0;
            while (i < allPeriods.size()) {
                FreezePeriod current = allPeriods.get(i);
                if (current.getLength() <= 90) {
                    if (i > 0) {
                        previous = allPeriods.get(i - 1);
                    } else {
                        previous = allPeriods.get(allPeriods.size() - 1);
                    }
                    if (previous != current) {
                        if (i != 0 || previous.isWrapped()) {
                            separation = (current.mStartDay - previous.mEndDay) - 1;
                        } else {
                            separation = (current.mStartDay + (365 - previous.mEndDay)) - 1;
                        }
                        if (separation < 60) {
                            throw SystemUpdatePolicy.ValidationFailedException.freezePeriodTooClose("Freeze periods " + previous + " and " + current + " are too close together: " + separation + " days apart");
                        }
                    }
                    i++;
                } else {
                    throw SystemUpdatePolicy.ValidationFailedException.freezePeriodTooLong("Freeze period " + current + " is too long: " + current.getLength() + " days");
                }
            }
            return;
        }
        throw SystemUpdatePolicy.ValidationFailedException.duplicateOrOverlapPeriods();
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x0053  */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x006b A[EDGE_INSN: B:34:0x006b->B:17:0x006b ?: BREAK  , SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static void validateAgainstPreviousFreezePeriod(java.util.List<android.app.admin.FreezePeriod> r10, java.time.LocalDate r11, java.time.LocalDate r12, java.time.LocalDate r13) {
        /*
            int r0 = r10.size()
            if (r0 == 0) goto L_0x0148
            if (r11 == 0) goto L_0x0148
            if (r12 != 0) goto L_0x000c
            goto L_0x0148
        L_0x000c:
            boolean r0 = r11.isAfter(r13)
            if (r0 != 0) goto L_0x0018
            boolean r0 = r12.isAfter(r13)
            if (r0 == 0) goto L_0x003e
        L_0x0018:
            java.lang.String r0 = "FreezePeriod"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "Previous period ("
            r1.append(r2)
            r1.append(r11)
            java.lang.String r2 = ","
            r1.append(r2)
            r1.append(r12)
            java.lang.String r2 = ") is after current date "
            r1.append(r2)
            r1.append(r13)
            java.lang.String r1 = r1.toString()
            android.util.Log.w((java.lang.String) r0, (java.lang.String) r1)
        L_0x003e:
            java.util.List r0 = canonicalizePeriods(r10)
            r1 = 0
            java.lang.Object r1 = r0.get(r1)
            android.app.admin.FreezePeriod r1 = (android.app.admin.FreezePeriod) r1
            java.util.Iterator r2 = r0.iterator()
        L_0x004d:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L_0x006b
            java.lang.Object r3 = r2.next()
            android.app.admin.FreezePeriod r3 = (android.app.admin.FreezePeriod) r3
            boolean r4 = r3.contains(r13)
            if (r4 != 0) goto L_0x0069
            int r4 = r3.mStartDay
            int r5 = dayOfYearDisregardLeapYear(r13)
            if (r4 <= r5) goto L_0x0068
            goto L_0x0069
        L_0x0068:
            goto L_0x004d
        L_0x0069:
            r1 = r3
        L_0x006b:
            android.util.Pair r2 = r1.toCurrentOrFutureRealDates(r13)
            F r3 = r2.first
            java.time.chrono.ChronoLocalDate r3 = (java.time.chrono.ChronoLocalDate) r3
            boolean r3 = r13.isAfter(r3)
            if (r3 == 0) goto L_0x0084
            android.util.Pair r3 = new android.util.Pair
            S r4 = r2.second
            java.time.LocalDate r4 = (java.time.LocalDate) r4
            r3.<init>(r13, r4)
            r2 = r3
        L_0x0084:
            F r3 = r2.first
            java.time.LocalDate r3 = (java.time.LocalDate) r3
            S r4 = r2.second
            java.time.chrono.ChronoLocalDate r4 = (java.time.chrono.ChronoLocalDate) r4
            boolean r3 = r3.isAfter(r4)
            if (r3 != 0) goto L_0x0125
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "Prev: "
            r3.append(r4)
            r3.append(r11)
            java.lang.String r4 = ","
            r3.append(r4)
            r3.append(r12)
            java.lang.String r4 = "; cur: "
            r3.append(r4)
            F r4 = r2.first
            r3.append(r4)
            java.lang.String r4 = ","
            r3.append(r4)
            S r4 = r2.second
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            F r4 = r2.first
            java.time.LocalDate r4 = (java.time.LocalDate) r4
            int r4 = distanceWithoutLeapYear(r4, r12)
            int r4 = r4 + -1
            long r4 = (long) r4
            r6 = 0
            int r6 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r6 <= 0) goto L_0x00f5
            r6 = 60
            int r6 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r6 < 0) goto L_0x00d7
            goto L_0x0106
        L_0x00d7:
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = "Previous freeze period too close to new period: "
            r6.append(r7)
            r6.append(r4)
            java.lang.String r7 = ", "
            r6.append(r7)
            r6.append(r3)
            java.lang.String r6 = r6.toString()
            android.app.admin.SystemUpdatePolicy$ValidationFailedException r6 = android.app.admin.SystemUpdatePolicy.ValidationFailedException.combinedPeriodTooClose(r6)
            throw r6
        L_0x00f5:
            S r6 = r2.second
            java.time.LocalDate r6 = (java.time.LocalDate) r6
            int r6 = distanceWithoutLeapYear(r6, r11)
            int r6 = r6 + 1
            long r6 = (long) r6
            r8 = 90
            int r8 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
            if (r8 > 0) goto L_0x0107
        L_0x0106:
            return
        L_0x0107:
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            java.lang.String r9 = "Combined freeze period exceeds maximum days: "
            r8.append(r9)
            r8.append(r6)
            java.lang.String r9 = ", "
            r8.append(r9)
            r8.append(r3)
            java.lang.String r8 = r8.toString()
            android.app.admin.SystemUpdatePolicy$ValidationFailedException r8 = android.app.admin.SystemUpdatePolicy.ValidationFailedException.combinedPeriodTooLong(r8)
            throw r8
        L_0x0125:
            java.lang.IllegalStateException r3 = new java.lang.IllegalStateException
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "Current freeze dates inverted: "
            r4.append(r5)
            F r5 = r2.first
            r4.append(r5)
            java.lang.String r5 = "-"
            r4.append(r5)
            S r5 = r2.second
            r4.append(r5)
            java.lang.String r4 = r4.toString()
            r3.<init>(r4)
            throw r3
        L_0x0148:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.app.admin.FreezePeriod.validateAgainstPreviousFreezePeriod(java.util.List, java.time.LocalDate, java.time.LocalDate, java.time.LocalDate):void");
    }
}
