package top.koolhaas.tinybee.inbound.ui.frontend.controller;

import top.koolhaas.tinybee.core.response.ResponseData;
import top.koolhaas.tinybee.service.CaUserService;
import top.koolhaas.tinybee.vo.UserBaseInfoVo;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @program: tinybee
 * @description:
 * @author: hackerdom
 * @created: 2021/08/21
 */
@Slf4j
@ApiIgnore
@RestController
public class UserController {

    @Autowired
    private CaUserService caUserService;

    @GetMapping("/user/{name}/base")
    public ResponseData<UserBaseInfoVo> chart(@ApiParam(value = "用户名") @PathVariable String name) {
        return ResponseData.success(caUserService.getUser(name));
    }

}
