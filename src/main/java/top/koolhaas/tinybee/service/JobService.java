package top.koolhaas.tinybee.service;

import cn.hutool.core.date.*;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Console;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import org.springframework.beans.factory.annotation.Value;
import top.koolhaas.tinybee.config.properties.GitLabApiProperties;
import top.koolhaas.tinybee.core.constants.GitlabUrlConstants;
import top.koolhaas.tinybee.domain.CaCommitRecord;
import top.koolhaas.tinybee.domain.CaHoliday;
import top.koolhaas.tinybee.domain.CaProject;
import top.koolhaas.tinybee.domain.CaUser;
import top.koolhaas.tinybee.infrastructure.repository.CaCommitRecordRepository;
import top.koolhaas.tinybee.infrastructure.repository.CaHolidayRepository;
import top.koolhaas.tinybee.infrastructure.repository.CaProjectRepository;
import top.koolhaas.tinybee.infrastructure.repository.CaUserRepository;
import top.koolhaas.tinybee.vo.*;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.ProjectApi;
import org.gitlab4j.api.UserApi;
import org.gitlab4j.api.models.Branch;
import org.gitlab4j.api.models.Project;
import org.gitlab4j.api.models.User;
import org.glassfish.jersey.internal.guava.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @program: tinybee
 * @description:
 * @author: hackerdom
 * @created: 2021/08/21
 */
@Slf4j
@Service
public class JobService {

    @Value("${tinybee.domain}")
    private String url;

    @Autowired
    private GitLabApi gitLabApi;
    @Autowired
    private GitLabApiProperties gitLabApiProperties;
    @Autowired
    private CaCommitRecordRepository caCommitRecordRepository;
    @Autowired
    private CaProjectRepository caProjectRepository;
    @Autowired
    private CaUserRepository caUserRepository;
    @Autowired
    private CaHolidayRepository caHolidayRepository;
    @Autowired
    private CaCommitRecordService caCommitRecordService;


    private String ROOT_PATH = new ApplicationHome(getClass()).getSource().getParentFile().toString();
    private static String UTC_WITH_ZONE_OFFSET_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    private static String MERGE_1 = "Merge branch";
    private static String MERGE_2 = "Merge remote-tracking branch";
    private static String MERGE_3 = "合并分支";
    private static String MERGE_4 = "merge";

    /**
     * 生成假期列表
     */
    public void holidayJob() {
        List<DateTime> monthOfThisYears = getMonthOfThisYear();
        for (DateTime monthOfThisYear : monthOfThisYears) {
            List<DateTime> dateTimes = DateUtil.rangeToList(monthOfThisYear, DateUtil.endOfMonth(monthOfThisYear), DateField.DAY_OF_WEEK);
            HttpResponse httpResponse = HttpRequest.get(getUrl(dateTimes)).execute();
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
                        if (holiday) {
                            try {
                                caHolidayRepository.save(CaHoliday.create(caHolidayRepository.nextIdentity(), name, date));
                            } catch (Exception e) {
                                log.error("[节假日更新失败] {}", dayInfo);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 保存所有用户
     *
     * @return
     */
    public void userJob() {
        UserApi userApi = gitLabApi.getUserApi();
        List<User> users = Lists.newArrayList();
        try {
            int i = 1;
            while (true) {
                List<User> baseUsers = userApi.getUsers(i, 100);
                if (baseUsers.size() > 0) {
                    i++;
                    users.addAll(baseUsers);
                } else {
                    break;
                }
            }
        } catch (GitLabApiException e) {
            log.info("获取Gitlab用户异常 {}", e);
        }
        for (User user : users) {
            CaUser caUser = caUserRepository.queryByUserId(String.valueOf(user.getId()));
            if (null == caUser) {
                caUser = CaUser.create(
                        String.valueOf(user.getId()),
                        user.getName(),
                        user.getUsername().toLowerCase(),
                        "",
                        user.getEmail().toLowerCase(),
                        "",
                        true);
            }
            String avatarUrl = user.getAvatarUrl();
            if (!avatarUrl.toLowerCase().contains("https")) {
                String path = ROOT_PATH + "/avatar/" + user.getId();
                FileUtil.mkdir(path);
                HttpUtil.downloadFile(avatarUrl, FileUtil.file(path + "/avatar.png"));
                caUser.updateAvatarUrl(url + "/avatar/" + user.getId());
            } else {
                caUser.updateAvatarUrl(user.getAvatarUrl());
            }
            if (!user.getState().equals("active")) {
                caUser.updateEnable(false);
            }
            try {
                caUserRepository.saveOrUpdate(caUser);
            } catch (Exception e) {
                log.error("[更新用户失败] {}", caUser.getId());
            }
        }
    }

    /**
     * 获取所有项目
     *
     * @return
     */
    @Deprecated
    public List<ProjectsVo> getProjects() {
        List<ProjectsVo> projectsVos = Lists.newArrayList();
        int i = 1;
        while (true) {
            HttpResponse httpResponse = HttpRequest.get(GitlabUrlConstants.getProjectUrl(gitLabApiProperties.getHostUrl(), gitLabApiProperties.getPrivateToken(), String.valueOf(i))).execute();
            JSONArray jsonArray = JSONUtil.parseArray(httpResponse.body());
            if (jsonArray.size() > 0) {
                i++;
                projectsVos.addAll(JSONUtil.toList(jsonArray, ProjectsVo.class));
            } else {
                break;
            }
        }
        return projectsVos;
    }

    /**
     * 获取所有分支
     *
     * @param projectId
     * @return
     */
    @Deprecated
    public List<BranchesVo> getBranches(String projectId) {
        HttpResponse httpResponse = HttpRequest.get(GitlabUrlConstants.getBrancheUrl(gitLabApiProperties.getHostUrl(), projectId, gitLabApiProperties.getPrivateToken())).execute();
        JSONArray jsonArray = JSONUtil.parseArray(httpResponse.body());
        return JSONUtil.toList(jsonArray, BranchesVo.class);
    }

    /**
     * 解决获取分支补全的情况
     *
     * @param projectId
     * @return
     */
    public List<Branch> getBranchesV2(String projectId) {
        List<Branch> list = Lists.newArrayList();
        try {
            list = gitLabApi.getRepositoryApi().getBranches(projectId);
        } catch (GitLabApiException e) {
            log.error("GitLabApiException 异常 {}", e);
        }
        return list;
    }

    /**
     * 获取所有提交记录
     *
     * @param projectId
     * @param brancheName
     * @param since
     * @param until
     * @return
     */
    public List<CommitsVo> getCommits(String projectId, String brancheName, String since, String until) {
        List<CommitsVo> commitVos = Lists.newArrayList();
        int i = 1;
        while (true) {
            HttpResponse httpResponse = HttpRequest.get(GitlabUrlConstants.getCommitUrl(gitLabApiProperties.getHostUrl(), projectId, String.valueOf(i), brancheName, since, until, gitLabApiProperties.getPrivateToken())).execute();
            JSONArray jsonArray = JSONUtil.parseArray(httpResponse.body());
            if (jsonArray.size() > 0) {
                i++;
                commitVos.addAll(JSONUtil.toList(jsonArray, CommitsVo.class));
            } else {
                break;
            }
        }
        return commitVos;
    }

    /**
     * 获取提交记录详细
     *
     * @param projectId
     * @param commitId
     * @return
     */
    public CommitDetailVo getCommitDetail(String projectId, String commitId) {
        HttpResponse httpResponse = HttpRequest.get(GitlabUrlConstants.getCommitDetailUrl(gitLabApiProperties.getHostUrl(), projectId, commitId, gitLabApiProperties.getPrivateToken())).execute();
        return JSONUtil.toBean(httpResponse.body(), CommitDetailVo.class);
    }

    /**
     * 统计数据
     *
     * @param dateFromStr
     * @param dateEndStr
     * @return
     */
    public List<UserVo> getUserContributions(String dateFromStr, String dateEndStr) {
        Map<String, UserVo> result = Maps.newHashMap();
        Date dateFrom = DateUtil.parseDate(dateFromStr);
        Date dateEnd = DateUtil.parseDate(dateEndStr);
        List<ProjectsVo> projectsVos = getProjects();

        Set<String> totalCommit = Sets.newHashSet();

        for (ProjectsVo project : projectsVos) {
            String defaultBranch = project.getDefault_branch();
            if (StrUtil.isEmpty(defaultBranch)) {
                continue;
            }
//            Date lastActivityAt = DateUtil.parse(project.getLast_activity_at().replace("+08:00", ""), UTC_WITH_ZONE_OFFSET_PATTERN);
//            //如果project的最后更新时间比起始时间小，则continue
//            int days = DateUtil.compare(lastActivityAt, dateFrom);
////            log.info("{}  ---  {}  ---  {}  ---  {}",project.getStr("last_activity_at"), lastActivityAt, dateFrom, days);
//            if (days == -1) {
//                continue;
//            }
            String projectId = project.getId();
            List<BranchesVo> branchesVos = getBranches(projectId);
            for (BranchesVo branchesVo : branchesVos) {
                if (branchesVo.isMerged()) {
                    continue;
                }
                String brancheName = branchesVo.getName();
                String since = DateUtil.format(dateFrom, DatePattern.UTC_MS_PATTERN);
                String until = DateUtil.format(dateEnd, DatePattern.UTC_MS_PATTERN);
                List<CommitsVo> commitVos = getCommits(projectId, brancheName, since, until);

                for (CommitsVo commitsVo : commitVos) {
                    String commitId = commitsVo.getId();
                    if (totalCommit.contains(commitId)) {
                        continue;
                    } else {
                        totalCommit.add(commitId);
                    }
                    CommitDetailVo commitDetailVo = getCommitDetail(projectId, commitId);
                    if (null != commitDetailVo && null != commitDetailVo.getStats()) {
                        String title = commitDetailVo.getTitle();

                        if (title.contains(MERGE_1) || title.contains(MERGE_2) || title.contains(MERGE_3) || title.contains(MERGE_4)) {
                            continue;
                        }

                        String committerName = commitDetailVo.getCommitter_name();
                        String committerEmail = commitDetailVo.getCommitter_email();
                        Date committedDate = DateUtil.parse(commitDetailVo.getCommitted_date(), UTC_WITH_ZONE_OFFSET_PATTERN);
                        Integer additions = commitDetailVo.getStats().getAdditions();
                        Integer deletions = commitDetailVo.getStats().getDeletions();
                        Integer total = commitDetailVo.getStats().getTotal();
                        UserVo userVo = result.get(committerName);
                        String projectName = project.getName();
                        String webUrl = project.getWeb_url();

                        UserVo.P p = UserVo.P.builder()
                                .id(projectId)
                                .name(projectName)
                                .branche(brancheName)
                                .webUrl(webUrl)
                                .build();

                        if (null == userVo) {
                            Set<String> projectNames = Sets.newHashSet();
                            projectNames.add(projectName);

                            List<UserVo.P> projects = Lists.newArrayList();
                            projects.add(p);

                            userVo = UserVo.builder()
                                    .name(committerName)
                                    .email(committerEmail)
                                    .addition(additions)
                                    .deletion(deletions)
                                    .lastCommitDate(committedDate)
                                    .total(total)
                                    .projectNames(projectNames)
                                    .projects(projects)
                                    .build();
                        } else {
                            userVo.setAddition(userVo.getAddition() + additions);
                            userVo.setDeletion(userVo.getDeletion() + deletions);
                            userVo.setTotal(userVo.getTotal() + total);

                            if (DateUtil.between(userVo.getLastCommitDate(), committedDate, DateUnit.MINUTE, false) > 0) {
                                userVo.setLastCommitDate(committedDate);
                            }

                            Set<String> projectNames = userVo.getProjectNames();
                            if (!projectNames.contains(projectName)) {
                                projectNames.add(projectName);
                                List<UserVo.P> projects = userVo.getProjects();
                                projects.add(p);
                                userVo.setProjectNames(projectNames);
                                userVo.setProjects(projects);
                            }
                        }
                        result.put(committerName, userVo);
                    }
                }

            }
        }
        Collection<UserVo> userVos = result.values();
        userVos.stream().forEach(userVo -> userVo.setLastCommitDate(DateUtil.offsetHour(userVo.getLastCommitDate(), 8)));
        return userVos.stream().sorted(Comparator.comparing(UserVo::getTotal).reversed()).collect(Collectors.toList());
    }

    /**
     * 更新用户提交记录 定时任务
     *
     * @param dateFrom
     * @param dateEnd
     */
    public void commitRecordJob(Date dateFrom, Date dateEnd) {

        List<CaUser> caUsers = caCommitRecordService.getCaUsers();

        List<ProjectsVo> projectsVos = getProjects();

        Set<String> totalCommit = Sets.newHashSet();

        for (ProjectsVo project : projectsVos) {
            String defaultBranch = project.getDefault_branch();
            if (StrUtil.isEmpty(defaultBranch)) {
                continue;
            }
            String projectId = project.getId();
            List<Branch> branchesVos = getBranchesV2(projectId);
            for (Branch branch : branchesVos) {
                if (branch.getMerged()) {
                    continue;
                }
                String brancheName = branch.getName();
                String since = DateUtil.format(dateFrom, DatePattern.UTC_MS_PATTERN);
                String until = DateUtil.format(dateEnd, DatePattern.UTC_MS_PATTERN);
                List<CommitsVo> commitVos = getCommits(projectId, brancheName, since, until);
                for (CommitsVo commitsVo : commitVos) {
                    String commitId = commitsVo.getId();
                    if (totalCommit.contains(commitId)) {
                        continue;
                    } else {
                        totalCommit.add(commitId);
                    }
                    CommitDetailVo commitDetailVo = getCommitDetail(projectId, commitId);
                    if (null != commitDetailVo && null != commitDetailVo.getStats()) {
                        String title = commitDetailVo.getTitle();
                        if (title.contains(MERGE_1) || title.contains(MERGE_2) || title.contains(MERGE_3)) {
                            continue;
                        }
                        LocalDateTime committedDate = DateUtil.parseLocalDateTime(commitDetailVo.getCommitted_date(), UTC_WITH_ZONE_OFFSET_PATTERN);
                        Integer addition = commitDetailVo.getStats().getAdditions();
                        Integer deletion = commitDetailVo.getStats().getDeletions();
                        Integer total = commitDetailVo.getStats().getTotal();

                        CaCommitRecord caCommitRecord = CaCommitRecord.create(caCommitRecordRepository.nextIdentity(),
                                commitDetailVo.getAuthor_name(),
                                commitDetailVo.getAuthor_email().toLowerCase(),
                                addition,
                                deletion,
                                total,
                                projectId,
                                project.getName(),
                                project.getWeb_url(),
                                brancheName,
                                commitId,
                                committedDate,
                                commitDetailVo.getCommitter_name(),
                                commitDetailVo.getMessage(),
                                title, DateUtil.toLocalDateTime(DateUtil.beginOfDay(DateUtil.parse(commitDetailVo.getCommitted_date(), UTC_WITH_ZONE_OFFSET_PATTERN))), caUsers);
                        try {
                            caCommitRecordRepository.save(caCommitRecord);
                        } catch (Exception e) {
                            log.error("[数据添加重复]:{}", commitDetailVo.getId());
                        }
                    }
                }
            }
        }
    }

    /**
     * 更新项目提交记录
     */
    public void projectJob() {
        ProjectApi projectApi = gitLabApi.getProjectApi();
        List<Project> projects = Lists.newArrayList();
        try {
            int i = 1;
            while (true) {
                List<Project> baseProjects = projectApi.getProjects(i, 100);
                if (baseProjects.size() > 0) {
                    i++;
                    projects.addAll(baseProjects);
                } else {
                    break;
                }
            }
        } catch (GitLabApiException e) {
            log.info("获取Gitlab所有项目 {}", e);
        }
        for (Project project : projects) {
            CaProject caProject = caProjectRepository.getById(Long.valueOf(project.getId()));
            if (null == caProject) {
                caProject = CaProject.create(
                        project.getId(),
                        caProjectRepository.nextIdentity(),
                        project.getName(),
                        project.getDescription(),
                        project.getDefaultBranch(),
                        project.getForksCount(),
                        project.getHttpUrlToRepo(),
                        project.getPathWithNamespace(),
                        project.getSshUrlToRepo(),
                        project.getStarCount(),
                        project.getWebUrl(),
                        project.getWikiEnabled(),
                        DateUtil.toLocalDateTime(project.getLastActivityAt()));
                caProjectRepository.save(caProject);
            } else {
                caProject.setForksCount(project.getForksCount());
                caProject.setStarCount(project.getStarCount());
                caProject.setWikiEnabled(caProject.getWikiEnabled());
                caProject.setUpdateDate(LocalDateTime.now());
                caProject.setLastActivityAt(DateUtil.toLocalDateTime(project.getLastActivityAt()));
                caProjectRepository.updateById(caProject);
            }
        }
    }

    /**
     * 获取假期请求地址
     *
     * @param dateTimes
     * @return
     */
    private String getUrl(List<DateTime> dateTimes) {
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

    /**
     * 获取当年时间
     *
     * @return
     */
    private List<DateTime> getMonthOfThisYear() {
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
