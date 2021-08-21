package top.koolhaas.tinybee.domain;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 用户每天提交记录
 * </p>
 *
 * @author yu.zhang
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "CaCommitRecord对象", description = "用户每天提交记录")
public class CaCommitRecord implements Serializable {

    private static final long serialVersionUID = 1L;

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

    public static CaCommitRecord create(String commitRecordId, String userName, String userEmail, Integer addition, Integer deletion,
                                        Integer total, String projectId, String projectName, String webUrl, String branche,
                                        String commitId, LocalDateTime commitDate, String commitName,
                                        String commitMessage, String commitTitle, LocalDateTime createTime, List<CaUser> caUsers) {
        CaCommitRecord caCommitRecord = new CaCommitRecord()
                .setCommitRecordId(commitRecordId)
                .setUserName(userName)
                .setUserEmail(userEmail)
                .setAddition(addition)
                .setDeletion(deletion)
                .setTotal(total)
                .setProjectId(projectId)
                .setProjectName(projectName)
                .setWebUrl(webUrl)
                .setBranche(branche)
                .setCommitId(commitId)
                .setCommitDate(commitDate)
                .setCommitName(commitName)
                .setCommitMessage(commitMessage)
                .setCommitTitle(commitTitle)
                .setCreateTime(createTime);
        for (CaUser caUser : caUsers) {
            String userOtherEmail = caUser.getOtherEmail();
            if (StrUtil.isNotEmpty(userOtherEmail) && userOtherEmail.contains(caCommitRecord.getUserEmail()) || userEmail.equals(caUser.getUserEmail())) {
                caCommitRecord.setUserEmail(caUser.getUserEmail());
                caCommitRecord.setUserName(caUser.getName());
            }
        }
        return caCommitRecord;
    }

}
