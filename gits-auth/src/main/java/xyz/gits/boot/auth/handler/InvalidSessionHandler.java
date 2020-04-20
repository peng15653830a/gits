package xyz.gits.boot.auth.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.stereotype.Component;
import xyz.gits.boot.common.response.ResponseCode;
import xyz.gits.boot.common.response.RestResponse;
import xyz.gits.boot.common.utils.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用户登录后，当达到超时时间后，自动将用户退出登录
 *
 * @author songyinyin
 * @date 2020/2/25 下午 10:45
 */
@Slf4j
@Component
public class InvalidSessionHandler implements InvalidSessionStrategy {


    @Override
    public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        log.info("用户登录超时，访问[{}]失败", request.getRequestURI());
        ServletUtils.render(request, response, RestResponse.fail(ResponseCode.USER_LOGIN_TIMEOUT));
    }
}
