package top.koolhaas.tinybee.inbound.transfer;

import top.koolhaas.tinybee.core.dto.CaUserVO;
import top.koolhaas.tinybee.domain.CaUser;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class CaUserTransfer {

    /**
     * 获取CaUser
     *
     * @param caUserVO
     * @return
     */
    public static CaUser getCaUser(CaUserVO caUserVO) {
        if (caUserVO == null) {
            return null;
        }
        return new CaUser()
                .setId(caUserVO.getId())
                .setUserId(caUserVO.getUserId())
                .setName(caUserVO.getName())
                .setUserName(caUserVO.getUserName())
                .setAvatarUrl(caUserVO.getAvatarUrl())
                .setUserEmail(caUserVO.getUserEmail())
                .setOtherEmail(caUserVO.getOtherEmail())
                .setCreateTime(caUserVO.getCreateTime())
                .setUpdateDate(caUserVO.getUpdateDate())
                ;
    }


    /**
     * 获取CaUserVO
     *
     * @param caUser
     * @return
     */
    public static CaUserVO getCaUserVO(CaUser caUser) {
        if (caUser == null) {
            return null;
        }
        return new CaUserVO()
                .setId(caUser.getId())
                .setUserId(caUser.getUserId())
                .setName(caUser.getName())
                .setUserName(caUser.getUserName())
                .setAvatarUrl(caUser.getAvatarUrl())
                .setUserEmail(caUser.getUserEmail())
                .setOtherEmail(caUser.getOtherEmail())
                .setCreateTime(caUser.getCreateTime())
                .setUpdateDate(caUser.getUpdateDate())
                ;
    }

    /**
     * 获取getCaUserList
     *
     * @param caUserVOList
     * @return
     */
    public static List<CaUser> getCaUsers(List<CaUserVO> caUserVOList) {
        List<CaUser> caUserList = new ArrayList<>();
        if (CollectionUtils.isEmpty(caUserVOList)) {
            return caUserList;
        }
        caUserVOList.stream().filter(caUserVO -> caUserVO != null).forEach(caUserVO -> {
            caUserList.add(getCaUser(caUserVO));
        });
        return caUserList;
    }


    /**
     * 获取getCaUserVOList
     *
     * @param caUserList
     * @return
     */
    public static List<CaUserVO> getCaUserVOs(List<CaUser> caUserList) {
        List<CaUserVO> caUserVOList = new ArrayList<>();
        if (CollectionUtils.isEmpty(caUserList)) {
            return caUserVOList;
        }
        caUserList.stream().filter(caUser -> caUser != null).forEach(caUser -> {
            caUserVOList.add(getCaUserVO(caUser));
        });
        return caUserVOList;
    }
}
