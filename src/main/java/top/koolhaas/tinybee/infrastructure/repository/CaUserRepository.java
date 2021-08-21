package top.koolhaas.tinybee.infrastructure.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import top.koolhaas.tinybee.domain.CaUser;

import java.util.List;

/**
 * 用户 服务类
 *
 * @author yu.zhang
 */
public interface CaUserRepository extends IService<CaUser> {
    String nextIdentity();

    CaUser queryByUserId(String userId);

    List<CaUser> queryByEnable(Boolean enable);

    List<CaUser> queryByName(String name);
}
