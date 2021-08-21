package top.koolhaas.tinybee.inbound.transfer;

import top.koolhaas.tinybee.core.dto.CaHolidayVO;
import top.koolhaas.tinybee.domain.CaHoliday;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class CaHolidayTransfer {

    /**
     * 获取CaHoliday
     *
     * @param caHolidayVO
     * @return
     */
    public static CaHoliday getCaHoliday(CaHolidayVO caHolidayVO) {
        if (caHolidayVO == null) {
            return null;
        }
        return new CaHoliday()
                .setId(caHolidayVO.getId())
                .setHolidayId(caHolidayVO.getHolidayId())
                .setHolidayName(caHolidayVO.getHolidayName())
                .setDate(caHolidayVO.getDate())
                ;
    }


    /**
     * 获取CaHolidayVO
     *
     * @param caHoliday
     * @return
     */
    public static CaHolidayVO getCaHolidayVO(CaHoliday caHoliday) {
        if (caHoliday == null) {
            return null;
        }
        return new CaHolidayVO()
                .setId(caHoliday.getId())
                .setHolidayId(caHoliday.getHolidayId())
                .setHolidayName(caHoliday.getHolidayName())
                .setDate(caHoliday.getDate())
                ;
    }

    /**
     * 获取getCaHolidayList
     *
     * @param caHolidayVOList
     * @return
     */
    public static List<CaHoliday> getCaHolidays(List<CaHolidayVO> caHolidayVOList) {
        List<CaHoliday> caHolidayList = new ArrayList<>();
        if (CollectionUtils.isEmpty(caHolidayVOList)) {
            return caHolidayList;
        }
        caHolidayVOList.stream().filter(caHolidayVO -> caHolidayVO != null).forEach(caHolidayVO -> {
            caHolidayList.add(getCaHoliday(caHolidayVO));
        });
        return caHolidayList;
    }


    /**
     * 获取getCaHolidayVOList
     *
     * @param caHolidayList
     * @return
     */
    public static List<CaHolidayVO> getCaHolidayVOs(List<CaHoliday> caHolidayList) {
        List<CaHolidayVO> caHolidayVOList = new ArrayList<>();
        if (CollectionUtils.isEmpty(caHolidayList)) {
            return caHolidayVOList;
        }
        caHolidayList.stream().filter(caHoliday -> caHoliday != null).forEach(caHoliday -> {
            caHolidayVOList.add(getCaHolidayVO(caHoliday));
        });
        return caHolidayVOList;
    }
}
