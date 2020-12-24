package spring.boot.rest.sample.controller.open;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import spring.boot.rest.sample.component.ResultHelper;
import spring.boot.rest.sample.constant.ResourcePath;
import spring.boot.rest.sample.enums.ErrorType;
import spring.boot.rest.sample.param.LoginParam;
import spring.boot.rest.sample.service.LoginService;
import spring.boot.rest.sample.tools.Assert;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api("Login")
@RestController
@RequestMapping(ResourcePath.API + ResourcePath.V1 + ResourcePath.OPEN + ResourcePath.LOGIN)
public class LoginController {

  @RequestMapping(method = RequestMethod.POST)
  @ApiOperation(value = "Login", httpMethod = "POST", response = OAuth2AccessToken.class)
  @ApiImplicitParams({
      @ApiImplicitParam(name = "Authorization", value = "token", paramType = "header", dataType = "string", required = true),
  })
  public ResponseEntity login(HttpServletRequest request, @RequestBody LoginParam param) {
    try {
      return loginService.login(param, request);
    } catch (Exception e) {
      // Return unknown error and log the exception.
      return resultHelper.errorResp(logger, e, ErrorType.UNKNOWN, e.getMessage(),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
  private final LoginService loginService;
  private final ResultHelper resultHelper;

  @Autowired public LoginController(LoginService loginService, ResultHelper resultHelper) {
    Assert.defaultNotNull(loginService);
    Assert.defaultNotNull(resultHelper);
    this.loginService = loginService;
    this.resultHelper = resultHelper;
  }
}

