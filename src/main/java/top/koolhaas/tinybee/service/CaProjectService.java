package top.koolhaas.tinybee.service;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import top.koolhaas.tinybee.domain.CaUser;
import top.koolhaas.tinybee.infrastructure.mapper.CaCommitRecordMapper;
import top.koolhaas.tinybee.infrastructure.repository.CaProjectRepository;
import top.koolhaas.tinybee.vo.ProjectVo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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
public class CaProjectService {

    @Autowired
    private CaCommitRecordService caCommitRecordService;
    @Autowired
    private CaProjectRepository caProjectRepository;
    @Autowired
    private CaCommitRecordMapper caCommitRecordMapper;

    /**
     * 获取所有项目信息
     *
     * @return
     */
//    @Cached(name = "getProjects", expire = 60, cacheType = CacheType.LOCAL)
    public List<ProjectVo> getProjects() {
        Map<String, CaUser> users = getUsers();

        List<ProjectVo> projectVos = caProjectRepository.list().stream().map(caProject -> {
            List<ProjectVo.U> us = Lists.newArrayList();
            List<String> userEmails = caCommitRecordMapper.queryUserEmailByProjectId(caProject.getId());
            for (String userEmail : userEmails) {
                CaUser user = users.get(userEmail);
                if (null != user) {
                    us.add(ProjectVo.U.builder()
                            .username(user.getUserName())
                            .email(user.getUserEmail())
                            .avatarUrl(user.getAvatarUrl())
                            .build());
                }
            }
            ProjectVo projectVo = ProjectVo.builder()
                    .id(caProject.getId())
                    .projectId(caProject.getProjectId())
                    .name(caProject.getName())
                    .description(caProject.getDescription())
                    .defaultBranch(caProject.getDefaultBranch())
                    .webUrl(caProject.getWebUrl())
                    .wikiEnabled(caProject.getWikiEnabled())
                    .users(us)
                    .build();

            Date lastActivityAt = DateUtil.date(caProject.getLastActivityAt());
            if (null != lastActivityAt) {
                projectVo.setLastActivityAt(DateUtil.offsetHour(lastActivityAt, 8));
                projectVo.setLastActivityAtStr(DateUtil.format(projectVo.getLastActivityAt(), DatePattern.NORM_DATETIME_MINUTE_PATTERN));
                projectVo.setCreateTime(DateUtil.date(caProject.getCreateTime()));
                projectVo.setUpdateDate(DateUtil.date(caProject.getUpdateDate()));
            }
            return projectVo;

        }).collect(Collectors.toList());
        return projectVos;
    }

    /**
     * 将所有用户信息转换为Map
     *
     * @return
     */
    private Map<String, CaUser> getUsers() {
        Map<String, CaUser> usersMap = Maps.newHashMap();
        List<CaUser> users = caCommitRecordService.getCaUsers();
        for (CaUser user : users) {
            usersMap.put(user.getUserEmail(), user);
        }
        return usersMap;
    }

}
