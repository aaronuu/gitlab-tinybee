package top.koolhaas.tinybee.infrastructure.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import top.koolhaas.tinybee.domain.CaHoliday;

import java.util.List;

/**
 * 法定节假日 服务类
 *
 * @author yu.zhang
 */
public interface CaHolidayRepository extends IService<CaHoliday> {

    String nextIdentity();

    List<CaHoliday> queryByDate(List<String> dates);

}
