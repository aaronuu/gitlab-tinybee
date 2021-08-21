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
 * 用户
 * </p>
 *
 * @author hackerdom
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "CaUser对象", description = "用户")
public class CaUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "用户名")
    private String name;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "头像地址")
    private String avatarUrl;

    @ApiModelProperty(value = "用户邮箱")
    private String userEmail;

    @ApiModelProperty(value = "其他邮箱")
    private String otherEmail;

    @ApiModelProperty(value = "职位")
    private String position;

    @ApiModelProperty(value = "是否启用")
    private Boolean enable;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateDate;

    public static CaUser create(String userId, String name, String userName, String avatarUrl, String userEmail, String otherEmail, Boolean enable) {
        CaUser caUser = new CaUser()
                .setUserId(userId)
                .setName(name)
                .setUserName(userName)
                .setUserEmail(userEmail)
                .setAvatarUrl(avatarUrl)
                .setEnable(enable)
                .setOtherEmail(otherEmail)
                .setCreateTime(LocalDateTime.now())
                .setUpdateDate(LocalDateTime.now());
        return caUser;
    }

    public void updateAvatarUrl(String avatarUrl) {
        this.setAvatarUrl(avatarUrl);
        this.setUpdateDate(LocalDateTime.now());
    }

    public void updateEnable(Boolean enable) {
        this.setEnable(enable);
        this.setUpdateDate(LocalDateTime.now());
    }

}
