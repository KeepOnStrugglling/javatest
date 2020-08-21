package com.javatest.util;

import org.apache.commons.lang3.StringUtils;

/**
 * cron表达式解析为中文，部分复杂的cron不能解析
 *
 * @author azure
 * @date 2020/6/24
 */
public class CronUtil {
    public static String decode(String cronExpression) {
        if (StringUtils.isBlank(cronExpression)) {
            return cronExpression;
        }
        // 对于前端设置的常用cron表达式，直接沿用前端的描述
        switch (cronExpression) {
            case "0 0 0 * * ? *":
                return "每天凌晨0点";
            case "0 0 18 * * ? *":
                return "每天下午6点";
            case "0 0 8,17 * * ? *":
                return "每天早上8点、下午5点";
            case "0 0 2 L * ? *":
                return "每月最后一日的凌晨02:00";
            case "0 15 10 ? * MON-FRI *":
                return "周一至周五的上午10:15";
            default:
                break;
        }
        String[] crons = cronExpression.split(" ");
        StringBuilder sb = new StringBuilder();
        if (crons.length == 7) {
            // 解析年
            String year = crons[6];
            if (StringUtils.isNumeric(year)) {
                sb.append("在").append(year).append("年");
            }
            if (year.contains("-")) {
                String[] yearRange = year.split("-");
                sb.append("从").append(yearRange[0]).append("年到").append(yearRange[1]).append("年");
            }// 特殊字符/、,暂未处理
        }

        if (!cronExpression.endsWith("* * ?") && !cronExpression.endsWith("? * *") && !cronExpression.endsWith("* * ? *") && !cronExpression.endsWith("? * * *")) {
            // 解析月
            if (!"*".equals(crons[4])) {
                String month = crons[4];
                if (crons.length == 6) {
                    sb.append("每年");
                }
                if (StringUtils.isNumeric(month)) {
                    sb.append(month).append("月");
                } else if (month.contains("-")) {
                    String[] monthSplits = month.split("-");
                    sb.append("从").append(decodeMonth(monthSplits[0])).append("月到").append(decodeMonth(monthSplits[1])).append("月");
                } else if (month.contains(",")) {
                    String[] monthSplits = month.split(",");
                    sb.append("每");
                    for (int i = 0; i < monthSplits.length; i++) {
                        if (i == monthSplits.length - 1) {
                            sb.append(decodeMonth(monthSplits[i])).append("月");
                        } else {
                            sb.append(decodeMonth(monthSplits[i])).append("、");
                        }
                    }
                } // 特殊字符/暂未处理
            } else {
                sb.append("每月");
            }

            // 解析周
            if (!"*".equals(crons[5]) && !"?".equals(crons[5])) {
                String week = crons[5];
                if (StringUtils.isNumeric(week)) {
                    week = decodeWeek(week);
                    sb.append("周").append(week);
                } else if (week.contains("-")) {
                    String[] weekSplits = week.split("-");
                    sb.append("每周").append(decodeWeek(weekSplits[0])).append("到周").append(decodeWeek(weekSplits[1]));
                } else if (week.contains(",")) {
                    String[] weekSplits = week.split(",");
                    sb.append("每周");
                    for (int i = 0; i < weekSplits.length; i++) {
                        if (i == weekSplits.length - 1) {
                            sb.append(decodeWeek(weekSplits[i]));
                        } else {
                            sb.append(decodeWeek(weekSplits[i])).append("、");
                        }
                    }
                } else if (week.contains("L")) {
                    sb.append("最后一个星期").append(decodeWeek(week.substring(0, week.length() - 1)));
                } else if (week.contains("#")) {
                    String[] weekSplits = week.split("#");
                    sb.append("第").append(weekSplits[1]).append("个周").append(decodeWeek(weekSplits[0]));
                } else {
                    week = decodeWeek(week);
                    if (week != null) {
                        sb.append("每周").append(week);
                    }
                } // 特殊字符/、L、C暂未处理
            }

            // 解析天
            if (!"?".equals(crons[3])) {
                if (!"*".equals(crons[3])) {
                    String day = crons[3];
                    if (StringUtils.isNumeric(day)) {
                        sb.append(day).append("号");
                    } else if (day.contains("-")) {
                        String[] daySplits = day.split("-");
                        sb.append("从").append(daySplits[0]).append("号到").append(daySplits[1]).append("号");
                    } else if (day.contains(",")) {
                        String[] daySplits = day.split(",");
                        sb.append("第");
                        for (int i = 0; i < daySplits.length; i++) {
                            if (i == daySplits.length - 1) {
                                sb.append(daySplits[i]).append("号");
                            } else {
                                sb.append(daySplits[i]).append("、");
                            }
                        }
                    } else if (day.contains("/")) {
                        String[] daySplits = day.split("/");
                        sb.append("从").append(daySplits[0]).append("号开始每隔").append(daySplits[1]).append("天");
                    } else if (day.contains("L")) {
                        if ("L".equals(day) || "1L".equals(day)) {
                            sb.append("最后一天");
                        } else {
                            sb.append("倒数第").append(day.substring(0, day.length() - 1)).append("天");
                        }
                    } // 特殊符号W、C暂未处理
                } else {
                    sb.append("每天");
                }

            }
        } else {
            sb.append("每天");
        }

        // 解析时
        if (!"*".equals(crons[2])) {
            String hour = crons[2];
            if (StringUtils.isNumeric(hour)) {
                sb.append(hour).append("点");
            } else if (hour.contains("-")) {
                String[] hourSplits = hour.split("-");
                sb.append(hourSplits[0]).append("点到").append(hourSplits[1]).append("点");
            } else if (hour.contains(",")) {
                String[] hourSplits = hour.split(",");
                sb.append("第");
                for (int i = 0; i < hourSplits.length; i++) {
                    if (i == hourSplits.length - 1) {
                        sb.append(hourSplits[i]).append("点");
                    } else {
                        sb.append(hourSplits[i]).append("、");
                    }
                }
            } else if (hour.contains("/")) {
                String[] hourSplits = hour.split("/");
                sb.append("从").append(hourSplits[0]).append("点开始每隔").append(hourSplits[1]).append("小时");
            }
        }

        // 解析分
        if (!"*".equals(crons[1]) && !"0".equals(crons[1])) {
            String minute = crons[1];
            if (StringUtils.isNumeric(minute)) {
                sb.append(minute).append("分");
            } else if (minute.contains("-")) {
                String[] minuteSplits = minute.split("-");
                sb.append(minuteSplits[0]).append("分到").append(minuteSplits[1]).append("分");
            } else if (minute.contains(",")) {
                String[] hourSplits = minute.split(",");
                sb.append("第");
                for (int i = 0; i < hourSplits.length; i++) {
                    if (i == hourSplits.length - 1) {
                        sb.append(hourSplits[i]).append("分");
                    } else {
                        sb.append(hourSplits[i]).append("、");
                    }
                }
            } else if (minute.contains("/")) {
                String[] hourSplits = minute.split("/");
                sb.append("从").append(hourSplits[0]).append("分开始每隔").append(hourSplits[1]).append("分钟");
            }
        }
        // 解析秒
        if (!"*".equals(crons[0]) && !"0".equals(crons[0])) {
            String second = crons[0];
            if (StringUtils.isNumeric(second)) {
                sb.append(second).append("秒");
            } else if (second.contains("-")) {
                String[] secondSplits = second.split("-");
                sb.append(secondSplits[0]).append("秒到").append(secondSplits[1]).append("秒");
            } else if (second.contains(",")) {
                String[] hourSplits = second.split(",");
                sb.append("第");
                for (int i = 0; i < hourSplits.length; i++) {
                    if (i == hourSplits.length - 1) {
                        sb.append(hourSplits[i]).append("秒");
                    } else {
                        sb.append(hourSplits[i]).append("、");
                    }
                }
            } else if (second.contains("/")) {
                String[] hourSplits = second.split("/");
                sb.append("从").append(hourSplits[0]).append("秒开始每隔").append(hourSplits[1]).append("秒");
            }
        }

        return sb.toString();
    }

    private static String decodeMonth(String month) {
        if (StringUtils.isNumeric(month)) {
            return month;
        }
        switch (month) {
            case "JAN":
                return "1";
            case "FEB":
                return "2";
            case "MAR":
                return "3";
            case "APR":
                return "4";
            case "MAY":
                return "5";
            case "JUN":
                return "6";
            case "JUL":
                return "7";
            case "AUG":
                return "8";
            case "SEP":
                return "9";
            case "OCT":
                return "10";
            case "NOV":
                return "11";
            case "DEC":
                return "12";
            default:
                return null;
        }
    }

    private static String decodeWeek(String week) {
        switch (week) {
            case "1":
                return "日";
            case "2":
                return "一";
            case "3":
                return "二";
            case "4":
                return "三";
            case "5":
                return "四";
            case "6":
                return "五";
            case "7":
                return "六";
            case "SUN":
                return "日";
            case "MON":
                return "一";
            case "TUE":
                return "二";
            case "WED":
                return "三";
            case "THU":
                return "四";
            case "FRI":
                return "五";
            case "SAT":
                return "六";
            default:
                return null;
        }

    }


    public static void main(String[] args) {
        System.out.println(decode("0 15 10 L * ? "));
    }
}
