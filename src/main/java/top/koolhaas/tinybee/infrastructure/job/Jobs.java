package top.koolhaas.tinybee.infrastructure.job;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import top.koolhaas.tinybee.service.JobService;

import java.util.Date;

/**
 * @program: tinybee
 * @description:
 * @author: hackerdom
 * @created: 2021/08/21
 */
@Slf4j
@Component
public class Jobs {

    @Autowired
    private JobService jobService;

    /**
     * 每15分钟同步一次
     */
    @Scheduled(cron = "0 0/15 8-23 * * ?")
    public void commitRecordJob() {
        Date dateFrom = DateUtil.beginOfDay(DateUtil.date());
        Date dateEnd = DateUtil.endOfDay(DateUtil.offsetDay(dateFrom, 1));
        jobService.commitRecordJob(dateFrom, dateEnd);
        jobService.userJob();
    }

    /**
     *
     */
    @Scheduled(cron = "0 0 0 1 1 ?")
    public void jobService() {
        jobService.holidayJob();
    }

}
