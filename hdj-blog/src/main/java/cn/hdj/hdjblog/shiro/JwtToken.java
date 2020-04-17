package cn.hdj.hdjblog.shiro;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.shiro.authc.HostAuthenticationToken;

/**
 * @author hdj
 * @ClassName: JwtToken
 * @Description: JwtToken 信息
 * @date 2019-03-29 17:23
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtToken implements HostAuthenticationToken {

	private String token;

	private String host;

	public JwtToken(String token) {
		this(token, null);
	}

	@Override
	public Object getPrincipal() {
		return this.token;
	}

	@Override
	public Object getCredentials() {
		return this.token;
	}

	@Override
	public String toString() {
		return token + ':' + host;
	}
}
