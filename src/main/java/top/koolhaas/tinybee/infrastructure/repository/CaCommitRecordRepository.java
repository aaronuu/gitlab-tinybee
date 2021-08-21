package top.koolhaas.tinybee.infrastructure.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import top.koolhaas.tinybee.domain.CaCommitRecord;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户每天提交记录 服务类
 *
 * @author yu.zhang
 */
public interface CaCommitRecordRepository extends IService<CaCommitRecord> {

    String nextIdentity();

    List<CaCommitRecord> queryByCreateTime(LocalDateTime dateFrom, LocalDateTime dateEnd);

    List<CaCommitRecord> queryByCreateTime(LocalDateTime dateFrom, LocalDateTime dateEnd, String email);

}
