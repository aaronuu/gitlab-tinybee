package top.koolhaas.tinybee.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @program: tinybee
 * @description:
 * @author: hackerdom
 * @created: 2021/08/21
 */
@ApiModel
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserVo implements Serializable {

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "职位")
    private String position;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "头像")
    private String avatarUrl;

    @ApiModelProperty("增加行")
    private Integer addition;

    @ApiModelProperty("删除行")
    private Integer deletion;

    @ApiModelProperty("总计")
    private Integer total;

    @ApiModelProperty("最后提交时间")
    private Date lastCommitDate;

    @ApiModelProperty("最后提交时间")
    private String lastCommitDateStr;

    @ApiModelProperty("项目名称")
    private Set<String> projectNames;

    @ApiModelProperty("项目")
    private List<P> projects;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class P implements Serializable {

        @ApiModelProperty(value = "项目id")
        private String id;

        @ApiModelProperty(value = "项目名")
        private String name;

        @ApiModelProperty(value = "分支")
        private String branche;

        @ApiModelProperty(value = "项目地址")
        private String webUrl;
    }
}
