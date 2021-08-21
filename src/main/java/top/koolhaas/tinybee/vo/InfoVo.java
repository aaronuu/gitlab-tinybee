package top.koolhaas.tinybee.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

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
public class InfoVo implements Serializable {

    @ApiModelProperty(value = "用户信息列表")
    private List<UserVo> userVos;

    @ApiModelProperty("增加行")
    private String addition;

    @ApiModelProperty("删除行")
    private String deletion;

    @ApiModelProperty("总计")
    private String total;

}
