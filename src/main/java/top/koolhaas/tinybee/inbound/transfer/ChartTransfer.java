package top.koolhaas.tinybee.inbound.transfer;

import cn.hutool.core.date.*;
import cn.hutool.json.JSONUtil;
import top.koolhaas.tinybee.domain.CaCommitRecord;
import top.koolhaas.tinybee.domain.CaUser;
import top.koolhaas.tinybee.vo.DataTableVo;
import top.koolhaas.tinybee.vo.TrendChartVo;
import top.koolhaas.tinybee.vo.UserVo;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.glassfish.jersey.internal.guava.Sets;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @program: tinybee
 * @description:
 * @author: hackerdom
 * @created: 2021/08/21
 */
@Slf4j
public class ChartTransfer {

    public static DataTableVo getDataTableVo(String dateFrom, String dateEnd, String email, List<CaCommitRecord> caCommitRecords, List<CaUser> users, Map<String, String> holidays) {
        List<String> categories = getCategories(dateFrom, dateEnd);

        List<DataTableVo.TableVo> tableVos = Lists.newArrayList();
        Integer additionTotal = 0;
        Integer deletionTotal = 0;
        for (String category : categories) {
            Integer addition = 0;
            Integer deletion = 0;
            Date lastCommitDate = null;

            Set<String> projectNames = Sets.newHashSet();
            List<UserVo.P> projects = Lists.newArrayList();

            for (CaCommitRecord caCommitRecord : caCommitRecords) {
                Date commitDate = DateUtil.date(caCommitRecord.getCommitDate());
                if (category.equals(DateUtil.format(caCommitRecord.getCreateTime(), DatePattern.NORM_DATE_PATTERN))) {
                    addition += caCommitRecord.getAddition();
                    deletion += caCommitRecord.getDeletion();
                    if (!projectNames.contains(caCommitRecord.getProjectName())) {
                        projectNames.add(caCommitRecord.getProjectName());
                        projects.add(UserVo.P.builder()
                                .id(caCommitRecord.getProjectId())
                                .name(caCommitRecord.getProjectName())
                                .branche(caCommitRecord.getBranche())
                                .webUrl(caCommitRecord.getWebUrl())
                                .build());
                    }
                    if (null == lastCommitDate) {
                        lastCommitDate = commitDate;
                    } else {
                        if (DateUtil.between(lastCommitDate, commitDate, DateUnit.MINUTE, false) > 0) {
                            lastCommitDate = commitDate;
                        }
                    }
                }
            }

            DataTableVo.TableVo tableVo = DataTableVo.TableVo.builder()
                    .date(DateUtil.parse(category, DatePattern.NORM_DATE_PATTERN).getTime())
                    .dateStr(getCategorie(category, holidays))
                    .addition(addition)
                    .deletion(deletion)
                    .total(addition + deletion)
                    .projectNames(projectNames)
                    .projects(projects)
                    .build();

            additionTotal += addition;
            deletionTotal += deletion;
            if (null == lastCommitDate) {
                tableVo.setLastCommitDateStr("-");
            } else {
                tableVo.setLastCommitDateStr(DateUtil.format(DateUtil.offsetHour(lastCommitDate, 8), DatePattern.NORM_DATETIME_MINUTE_PATTERN));
            }
            tableVos.add(tableVo);
        }

        DataTableVo dataTableVo = DataTableVo.builder().build();

        for (CaUser caUser : users) {
            if (caUser.getOtherEmail().contains(email) || caUser.getUserEmail().contains(email)) {
                dataTableVo.setAvatarUrl(caUser.getAvatarUrl());
                dataTableVo.setName(caUser.getName());
                dataTableVo.setEmail(caUser.getUserEmail());
            }
        }

        dataTableVo.setAddition(additionTotal);
        dataTableVo.setDeletion(deletionTotal);
        dataTableVo.setTotal(additionTotal + deletionTotal);
        dataTableVo.setTableVos(tableVos.stream().sorted(Comparator.comparing(DataTableVo.TableVo::getDate).reversed()).collect(Collectors.toList()));
        return dataTableVo;
    }

    public static TrendChartVo getTrendChartVo(String dateFrom, String dateEnd, List<CaCommitRecord> caCommitRecords, Map<String, String> holidays) {
        List<String> dates = getCategories(dateFrom, dateEnd);

        List<Integer> additionData = Lists.newArrayList();
        List<Integer> deletionData = Lists.newArrayList();

        for (String date : dates) {
            Integer addition = 0;
            Integer deletion = 0;
            for (CaCommitRecord caCommitRecord : caCommitRecords) {
                if (date.equals(DateUtil.format(caCommitRecord.getCreateTime(), DatePattern.NORM_DATE_PATTERN))) {
                    addition += caCommitRecord.getAddition();
                    deletion += caCommitRecord.getDeletion();
                }
            }
            additionData.add(addition);
            deletionData.add(deletion);
        }

        TrendChartVo.Series seriesAddition = TrendChartVo.Series.builder()
                .name("新增代码")
                .data(additionData)
                .build();
        TrendChartVo.Series seriesDeletion = TrendChartVo.Series.builder()
                .name("删除代码")
                .data(deletionData)
                .build();

        List<TrendChartVo.Series> seriesList = Lists.newArrayList(seriesAddition, seriesDeletion);

        TrendChartVo trendChartVo = TrendChartVo.builder().build();

        if (caCommitRecords.size() > 0) {
            CaCommitRecord caCommitRecord = caCommitRecords.get(0);
            trendChartVo.setUsername(caCommitRecord.getUserName());
            trendChartVo.setEmail(caCommitRecord.getUserEmail());
        }

        trendChartVo.setDays(DateUtil.betweenDay(DateUtil.parse(dateFrom, DatePattern.NORM_DATE_PATTERN), DateUtil.parse(dateEnd, DatePattern.NORM_DATE_PATTERN), true));
        trendChartVo.setSeries(JSONUtil.toJsonStr(seriesList));

        List<String> categories = Lists.newArrayList();

        for (String date : dates) {
            categories.add(getCategorie(date, holidays));
        }

        trendChartVo.setCategories(JSONUtil.toJsonStr(categories));

        return trendChartVo;
    }

    private static List<String> getCategories(String dateFrom, String dateEnd) {
        List<DateTime> dateTimes = DateUtil.rangeToList(DateUtil.parse(dateFrom, DatePattern.NORM_DATE_PATTERN), DateUtil.parse(dateEnd, DatePattern.NORM_DATE_PATTERN), DateField.DAY_OF_WEEK);
        return dateTimes.stream().map(dateTime -> DateUtil.format(dateTime, DatePattern.NORM_DATE_PATTERN)).collect(Collectors.toList());
    }

    private static String getCategorie(String date, Map<String, String> holidays) {
        String holidayName = holidays.get(date);
        if (Strings.isNotEmpty(holidayName)) {
            date = (date + " (" + holidayName + ")");
        } else {
            int week = DateUtil.dayOfWeek(DateUtil.parse(date, DatePattern.NORM_DATE_PATTERN));
            if (week == 1) {
                date = (date + " (周日)");
            } else if (week == 7) {
                date = (date + " (周六)");
            }
        }
        return date;
    }


}
