package top.koolhaas.tinybee.inbound.transfer;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import top.koolhaas.tinybee.core.dto.CaCommitRecordVO;
import top.koolhaas.tinybee.domain.CaCommitRecord;
import top.koolhaas.tinybee.domain.CaUser;
import top.koolhaas.tinybee.vo.InfoVo;
import top.koolhaas.tinybee.vo.UserVo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class CaCommitRecordTransfer {

    /**
     * 单独封装用户数据
     * @param users
     * @return
     */
    public static InfoVo getInfoVo(List<CaUser> users) {
        return InfoVo.builder()
            .userVos(CaCommitRecordTransfer.getUserVos(users))
            .build();
    }

    public static InfoVo getInfoVo(List<CaCommitRecord> caCommitRecords, List<CaUser> users) {
        Integer addition = 0;
        Integer deletion = 0;

        for (CaCommitRecord caCommitRecord : caCommitRecords) {
            addition += caCommitRecord.getAddition();
            deletion += caCommitRecord.getDeletion();
        }

        return InfoVo.builder()
                .userVos(CaCommitRecordTransfer.getUserVos(caCommitRecords, users))
                .addition(NumberUtil.decimalFormat(",###", addition))
                .deletion(NumberUtil.decimalFormat(",###", deletion))
                .total(NumberUtil.decimalFormat(",###", addition + deletion))
                .build();
    }

    public static List<UserVo> getUserVos(List<CaUser> users) {
        return users.stream().map(user -> UserVo.builder()
            .name(user.getName())
            .email(user.getUserEmail())
            .position(user.getPosition())
            .avatarUrl(user.getAvatarUrl())
            .build()).collect(Collectors.toList());
    }

    public static List<UserVo> getUserVos(List<CaCommitRecord> caCommitRecords, List<CaUser> users) {
        Map<String, UserVo> result = Maps.newHashMap();
        Map<String, CaUser> caUsersToMap = getCaUsersToMap(users);
        for (CaCommitRecord caCommitRecord : caCommitRecords) {
            String userEmail = caCommitRecord.getUserEmail().toLowerCase();
            if (!caUsersToMap.containsKey(userEmail)) {
                continue;
            }

            Date commitDate = DateUtil.date(caCommitRecord.getCommitDate());
            UserVo userVo = result.get(userEmail);
            UserVo.P p = UserVo.P.builder()
                    .id(caCommitRecord.getProjectId())
                    .name(caCommitRecord.getProjectName())
                    .branche(caCommitRecord.getBranche())
                    .webUrl(caCommitRecord.getWebUrl())
                    .build();

            if (null == userVo) {
                Set<String> projectNames = Sets.newHashSet();
                projectNames.add(caCommitRecord.getProjectName());

                List<UserVo.P> projects = Lists.newArrayList();
                projects.add(p);

                userVo = UserVo.builder()
                        .name(caCommitRecord.getUserName())
                        .email(userEmail)
                        .addition(caCommitRecord.getAddition())
                        .deletion(caCommitRecord.getDeletion())
                        .lastCommitDate(commitDate)
                        .total(caCommitRecord.getTotal())
                        .projectNames(projectNames)
                        .projects(projects)
                        .build();
            } else {
                userVo.setAddition(userVo.getAddition() + caCommitRecord.getAddition());
                userVo.setDeletion(userVo.getDeletion() + caCommitRecord.getDeletion());
                userVo.setTotal(userVo.getTotal() + caCommitRecord.getTotal());

                if (DateUtil.between(userVo.getLastCommitDate(), commitDate, DateUnit.MINUTE, false) > 0) {
                    userVo.setLastCommitDate(commitDate);
                }

                Set<String> projectNames = userVo.getProjectNames();
                if (!projectNames.contains(caCommitRecord.getProjectName())) {
                    projectNames.add(caCommitRecord.getProjectName());
                    List<UserVo.P> projects = userVo.getProjects();
                    projects.add(p);
                    userVo.setProjectNames(projectNames);
                    userVo.setProjects(projects);
                }
            }
            result.put(userEmail, userVo);
        }

        for (CaUser user : users) {
            String email = user.getUserEmail();
            if (!result.containsKey(email)) {
                UserVo userVo = UserVo.builder()
                        .name(user.getName())
                        .position(user.getPosition())
                        .avatarUrl(user.getAvatarUrl())
                        .email(email)
                        .addition(0)
                        .deletion(0)
                        .lastCommitDateStr("-")
                        .total(0)
                        .projectNames(Sets.newHashSet())
                        .projects(Lists.newArrayList())
                        .build();
                result.put(email, userVo);
            }
            if (result.containsKey(email)) {
                UserVo userVo = result.get(email);
                userVo.setAvatarUrl(user.getAvatarUrl());
                userVo.setName(user.getName());
                userVo.setPosition(user.getPosition());
                userVo.setEmail(user.getUserEmail());
                result.put(email, userVo);
            }
        }

        Collection<UserVo> userVos = result.values();
        userVos.stream().forEach(userVo -> {
            Date date = userVo.getLastCommitDate();
            if (null != date) {
                userVo.setLastCommitDate(DateUtil.offsetHour(userVo.getLastCommitDate(), 8));
                userVo.setLastCommitDateStr(DateUtil.format(userVo.getLastCommitDate(), DatePattern.NORM_DATETIME_MINUTE_PATTERN));
            }
        });
        return userVos.stream().sorted(Comparator.comparing(UserVo::getAddition).reversed()).collect(Collectors.toList());
    }

    /**
     * 获取CaCommitRecord
     *
     * @param caCommitRecordVO
     * @return
     */
    public static CaCommitRecord getCaCommitRecord(CaCommitRecordVO caCommitRecordVO) {
        if (caCommitRecordVO == null) {
            return null;
        }
        return new CaCommitRecord()
                .setId(caCommitRecordVO.getId())
                .setCommitRecordId(caCommitRecordVO.getCommitRecordId())
                .setUserName(caCommitRecordVO.getUserName())
                .setUserEmail(caCommitRecordVO.getUserEmail())
                .setAddition(caCommitRecordVO.getAddition())
                .setDeletion(caCommitRecordVO.getDeletion())
                .setTotal(caCommitRecordVO.getTotal())
                .setProjectId(caCommitRecordVO.getProjectId())
                .setProjectName(caCommitRecordVO.getProjectName())
                .setWebUrl(caCommitRecordVO.getWebUrl())
                .setBranche(caCommitRecordVO.getBranche())
                .setCommitId(caCommitRecordVO.getCommitId())
                .setCommitName(caCommitRecordVO.getCommitName())
                .setCommitMessage(caCommitRecordVO.getCommitMessage())
                .setCommitTitle(caCommitRecordVO.getCommitTitle())
                .setCommitDate(caCommitRecordVO.getCommitDate())
                .setCreateTime(caCommitRecordVO.getCreateTime())
                ;
    }

    /**
     * 获取CaCommitRecordVO
     *
     * @param caCommitRecord
     * @return
     */
    public static CaCommitRecordVO getCaCommitRecordVO(CaCommitRecord caCommitRecord) {
        if (caCommitRecord == null) {
            return null;
        }
        return new CaCommitRecordVO()
                .setId(caCommitRecord.getId())
                .setCommitRecordId(caCommitRecord.getCommitRecordId())
                .setUserName(caCommitRecord.getUserName())
                .setUserEmail(caCommitRecord.getUserEmail())
                .setAddition(caCommitRecord.getAddition())
                .setDeletion(caCommitRecord.getDeletion())
                .setTotal(caCommitRecord.getTotal())
                .setProjectId(caCommitRecord.getProjectId())
                .setProjectName(caCommitRecord.getProjectName())
                .setWebUrl(caCommitRecord.getWebUrl())
                .setBranche(caCommitRecord.getBranche())
                .setCommitId(caCommitRecord.getCommitId())
                .setCommitName(caCommitRecord.getCommitName())
                .setCommitMessage(caCommitRecord.getCommitMessage())
                .setCommitTitle(caCommitRecord.getCommitTitle())
                .setCommitDate(caCommitRecord.getCommitDate())
                .setCreateTime(caCommitRecord.getCreateTime())
                ;
    }

    /**
     * 获取getCaCommitRecordList
     *
     * @param caCommitRecordVOList
     * @return
     */
    public static List<CaCommitRecord> getCaCommitRecords(List<CaCommitRecordVO> caCommitRecordVOList) {
        List<CaCommitRecord> caCommitRecordList = new ArrayList<>();
        if (CollectionUtils.isEmpty(caCommitRecordVOList)) {
            return caCommitRecordList;
        }
        caCommitRecordVOList.stream().filter(caCommitRecordVO -> caCommitRecordVO != null).forEach(caCommitRecordVO -> {
            caCommitRecordList.add(getCaCommitRecord(caCommitRecordVO));
        });
        return caCommitRecordList;
    }

    /**
     * 获取getCaCommitRecordVOList
     *
     * @param caCommitRecordList
     * @return
     */
    public static List<CaCommitRecordVO> getCaCommitRecordVOs(List<CaCommitRecord> caCommitRecordList) {
        List<CaCommitRecordVO> caCommitRecordVOList = new ArrayList<>();
        if (CollectionUtils.isEmpty(caCommitRecordList)) {
            return caCommitRecordVOList;
        }
        caCommitRecordList.stream().filter(caCommitRecord -> caCommitRecord != null).forEach(caCommitRecord -> {
            caCommitRecordVOList.add(getCaCommitRecordVO(caCommitRecord));
        });
        return caCommitRecordVOList;
    }

    public static Map<String, CaUser> getCaUsersToMap(List<CaUser> users) {
        Map<String, CaUser> result = Maps.newHashMap();
        for (CaUser caUser : users) {
            result.put(caUser.getUserEmail(), caUser);
        }
        return result;
    }

}
