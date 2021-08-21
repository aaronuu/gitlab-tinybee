package top.koolhaas.tinybee.service;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import top.koolhaas.tinybee.domain.CaUser;
import top.koolhaas.tinybee.inbound.transfer.CaCommitRecordTransfer;
import top.koolhaas.tinybee.infrastructure.repository.CaCommitRecordRepository;
import top.koolhaas.tinybee.infrastructure.repository.CaUserRepository;
import top.koolhaas.tinybee.vo.InfoVo;
import top.koolhaas.tinybee.vo.UserVo;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @program: tinybee
 * @description:
 * @author: hackerdom
 * @created: 2021/08/21
 */
@Slf4j
@Service
public class CaCommitRecordService {

    @Autowired
    private CaCommitRecordRepository caCommitRecordRepository;

    @Autowired
    private CaUserRepository caUserRepository;

    private String ROOT_PATH = new ApplicationHome(getClass()).getSource().getParentFile().toString();

    /**
     * 获取自定义头像
     *
     * @param userId
     * @return
     */
    public ResponseEntity<Resource> getAvatar(String userId) {
        String fileName = "avatar.png";
        String path = ROOT_PATH + "/avatar/" + userId + "/avatar.png";
        FileSystemResource resource = new FileSystemResource(path);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.PRAGMA, "public");
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + fileName);
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("image/png"))
                .body(resource);
    }

    /**
     * 获取启用用户信息
     *
     * @return
     */
    public List<CaUser> getCaUsers() {
        return caUserRepository.queryByEnable(true);
    }

    /**
     * 将获取到的启用用户信息转换为Map
     *
     * @return
     */
    public Map<String, CaUser> getCaUsersToMap() {
        Map<String, CaUser> result = Maps.newHashMap();
        List<CaUser> users = caUserRepository.queryByEnable(true);
        for (CaUser caUser : users) {
            result.put(caUser.getUserEmail(), caUser);
        }
        return result;
    }

    /**
     * 首页提交记录列表
     *
     * @param dateFrom
     * @param dateEnd
     * @param users
     * @return
     */
    public List<UserVo> getCommitRecord(String dateFrom, String dateEnd, List<CaUser> users) {
        return CaCommitRecordTransfer.getUserVos(caCommitRecordRepository.queryByCreateTime(DateUtil.parseLocalDateTime(dateFrom, DatePattern.NORM_DATE_PATTERN), DateUtil.parseLocalDateTime(dateEnd, DatePattern.NORM_DATE_PATTERN)), users);
    }

    /**
     * 获取首页数据
     *
     * @param dateFrom
     * @param dateEnd
     * @param users
     * @return
     */
//    @Cached(name = "getInfoVo1", expire = 60, cacheType = CacheType.LOCAL)
    public InfoVo getInfoVo(String dateFrom, String dateEnd, List<CaUser> users) {
        return CaCommitRecordTransfer.getInfoVo(caCommitRecordRepository.queryByCreateTime(DateUtil.parseLocalDateTime(dateFrom, DatePattern.NORM_DATE_PATTERN), DateUtil.parseLocalDateTime(dateEnd, DatePattern.NORM_DATE_PATTERN)), users);
    }

    /**
     * 单独封装用户信息
     *
     * @return
     */
//    @Cached(name = "getInfoVo2", expire = 60, cacheType = CacheType.LOCAL)
    public InfoVo getInfoVo() {
        return CaCommitRecordTransfer.getInfoVo(getCaUsers());
    }

}
