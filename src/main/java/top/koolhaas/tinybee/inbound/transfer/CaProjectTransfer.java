package top.koolhaas.tinybee.inbound.transfer;

import top.koolhaas.tinybee.core.dto.CaProjectVO;
import top.koolhaas.tinybee.domain.CaProject;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class CaProjectTransfer {

    /**
     * 获取CaProject
     *
     * @param caProjectVO
     * @return
     */
    public static CaProject getCaProject(CaProjectVO caProjectVO) {
        if (caProjectVO == null) {
            return null;
        }
        return new CaProject()
                .setId(caProjectVO.getId())
                .setProjectId(caProjectVO.getProjectId())
                .setName(caProjectVO.getName())
                .setDescription(caProjectVO.getDescription())
                .setDefaultBranch(caProjectVO.getDefaultBranch())
                .setForksCount(caProjectVO.getForksCount())
                .setStarCount(caProjectVO.getStarCount())
                .setWebUrl(caProjectVO.getWebUrl())
                .setWikiEnabled(caProjectVO.getWikiEnabled())
                .setLastActivityAt(caProjectVO.getLastActivityAt())
                .setCreateTime(caProjectVO.getCreateTime())
                .setUpdateDate(caProjectVO.getUpdateDate())
                ;
    }

    /**
     * 获取CaProjectVO
     *
     * @param caProject
     * @return
     */
    public static CaProjectVO getCaProjectVO(CaProject caProject) {
        if (caProject == null) {
            return null;
        }
        return new CaProjectVO()
                .setId(caProject.getId())
                .setProjectId(caProject.getProjectId())
                .setName(caProject.getName())
                .setDescription(caProject.getDescription())
                .setDefaultBranch(caProject.getDefaultBranch())
                .setForksCount(caProject.getForksCount())
                .setStarCount(caProject.getStarCount())
                .setWebUrl(caProject.getWebUrl())
                .setWikiEnabled(caProject.getWikiEnabled())
                .setLastActivityAt(caProject.getLastActivityAt())
                .setCreateTime(caProject.getCreateTime())
                .setUpdateDate(caProject.getUpdateDate())
                ;
    }

    /**
     * 获取getCaProjectList
     *
     * @param caProjectVOList
     * @return
     */
    public static List<CaProject> getCaProjects(List<CaProjectVO> caProjectVOList) {
        List<CaProject> caProjectList = new ArrayList<>();
        if (CollectionUtils.isEmpty(caProjectVOList)) {
            return caProjectList;
        }
        caProjectVOList.stream().filter(caProjectVO -> caProjectVO != null).forEach(caProjectVO -> {
            caProjectList.add(getCaProject(caProjectVO));
        });
        return caProjectList;
    }

    /**
     * 获取getCaProjectVOList
     *
     * @param caProjectList
     * @return
     */
    public static List<CaProjectVO> getCaProjectVOs(List<CaProject> caProjectList) {
        List<CaProjectVO> caProjectVOList = new ArrayList<>();
        if (CollectionUtils.isEmpty(caProjectList)) {
            return caProjectVOList;
        }
        caProjectList.stream().filter(caProject -> caProject != null).forEach(caProject -> {
            caProjectVOList.add(getCaProjectVO(caProject));
        });
        return caProjectVOList;
    }
}
