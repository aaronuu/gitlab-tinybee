package top.koolhaas.tinybee.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 法定节假日
 * </p>
 *
 * @author yu.zhang
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "CaHoliday对象", description = "法定节假日")
public class CaHoliday implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "节日id")
    private String holidayId;

    @ApiModelProperty(value = "节日名称")
    private String holidayName;

    @ApiModelProperty(value = "节日")
    private String date;

    public static CaHoliday create(String holidayId, String holidayName, String date) {
        CaHoliday caHoliday = new CaHoliday()
                .setHolidayId(holidayId)
                .setHolidayName(holidayName)
                .setDate(date);
        return caHoliday;
    }

}
