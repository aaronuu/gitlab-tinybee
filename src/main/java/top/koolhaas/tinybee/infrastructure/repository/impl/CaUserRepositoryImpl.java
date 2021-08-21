package top.koolhaas.tinybee.infrastructure.repository.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import top.koolhaas.tinybee.domain.CaUser;
import top.koolhaas.tinybee.generator.genid.BizTypeEnum;
import top.koolhaas.tinybee.generator.genid.SequenceFactory;
import top.koolhaas.tinybee.infrastructure.mapper.CaUserMapper;
import top.koolhaas.tinybee.infrastructure.repository.CaUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户 服务实现类
 *
 * @author yu.zhang
 */
@Service
public class CaUserRepositoryImpl extends ServiceImpl<CaUserMapper, CaUser> implements CaUserRepository {

    @Autowired
    private SequenceFactory sequenceFactory;

    @Override
    public String nextIdentity() {
        return sequenceFactory.generate(BizTypeEnum.CIF_PARTY);
    }

    @Override
    public CaUser queryByUserId(String userId) {
        return this.getOne(new QueryWrapper<CaUser>().eq("user_id", userId));
    }

    @Override
    public List<CaUser> queryByEnable(Boolean enable) {
        return this.list(new QueryWrapper<CaUser>().eq("enable", enable));
    }

    @Override
    public List<CaUser> queryByName(String name) {
        return this.list(new QueryWrapper<CaUser>().like("name", "%" + name + "%"));
    }

}
