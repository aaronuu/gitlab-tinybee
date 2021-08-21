package top.koolhaas.tinybee.service;

import top.koolhaas.tinybee.domain.CaUser;
import top.koolhaas.tinybee.infrastructure.repository.CaUserRepository;
import top.koolhaas.tinybee.vo.UserBaseInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: tinybee
 * @description:
 * @author: hackerdom
 * @created: 2021/08/21
 */
@Slf4j
@Service
public class CaUserService {

    @Autowired
    private CaUserRepository caUserRepository;

    /**
     * 根据用户名查询用户信息
     *
     * @param name
     * @return
     */
    public UserBaseInfoVo getUser(String name) {
        List<CaUser> caUsers = caUserRepository.queryByName(name);
        CaUser caUser = null;
        if(caUsers.size() > 0) {
            caUser = caUsers.get(0);
        }

        return UserBaseInfoVo.builder()
                .email(caUser.getUserEmail())
                .name(caUser.getName())
                .build();
    }

}
