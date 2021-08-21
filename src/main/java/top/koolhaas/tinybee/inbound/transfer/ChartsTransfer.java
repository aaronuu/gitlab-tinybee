package top.koolhaas.tinybee.inbound.transfer;

import top.koolhaas.tinybee.vo.ChartsVo;
import top.koolhaas.tinybee.vo.UserVo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

/**
 * @program: tinybee
 * @description:
 * @author: hackerdom
 * @created: 2021/08/21
 */
public class ChartsTransfer {

    public static ChartsVo getLoginRespVo(List<UserVo> userVoList) {
        ChartsVo.Label label = ChartsVo.Label.builder().show(true).position("insideRight").build();

        List<String> legendData = Lists.newArrayList("新增", "删除");

        Map<String, Boolean> legendSelected = Maps.newHashMap();
        legendSelected.put(legendData.get(0), true);
        legendSelected.put(legendData.get(1), false);

        List<String> yAxisData = Lists.newArrayList();

        List<Integer> additionSeriesData = Lists.newArrayList();
        List<Integer> deletionSeriesData = Lists.newArrayList();

        for (UserVo userVo : userVoList) {
            yAxisData.add(userVo.getName());
            additionSeriesData.add(userVo.getAddition());
            deletionSeriesData.add(userVo.getDeletion());
        }

        ChartsVo.Series additionSeries = ChartsVo.Series.builder()
                .name("新增")
                .type("bar")
                .stack("总量")
                .label(label)
                .data(additionSeriesData)
                .build();

        ChartsVo.Series deletionSeries = ChartsVo.Series.builder()
                .name("删除")
                .type("bar")
                .stack("总量")
                .label(label)
                .data(deletionSeriesData)
                .build();

        List<ChartsVo.Series> series = Lists.newArrayList();
        series.add(additionSeries);
        series.add(deletionSeries);
        return ChartsVo.builder()
                .legendData(legendData)
                .legendSelected(legendSelected)
                .yAxisData(yAxisData)
                .series(series)
                .build();
    }
}
