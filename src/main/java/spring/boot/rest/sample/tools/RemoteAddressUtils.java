package spring.boot.rest.sample.tools;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;

import spring.boot.rest.sample.servlet.RequestWrapper;

/**
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 27/12/2016
 * @since JDK1.8
 */
public class RemoteAddressUtils {

  public static String getRealIp(HttpServletRequest request) {
    String ip = request.getHeader("X-Forwarded-For");
    if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
      int index = ip.indexOf(",");
      if (index != -1) {
        return ip.substring(0, index);
      } else {
        return ip;
      }
    }
    ip = request.getHeader("X-Real-IP");
    if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
      return ip;
    }
    return request.getRemoteAddr();
  }

  public static String getRealIp(RequestWrapper request) {
    String ip = request.getHeader("X-Forwarded-For");
    if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
      int index = ip.indexOf(",");
      if (index != -1) {
        return ip.substring(0, index);
      } else {
        return ip;
      }
    }
    ip = request.getHeader("X-Real-IP");
    if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
      return ip;
    }
    return request.getRemoteAddr();
  }
}
