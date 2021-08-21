package top.koolhaas.tinybee.infrastructure.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import top.koolhaas.tinybee.domain.CaProject;

/**
 * 项目 服务类
 *
 * @author hackerdom
 */
public interface CaProjectRepository extends IService<CaProject> {
    String nextIdentity();

    CaProject queryById(Long id);
}
