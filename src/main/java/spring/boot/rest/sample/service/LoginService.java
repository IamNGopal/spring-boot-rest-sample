package spring.boot.rest.sample.service;

import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import spring.boot.rest.sample.param.LoginParam;
import spring.boot.rest.sample.param.RefreshParam;

/**
 * Service for register.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 23/01/2017
 * @since JDK1.8
 */
public interface LoginService {

  ResponseEntity login(LoginParam param, HttpServletRequest request) throws Exception;

  ResponseEntity refresh(RefreshParam param, HttpServletRequest request) throws Exception;
}
