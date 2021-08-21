package top.koolhaas.tinybee.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 项目
 * </p>
 *
 * @author yu.zhang
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "CaProject对象", description = "项目")
public class CaProject implements Serializable {

    private static final long serialVersionUID = 1L;

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

    private Integer forksCount;

    @ApiModelProperty(value = "http")
    private String httpUrlToRepo;

    @ApiModelProperty(value = "namespace")
    private String pathWithNamespace;

    @ApiModelProperty(value = "ssh")
    private String sshUrlToRepo;

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

    public static CaProject create(long id, String projectId, String name, String description, String defaultBranch, Integer forksCount,
                                   String httpUrlToRepo, String pathWithNamespace, String sshUrlToRepo,
                                   Integer starCount, String webUrl, Boolean wikiEnabled,
                                   LocalDateTime lastActivityAt) {
        return new CaProject()
                .setId(id)
                .setProjectId(projectId)
                .setName(name)
                .setDescription(description)
                .setDefaultBranch(defaultBranch)
                .setForksCount(forksCount)
                .setHttpUrlToRepo(httpUrlToRepo)
                .setPathWithNamespace(pathWithNamespace)
                .setSshUrlToRepo(sshUrlToRepo)
                .setStarCount(starCount)
                .setWebUrl(webUrl)
                .setWikiEnabled(wikiEnabled)
                .setLastActivityAt(lastActivityAt);
    }
}
