package top.koolhaas.tinybee.service;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import top.koolhaas.tinybee.domain.CaCommitRecord;
import top.koolhaas.tinybee.domain.CaHoliday;
import top.koolhaas.tinybee.domain.CaUser;
import top.koolhaas.tinybee.inbound.transfer.ChartTransfer;
import top.koolhaas.tinybee.infrastructure.repository.CaCommitRecordRepository;
import top.koolhaas.tinybee.infrastructure.repository.CaHolidayRepository;
import top.koolhaas.tinybee.vo.DataTableVo;
import top.koolhaas.tinybee.vo.TrendChartVo;
import top.koolhaas.tinybee.vo.UserInfoVo;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @program: tinybee
 * @description:
 * @author: hackerdom
 * @created: 2021/08/21
 */
@Slf4j
@Service
public class ChartService {

    @Autowired
    private CaCommitRecordRepository caCommitRecordRepository;

    @Autowired
    private CaHolidayRepository caHolidayRepository;

    /**
     * 获取图表
     *
     * @param dateFrom
     * @param dateEnd
     * @param email
     * @return
     */
    @Deprecated
    public TrendChartVo getChart(String dateFrom, String dateEnd, String email) {
        Map<String, String> holidays = getHolidays(caHolidayRepository.queryByDate(getCategories(dateFrom, dateEnd)));
        List<CaCommitRecord> caCommitRecords = caCommitRecordRepository.queryByCreateTime(DateUtil.parseLocalDateTime(dateFrom, DatePattern.NORM_DATE_PATTERN), DateUtil.parseLocalDateTime(dateEnd, DatePattern.NORM_DATE_PATTERN), email);
        return ChartTransfer.getTrendChartVo(dateFrom, dateEnd, caCommitRecords, holidays);
    }

    /**
     * 获取图表信息 & 数据表信息
     *
     * @param dateFrom
     * @param dateEnd
     * @param email
     * @return
     */
//    @Cached(name = "getUserInfo1", expire = 60, cacheType = CacheType.LOCAL)
    public UserInfoVo getUserInfo(String dateFrom, String dateEnd, String email, List<CaUser> users) {
        Map<String, String> holidays = getHolidays(caHolidayRepository.queryByDate(getCategories(dateFrom, dateEnd)));
        List<CaCommitRecord> caCommitRecords = caCommitRecordRepository.queryByCreateTime(DateUtil.parseLocalDateTime(dateFrom, DatePattern.NORM_DATE_PATTERN), DateUtil.parseLocalDateTime(dateEnd, DatePattern.NORM_DATE_PATTERN), email);
        TrendChartVo trendChartVo = ChartTransfer.getTrendChartVo(dateFrom, dateEnd, caCommitRecords, holidays);
        DataTableVo dataTableVo = ChartTransfer.getDataTableVo(dateFrom, dateEnd, email, caCommitRecords, users, holidays);
        return UserInfoVo.builder()
                .trendChartVo(trendChartVo)
                .dataTableVo(dataTableVo)
                .build();
    }

    /**
     * 获取假期
     *
     * @param caHolidays
     * @return
     */
    private Map<String, String> getHolidays(List<CaHoliday> caHolidays) {
        Map<String, String> holidays = Maps.newConcurrentMap();
        for (CaHoliday caHoliday : caHolidays) {
            holidays.put(caHoliday.getDate(), caHoliday.getHolidayName());
        }
        return holidays;
    }

    /**
     * 生成时间
     *
     * @param dateFrom
     * @param dateEnd
     * @return
     */
    private List<String> getCategories(String dateFrom, String dateEnd) {
        List<DateTime> dateTimes = DateUtil.rangeToList(DateUtil.parse(dateFrom, DatePattern.NORM_DATE_PATTERN), DateUtil.parse(dateEnd, DatePattern.NORM_DATE_PATTERN), DateField.DAY_OF_WEEK);
        return dateTimes.stream().map(dateTime -> DateUtil.format(dateTime, DatePattern.NORM_DATE_PATTERN)).collect(Collectors.toList());
    }

}
