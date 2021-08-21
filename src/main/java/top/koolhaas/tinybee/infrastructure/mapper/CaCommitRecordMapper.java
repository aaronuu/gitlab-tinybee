package top.koolhaas.tinybee.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.koolhaas.tinybee.domain.CaCommitRecord;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 用户每天提交记录 Mapper
 *
 * @author hackerdom
 */
public interface CaCommitRecordMapper extends BaseMapper<CaCommitRecord> {

    @Select("SELECT DISTINCT user_email FROM ca_commit_record WHERE project_id = #{projectId}")
    List<String> queryUserEmailByProjectId(@Param("projectId") Long projectId);

}
