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
public class CaProjectVO {

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

    @ApiModelProperty(value = "fork数量")
    private Integer forksCount;

    @ApiModelProperty(value = "页面地址")
    private Integer starCount;

    @ApiModelProperty(value = "web地址")
    private String webUrl;

    @ApiModelProperty(value = "是否启用wiki")
    private Boolean wikiEnabled;

    private LocalDateTime lastActivityAt;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateDate;
}
