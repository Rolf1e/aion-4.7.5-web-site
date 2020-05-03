package com.aion.server.service.infra.utils;

import lombok.experimental.UtilityClass;

import java.util.Date;

@UtilityClass
public class DateUtils {

    public static final long ONE_HOUR_MILLISECOND = 3600000L;

    public static Date getCurrentDate() {
        return new Date();
    }

    public static Date resolveDate(final Date updatedAt) {
        final long currentTime = getCurrentDate().getTime();
        if (updatedAt.getTime() - currentTime <= 0) {
            return new Date(currentTime + ONE_HOUR_MILLISECOND);
        }
        return updatedAt;
    }
}
