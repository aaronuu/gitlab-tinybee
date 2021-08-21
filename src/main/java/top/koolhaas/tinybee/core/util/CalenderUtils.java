package top.koolhaas.tinybee.core.util;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Console;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Map;

/**
 * @program: tinybee
 * @description:
 * @author: hackerdom
 * @created: 2021/08/21
 */
public class CalenderUtils {

    public static void main(String[] args) {
        List<DateTime> monthOfThisYears = CalenderUtils.getMonthOfThisYear();
        for (DateTime monthOfThisYear : monthOfThisYears) {
            List<DateTime> dateTimes = DateUtil.rangeToList(monthOfThisYear, DateUtil.endOfMonth(monthOfThisYear), DateField.DAY_OF_WEEK);
            HttpResponse httpResponse = HttpRequest.get(CalenderUtils.getUrl(dateTimes)).execute();
            Map<String, Object> map = JSONUtil.toBean(httpResponse.body(), Map.class);
            Object data = map.get("holiday");
            if (!data.equals(null)) {
                Map<String, Object> days = (Map<String, Object>) data;
                for (Map.Entry<String, Object> entry : days.entrySet()) {
                    if (!entry.getValue().equals(null)) {
                        Map<String, Object> dayInfo = (Map<String, Object>) entry.getValue();
                        Boolean holiday = (Boolean) dayInfo.get("holiday");
                        String name = (String) dayInfo.get("name");
                        String date = (String) dayInfo.get("date");
                        Console.log("{}, {}, {}", holiday, name, date);
                    }
                }
            }
            break;
        }
    }

    public static String getUrl(List<DateTime> dateTimes) {
        String url = "http://timor.tech/api/holiday/batch?d=";
        StringBuilder prarm = new StringBuilder(url);
        for (int i = 0; i < dateTimes.size(); i++) {
            if (i < dateTimes.size() - 1) {
                prarm.append(DateUtil.format(dateTimes.get(i), DatePattern.NORM_DATE_PATTERN));
                prarm.append(",");
            } else {
                prarm.append(DateUtil.format(dateTimes.get(i), DatePattern.NORM_DATE_PATTERN));
            }
        }
        url = prarm.toString();
        Console.log("{}", url);
        return url;
    }

    public static List<DateTime> getMonthOfThisYear() {
        Integer year = DateUtil.thisYear();

        List<DateTime> list = Lists.newArrayList();

        for (int i = 1; i < 13; i++) {
            String day = "01";
            String month = String.valueOf(i);
            if (month.length() == 1) {
                month = "0" + month;
            }
            list.add(DateUtil.parse(year + month + day, DatePattern.PURE_DATE_PATTERN));
        }

        return list;
    }

}
