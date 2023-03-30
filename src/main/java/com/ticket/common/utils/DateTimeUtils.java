package com.ticket.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 返回两个时间间隔内的所有
 */
public class DateTimeUtils {

    /**
     * 返回两个时间间隔内的所有日期（正序）
     *
     * @param dateStart
     * @param dateEnd
     * @return java.util.List<java.lang.String>
     * @Title: getTwoDaysDay
     */
    public static List<String> getTwoDaysDay(String dateStart, String dateEnd) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        List<String> dateList = new ArrayList<String>();

        try {
            Date dateOne = sdf.parse(dateStart);
            Date dateTwo = sdf.parse(dateEnd);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateOne);

            dateList.add(sdf.format(dateOne));
            while (calendar.getTime().before(dateTwo)) {
                calendar.add(Calendar.DAY_OF_MONTH, +1);
                dateList.add(sdf.format(calendar.getTime()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateList;
    }

    /**
     * 获取两个日期之间的所有月份 (年月)
     *
     * @param startTime
     * @param endTime
     * @return：YYYY-MM
     */
    public static List<String> getMonthBetweenDate(String startTime, String endTime){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        // 声明保存日期集合
        List<String> list = new ArrayList<String>();
        try {
            // 转化成日期类型
            Date startDate = sdf.parse(startTime);
            Date endDate = sdf.parse(endTime);

            //用Calendar 进行日期比较判断
            Calendar calendar = Calendar.getInstance();
            while (startDate.getTime()<=endDate.getTime()){
                // 把日期添加到集合
                list.add(sdf.format(startDate));
                // 设置日期
                calendar.setTime(startDate);
                //把日期增加一天
                calendar.add(Calendar.MONTH, 1);
                // 获取增加后的日期
                startDate=calendar.getTime();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return list;
    }


}
