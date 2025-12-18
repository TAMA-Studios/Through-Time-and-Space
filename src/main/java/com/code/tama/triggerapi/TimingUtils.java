package com.code.tama.triggerapi;

public class TimingUtils {

    private final long ticks;

    public TimingUtils(long time, Time type) {
        switch (type) {
            case NANOS   -> this.ticks = time / 50_000_000L;
            case MILLIS -> this.ticks = time / 50L;
            case SECONDS-> this.ticks = time * 20L;
            case MINUTES-> this.ticks = time * 20L * 60L;
            case HOURS  -> this.ticks = time * 20L * 60L * 60L;
            case TICKS  -> this.ticks = time;
            default     -> throw new IllegalArgumentException("Unknown time unit");
        }
    }

    /* ======================
       Conversions OUT
       ====================== */

    public long toNanos() {
        return ticks * 50_000_000L;
    }

    public long toMS() {
        return ticks * 50L;
    }

    public long toSec() {
        return ticks / 20L;
    }

    public long toMinutes() {
        return ticks / (20L * 60L);
    }

    public long toHours() {
        return ticks / (20L * 60L * 60L);
    }

    public long toTicks() {
        return ticks;
    }

    /* ======================
       Factory helpers
       ====================== */

    public static TimingUtils fromMS(long ms) {
        return new TimingUtils(ms, Time.MILLIS);
    }

    public static TimingUtils fromSeconds(long seconds) {
        return new TimingUtils(seconds, Time.SECONDS);
    }

    public static TimingUtils fromTicks(long ticks) {
        return new TimingUtils(ticks, Time.TICKS);
    }

    public enum Time {
        NANOS,
        MILLIS,
        SECONDS,
        MINUTES,
        HOURS,
        TICKS
    }
}
