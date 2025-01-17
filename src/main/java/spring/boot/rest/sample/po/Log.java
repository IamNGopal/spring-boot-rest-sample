package spring.boot.rest.sample.po;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.http.HttpMethod;

/**
 * Log, record users' behavior.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/27/15
 * @since JDK1.8
 */
@Entity
@Table(name = "logs")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Log implements Serializable {

	private static final long serialVersionUID = 7088091769901805623L;

	@GenericGenerator(name = "logSequenceGenerator", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = {
			@Parameter(name = "sequence_name", value = "logs_seq"), @Parameter(name = "initial_value", value = "1"),
			@Parameter(name = "increment_size", value = "1") })
	@Id
	@GeneratedValue(generator = "logSequenceGenerator")
	@Column(updatable = false)
	private long id;
	
	private String accessResource;
	
	private String clientId;

	@CreatedDate
	@Column(nullable = false)
	private Date createdDate;

	@Column(nullable = false, length = 50)
	private String ip;
	
	private int type;
	
	@Column(nullable = false)
	
	private long userId;
	private String username;
	
	@CreatedDate
	@Column(nullable = false)
	private long createdAt;
	
	private HttpMethod method;
	
	private String path;
	
	private String usr;

}
