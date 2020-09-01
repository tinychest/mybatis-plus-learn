package priv.wmc.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * 时间工具类
 *
 * @author Wang Mincong
 * @date 2020-07-20 21:07:35
 */
public final class TimeUtils {

    private TimeUtils() {}

    /**
     * 获取指定日期的Date
     *
     * @param year 年
     * @param monthOfYear 年中的月
     * @param dayOfMonth 月中的日
     * @return Date日期
     */
    public static Date getDate(int year, int monthOfYear, int dayOfMonth) {
        LocalDateTime localDateTime = LocalDateTime.of(year, monthOfYear, dayOfMonth, 0, 0);
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 获取当前时间
     */
    public static LocalDateTime now() {
        return LocalDateTime.now(ZoneId.systemDefault());
    }

}
