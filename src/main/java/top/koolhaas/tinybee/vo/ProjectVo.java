package top.koolhaas.tinybee.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @program: tinybee
 * @description:
 * @author: hackerdom
 * @created: 2021/08/21
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectVo {

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "项目id")
    private String projectId;

    @ApiModelProperty(value = "项目名称")
    private String name;

    @ApiModelProperty(value = "项目描述")
    private String description;

    @ApiModelProperty(value = "默认分支")
    private String defaultBranch;

    @ApiModelProperty(value = "web地址")
    private String webUrl;

    @ApiModelProperty(value = "是否启用wiki")
    private Boolean wikiEnabled;

    private Date lastActivityAt;
    private String lastActivityAtStr;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    private String createTimeStr;

    @ApiModelProperty(value = "更新时间")
    private Date updateDate;
    private String updateDateStr;

    private List<U> users;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class U {

        @ApiModelProperty(value = "用户名")
        private String username;

        @ApiModelProperty(value = "邮箱")
        private String email;

        @ApiModelProperty(value = "头像")
        private String avatarUrl;
    }
}
