package top.koolhaas.tinybee.inbound.ui.frontend.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;
import top.koolhaas.tinybee.service.CaCommitRecordService;

import java.io.IOException;

/**
 * @program: tinybee
 * @description:
 * @author: hackerdom
 * @created: 2021/08/21
 */
@Slf4j
@ApiIgnore
@RestController
public class AvatarController {

    @Autowired
    private CaCommitRecordService caCommitRecordService;

    @GetMapping("/avatar/{userId}")
    public ResponseEntity<Resource> avatar(@PathVariable String userId) throws IOException {
        return caCommitRecordService.getAvatar(userId);
    }
}
