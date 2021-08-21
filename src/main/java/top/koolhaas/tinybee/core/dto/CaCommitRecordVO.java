package top.koolhaas.tinybee.core.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CaCommitRecordVO {

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "提交记录id")
    private String commitRecordId;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "用户邮箱")
    private String userEmail;

    @ApiModelProperty(value = "新增代码")
    private Integer addition;

    @ApiModelProperty(value = "删除代码")
    private Integer deletion;

    @ApiModelProperty(value = "总计")
    private Integer total;

    @ApiModelProperty(value = "项目id")
    private String projectId;

    @ApiModelProperty(value = "项目名称")
    private String projectName;

    @ApiModelProperty(value = "页面地址")
    private String webUrl;

    @ApiModelProperty(value = "分支名称")
    private String branche;

    @ApiModelProperty(value = "提交Id")
    private String commitId;

    @ApiModelProperty(value = "提交名称")
    private String commitName;

    @ApiModelProperty(value = "提交信息")
    private String commitMessage;

    @ApiModelProperty(value = "提交头信息")
    private String commitTitle;

    @ApiModelProperty(value = "提交时间")
    private LocalDateTime commitDate;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;
}
