package top.koolhaas.tinybee.infrastructure.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import top.koolhaas.tinybee.domain.CaCommitRecord;
import top.koolhaas.tinybee.generator.genid.BizTypeEnum;
import top.koolhaas.tinybee.generator.genid.SequenceFactory;
import top.koolhaas.tinybee.infrastructure.mapper.CaCommitRecordMapper;
import top.koolhaas.tinybee.infrastructure.repository.CaCommitRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户每天提交记录 服务实现类
 *
 * @author hackerdom
 */
@Service
public class CaCommitRecordRepositoryImpl extends ServiceImpl<CaCommitRecordMapper, CaCommitRecord> implements CaCommitRecordRepository {

    @Autowired
    private SequenceFactory sequenceFactory;

    @Override
    public String nextIdentity() {
        return sequenceFactory.generate(BizTypeEnum.CIF_PARTY);
    }

    @Override
    public List<CaCommitRecord> queryByCreateTime(LocalDateTime dateFrom, LocalDateTime dateEnd) {
        return this.list(new QueryWrapper<CaCommitRecord>().ge("create_time", dateFrom).le("create_time", dateEnd));
    }

    @Override
    public List<CaCommitRecord> queryByCreateTime(LocalDateTime dateFrom, LocalDateTime dateEnd, String email) {
        return this.list(new QueryWrapper<CaCommitRecord>().ge("create_time", dateFrom).le("create_time", dateEnd).eq("user_email", email));
    }

}
