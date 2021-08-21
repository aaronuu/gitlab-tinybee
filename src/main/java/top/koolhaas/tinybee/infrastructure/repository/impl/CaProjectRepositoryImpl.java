package top.koolhaas.tinybee.infrastructure.repository.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import top.koolhaas.tinybee.domain.CaProject;
import top.koolhaas.tinybee.generator.genid.BizTypeEnum;
import top.koolhaas.tinybee.generator.genid.SequenceFactory;
import top.koolhaas.tinybee.infrastructure.mapper.CaProjectMapper;
import top.koolhaas.tinybee.infrastructure.repository.CaProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 项目 服务实现类
 *
 * @author yu.zhang
 */
@Service
public class CaProjectRepositoryImpl extends ServiceImpl<CaProjectMapper, CaProject> implements CaProjectRepository {

    @Autowired
    private SequenceFactory sequenceFactory;

    @Override
    public String nextIdentity() {
        return sequenceFactory.generate(BizTypeEnum.CIF_PARTY);
    }

    @Override
    public CaProject queryById(Long id) {
        return this.queryById(id);
    }

}
