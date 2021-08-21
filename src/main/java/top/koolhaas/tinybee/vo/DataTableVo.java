package top.koolhaas.tinybee.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class DataTableVo {

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "头像")
    private String avatarUrl;

    private List<TableVo> tableVos;

    @ApiModelProperty("增加行")
    private Integer addition;

    @ApiModelProperty("删除行")
    private Integer deletion;

    @ApiModelProperty("总计")
    private Integer total;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TableVo {

        @ApiModelProperty("日期")
        private String dateStr;

        @ApiModelProperty("日期")
        private Long date;

        @ApiModelProperty("增加行")
        private Integer addition;

        @ApiModelProperty("删除行")
        private Integer deletion;

        @ApiModelProperty("总计")
        private Integer total;

        @ApiModelProperty("最后提交时间")
        private String lastCommitDateStr;

        @ApiModelProperty("项目名称")
        private Set<String> projectNames;

        @ApiModelProperty("项目")
        private List<UserVo.P> projects;
    }
}
