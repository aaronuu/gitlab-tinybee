package top.koolhaas.tinybee.inbound.ui.frontend.controller;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import top.koolhaas.tinybee.core.response.ResponseData;
import top.koolhaas.tinybee.service.ChartService;
import top.koolhaas.tinybee.service.JobService;
import top.koolhaas.tinybee.vo.TrendChartVo;
import com.google.common.base.Strings;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @program: tinybee
 * @description:
 * @author: hackerdom
 * @created: 2021/08/21
 */
@Slf4j
@Api(tags = "Chart接口")
@RestController
public class ChartController {

    @Autowired
    private ChartService chartService;

    @Autowired
    private JobService jobService;

    @GetMapping("/add/data")
    public void test(@RequestParam(defaultValue = "1") Integer offset) {
        Date dateFrom = DateUtil.beginOfDay(DateUtil.offsetDay(DateUtil.date(), offset));
        Date dateEnd = DateUtil.endOfDay(dateFrom);
        jobService.commitRecordJob(dateFrom, dateEnd);
    }

    @GetMapping("/chart/{email}")
    public ResponseData<TrendChartVo> chart(@PathVariable String email,
                                            @ApiParam(value = "开始时间（小于）：yyyy-MM-dd") @RequestParam(required = false, defaultValue = "") String startTime,
                                            @ApiParam(value = "结束时间（大于）：yyyy-MM-dd") @RequestParam(required = false, defaultValue = "") String endTime) {
        Date date = DateUtil.date();
        if (Strings.isNullOrEmpty(startTime)) {
            startTime = DateUtil.format(DateUtil.offsetDay(date, -7), DatePattern.NORM_DATE_PATTERN);
        }
        if (Strings.isNullOrEmpty(endTime)) {
            endTime = DateUtil.format(date, DatePattern.NORM_DATE_PATTERN);
        }
        return ResponseData.success(chartService.getChart(startTime, endTime, email));
    }

    @GetMapping("/sidebar/img")
    @ResponseBody
    public ResponseData<Integer> getSidebarImg() {
        return ResponseData.success(RandomUtil.randomInt(1, 20));
    }

    @GetMapping("/test")
    public void test(
        @RequestParam String startDate,
        @RequestParam String endDate) {
        Date dateFrom = DateUtil.beginOfDay(DateUtil.parse(startDate, DatePattern.NORM_DATE_PATTERN));
        Date dateEnd = DateUtil.endOfDay(DateUtil.parse(endDate, DatePattern.NORM_DATE_PATTERN));
        log.info("{}, {}", dateFrom, dateEnd);
        jobService.commitRecordJob(dateFrom, dateEnd);
    }

}
