package com.team4.studykit.global.util;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@AllArgsConstructor
@Slf4j
public class Time {
    private static class TIME_MAXIMUM {
        public static final int SEC = 60;
        public static final int MIN = 60;
        public static final int HOUR = 24;
        public static final int DAY = 30;
        public static final int MONTH = 12;
    }

    public static String calculateTime(Date date) {
        long curTime = System.currentTimeMillis();
        long regTime = date.getTime();
        long diffTime = (curTime - regTime) / 1000;
        String msg = null;
        if (diffTime < TIME_MAXIMUM.SEC) {
            // sec
            msg = diffTime + "초 전";
        } else if ((diffTime /= TIME_MAXIMUM.SEC) < TIME_MAXIMUM.MIN) {
            // min
            msg = diffTime + "분 전";
        } else if ((diffTime /= TIME_MAXIMUM.MIN) < TIME_MAXIMUM.HOUR) {
            // hour
            msg = (diffTime) + "시간 전";
        } else if ((diffTime /= TIME_MAXIMUM.HOUR) < TIME_MAXIMUM.DAY) {
            // day
            msg = (diffTime) + "일 전";
        } else if ((diffTime /= TIME_MAXIMUM.DAY) < TIME_MAXIMUM.MONTH) {
            // day
            msg = (diffTime) + "달 전";
        } else {
            msg = (diffTime) + "년 전";
        }
        return msg;
    }

    public static String calculatePercent(String dates) {
        String percent = "";
        try {
            String[] date = StringUtils.split(dates, "~");
            String start = date[0];
            String end = date[1];
            log.info(start);
            log.info(end);

            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date d1 = format.parse(start);
            Date d2 = format.parse(end);
            Date now = new Date();

            long total = (d2.getTime() - d1.getTime()) / 1000;
            long part = (now.getTime() - d1.getTime()) / 1000;
            log.info(String.valueOf(total));
            log.info(String.valueOf(part));
            long totals = total / (24 * 60 * 60);
            long parts = part / (24 * 60 * 60);

            percent = String.format("%.0f", (double) parts / totals * 100);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return percent;
    }
}
