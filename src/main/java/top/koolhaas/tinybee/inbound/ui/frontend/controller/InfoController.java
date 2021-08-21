package top.koolhaas.tinybee.inbound.ui.frontend.controller;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import top.koolhaas.tinybee.service.CaCommitRecordService;
import top.koolhaas.tinybee.service.CaProjectService;
import top.koolhaas.tinybee.service.ChartService;
import top.koolhaas.tinybee.vo.InfoVo;
import com.google.common.base.Strings;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Date;

/**
 * @program: tinybee
 * @description:
 * @author: hackerdom
 * @created: 2021/08/21
 */
@Slf4j
@ApiIgnore
@Controller
public class InfoController {

    @Autowired
    private CaCommitRecordService caCommitRecordService;

    @Autowired
    private CaProjectService caProjectService;

    @Autowired
    private ChartService chartService;

    /**
     * 首页
     *
     * @param startTime
     * @param endTime
     * @param model
     * @return
     */
    @GetMapping("/")
    public String info(@ApiParam(value = "开始时间（小于）：yyyy-MM-dd") @RequestParam(required = false, defaultValue = "") String startTime,
                       @ApiParam(value = "结束时间（大于）：yyyy-MM-dd") @RequestParam(required = false, defaultValue = "") String endTime,
                       Model model) {
        Date date = DateUtil.date();
        if (Strings.isNullOrEmpty(startTime)) {
            startTime = DateUtil.format(date, DatePattern.NORM_DATE_PATTERN);
        }
        if (Strings.isNullOrEmpty(endTime)) {
            endTime = startTime;
        }
        InfoVo infoVo = caCommitRecordService.getInfoVo(startTime, endTime, caCommitRecordService.getCaUsers());

        model.addAttribute("infoVo", infoVo);
        model.addAttribute("startTime", startTime);
        model.addAttribute("endTime", endTime);
        return "/gitlab/info.html";
    }

    @GetMapping("/data/{ts}")
    public String info(@PathVariable Integer ts, Model model) {
        Date date = DateUtil.date();
        String startTime;
        String endTime;
        if (ts > 30) {
            ts = 30;
        }
        if (ts <= 0) {
            startTime = DateUtil.format(DateUtil.beginOfWeek(date), DatePattern.NORM_DATE_PATTERN);
            endTime = DateUtil.format(DateUtil.endOfWeek(date), DatePattern.NORM_DATE_PATTERN);
        } else {
            startTime = DateUtil.format(DateUtil.offsetDay(date, -ts), DatePattern.NORM_DATE_PATTERN);
            endTime = DateUtil.format(date, DatePattern.NORM_DATE_PATTERN);
        }
        InfoVo infoVo = caCommitRecordService.getInfoVo(startTime, endTime, caCommitRecordService.getCaUsers());
        model.addAttribute("infoVo", infoVo);
        model.addAttribute("startTime", startTime);
        model.addAttribute("endTime", endTime);
        return "/gitlab/info.html";
    }

    /**
     * 获取所有项目信息
     *
     * @param model
     * @return
     */
    @GetMapping("/projects")
    public String projects(Model model) {
        model.addAttribute("projects", caProjectService.getProjects());
        return "/gitlab/project.html";
    }

    /**
     * 用户个人详细信息页面
     *
     * @param email
     * @param model
     * @return
     */
    @GetMapping("/user/{email}/info")
    public String chart(@ApiParam(value = "用户邮箱") @PathVariable String email,
                        @ApiParam(value = "开始时间（小于）：yyyy-MM-dd") @RequestParam(required = false, defaultValue = "") String startTime,
                        @ApiParam(value = "结束时间（大于）：yyyy-MM-dd") @RequestParam(required = false, defaultValue = "") String endTime,
                        Model model) {
        Date date = DateUtil.date();
        if (Strings.isNullOrEmpty(startTime)) {
            startTime = DateUtil.format(DateUtil.offsetDay(date, -6), DatePattern.NORM_DATE_PATTERN);
        }
        if (Strings.isNullOrEmpty(endTime)) {
            endTime = DateUtil.format(date, DatePattern.NORM_DATE_PATTERN);
        }
        model.addAttribute("startTime", startTime);
        model.addAttribute("endTime", endTime);
        model.addAttribute("email", email);
        model.addAttribute("userInfo", chartService.getUserInfo(startTime, endTime, email, caCommitRecordService.getCaUsers()));
        model.addAttribute("infoVo", caCommitRecordService.getInfoVo());
        return "/gitlab/user-info.html";
    }

    @GetMapping("/user/{email}/{ts}")
    public String chart(@PathVariable String email, @PathVariable Integer ts, Model model) {
        Date date = DateUtil.date();
        String startTime;
        String endTime;
        if (ts > 30) {
            ts = 30;
        }
        if (ts <= 0) {
            startTime = DateUtil.format(DateUtil.beginOfWeek(date), DatePattern.NORM_DATE_PATTERN);
            endTime = DateUtil.format(DateUtil.endOfWeek(date), DatePattern.NORM_DATE_PATTERN);
        } else {
            startTime = DateUtil.format(DateUtil.offsetDay(date, -ts), DatePattern.NORM_DATE_PATTERN);
            endTime = DateUtil.format(date, DatePattern.NORM_DATE_PATTERN);
        }
        model.addAttribute("startTime", startTime);
        model.addAttribute("endTime", endTime);
        model.addAttribute("email", email);
        model.addAttribute("userInfo", chartService.getUserInfo(startTime, endTime, email, caCommitRecordService.getCaUsers()));
        model.addAttribute("infoVo", caCommitRecordService.getInfoVo());
        return "/gitlab/user-info.html";
    }

}
