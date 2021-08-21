package top.koolhaas.tinybee.infrastructure.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import top.koolhaas.tinybee.domain.CaHoliday;
import top.koolhaas.tinybee.generator.genid.BizTypeEnum;
import top.koolhaas.tinybee.generator.genid.SequenceFactory;
import top.koolhaas.tinybee.infrastructure.mapper.CaHolidayMapper;
import top.koolhaas.tinybee.infrastructure.repository.CaHolidayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 法定节假日 服务实现类
 *
 * @author hackerdom
 */
@Service
public class CaHolidayRepositoryImpl extends ServiceImpl<CaHolidayMapper, CaHoliday> implements CaHolidayRepository {

    @Autowired
    private SequenceFactory sequenceFactory;

    @Override
    public String nextIdentity() {
        return sequenceFactory.generate(BizTypeEnum.CIF_PARTY);
    }

    @Override
    public List<CaHoliday> queryByDate(List<String> dates) {
        return this.list(new QueryWrapper<CaHoliday>().in("date", dates));
    }

}
