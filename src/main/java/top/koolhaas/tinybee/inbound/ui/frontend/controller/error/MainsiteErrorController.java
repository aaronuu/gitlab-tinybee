package top.koolhaas.tinybee.inbound.ui.frontend.controller.error;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: tinybee
 * @description:
 * @author: hackerdom
 * @created: 2021/08/21
 */
@Controller
public class MainsiteErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        //        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        return "/gitlab/err/404.html";
    }

    @Override
    public String getErrorPath() {
        return "/gitlab/404.html";
    }
}
