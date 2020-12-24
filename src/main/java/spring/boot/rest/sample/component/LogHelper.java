package spring.boot.rest.sample.component;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import spring.boot.rest.sample.domain.LogDomain;
import spring.boot.rest.sample.po.Log;
import spring.boot.rest.sample.tools.RemoteAddressUtils;
import spring.boot.rest.sample.tools.SpringSecurityUtils;

/**
 * Log users' operations.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 11/6/15
 * @since JDK1.8
 */
@Component
public class LogHelper {

	public void log(HttpServletRequest request) {
		String ip = RemoteAddressUtils.getRealIp(request);
		Log log = Log.builder()
				.clientId(SpringSecurityUtils.getCurrentClientId())
				.createdDate(new Date())
				.ip(StringUtils.isBlank(ip) ? "0.0.0.0.0.0.0.0:1" : ip)
				.type(0)
				.userId(0)
				.username(SpringSecurityUtils.getCurrentUsername())
				.createdAt(new Date().getTime())
				.method(HttpMethod.GET)
				.path(request.getRequestURI().substring(request.getContextPath().length()))
				.usr(SpringSecurityUtils.getCurrentUsername())
				.build();
		logDomain.create(log);
	}

	public void log(HttpMethod method, String usr, String ip, String clientId, String path) {
		Log log = Log.builder()
				.clientId(clientId)
				.createdDate(new Date())
				.ip(StringUtils.isBlank(ip) ? "0.0.0.0.0.0.0.0:1" : ip)
				.type(0)
				.userId(0)
				.username(SpringSecurityUtils.getCurrentUsername())
				.createdAt(new Date().getTime())
				.method(HttpMethod.GET)
				.path(path)
				.usr(SpringSecurityUtils.getCurrentUsername())
				.build();
		logDomain.create(log);
	}

	public void log(HttpMethod method) {
		String ip = SpringSecurityUtils.getCurrentUserIp();
		Log log = Log.builder()
				.clientId(SpringSecurityUtils.getCurrentClientId())
				.createdDate(new Date())
				.ip(StringUtils.isBlank(ip) ? "0.0.0.0.0.0.0.0:1" : ip)
				.type(0)
				.userId(0)
				.username(SpringSecurityUtils.getCurrentUsername())
				.createdAt(new Date().getTime())
				.method(HttpMethod.GET)
				.path("")
				.usr(SpringSecurityUtils.getCurrentUsername())
				.build();
		logDomain.create(log);
	}

	public void log(HttpMethod method, String path) {
		String ip = SpringSecurityUtils.getCurrentUserIp();
		Log log = Log.builder()
				.clientId(SpringSecurityUtils.getCurrentClientId())
				.createdDate(new Date())
				.ip(StringUtils.isBlank(ip) ? "0.0.0.0.0.0.0.0:1" : ip)
				.type(0)
				.userId(0)
				.username(SpringSecurityUtils.getCurrentUsername())
				.createdAt(new Date().getTime())
				.method(HttpMethod.GET)
				.path(path)
				.usr(SpringSecurityUtils.getCurrentUsername())
				.build();
		logDomain.create(log);
	}

	private final LogDomain logDomain;

	@Autowired
	public LogHelper(LogDomain logDomain) {
		this.logDomain = logDomain;
	}
}
