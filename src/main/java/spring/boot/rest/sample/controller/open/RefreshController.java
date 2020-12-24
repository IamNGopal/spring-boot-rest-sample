package spring.boot.rest.sample.controller.open;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import spring.boot.rest.sample.component.ResultHelper;
import spring.boot.rest.sample.constant.ResourcePath;
import spring.boot.rest.sample.enums.ErrorType;
import spring.boot.rest.sample.exception.IllegalTokenTypeException;
import spring.boot.rest.sample.param.RefreshParam;
import spring.boot.rest.sample.service.LoginService;
import spring.boot.rest.sample.tools.Assert;
import spring.boot.rest.sample.vo.ErrorVO;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 15/02/2017
 * @since JDK1.8
 */
@Api("refresh token")
@RestController
@RequestMapping(ResourcePath.API + ResourcePath.V1 + ResourcePath.OPEN + ResourcePath.REFRESH)
public class RefreshController {

  @RequestMapping(method = RequestMethod.PUT)
  @ApiOperation(value = "refresh token", httpMethod = "POST", response = OAuth2AccessToken.class)
  @ApiImplicitParam(name = "Authorization", value = "token", paramType = "header", dataType = "string", required = true)
  public ResponseEntity refresh(@RequestBody RefreshParam param, @ApiIgnore HttpServletRequest request) {
    try {
      return loginService.refresh(param, request);
    } catch (IllegalTokenTypeException e) {
      return new ResponseEntity<>(new ErrorVO(e.getErrorType().name(), e.getErrorType().description()), HttpStatus.UNAUTHORIZED);
    } catch (Exception e) {
      // Return unknown error and log the exception.
      return resultHelper.errorResp(logger, e, ErrorType.UNKNOWN, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  private static final Logger logger = LoggerFactory.getLogger(RefreshController.class);
  private final LoginService loginService;
  private final ResultHelper resultHelper;

  public RefreshController(LoginService loginService, ResultHelper resultHelper) {
    Assert.defaultNotNull(loginService);
    Assert.defaultNotNull(resultHelper);
    this.loginService = loginService;
    this.resultHelper = resultHelper;
  }
}
