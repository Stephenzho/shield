/**
 * 
 */
package io.stephen.shield.core.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author zhoushuyi
 *
 */
@ConfigurationProperties(prefix = "shield")
public class SecurityProperties {
	
	/**
	 * 浏览器环境配置
	 */
	private BrowserProperties browser = new BrowserProperties();

	private ValidateCodeProperties code = new ValidateCodeProperties();

	private SocialProperties social = new SocialProperties();


	private OAuth2Properties oauth2 = new OAuth2Properties();





	public OAuth2Properties getOauth2() {
		return oauth2;
	}

	public void setOauth2(OAuth2Properties oauth2) {
		this.oauth2 = oauth2;
	}

	public SocialProperties getSocial() {
		return social;
	}

	public void setSocial(SocialProperties social) {
		this.social = social;
	}

	public ValidateCodeProperties getCode() {
		return code;
	}

	public void setCode(ValidateCodeProperties code) {
		this.code = code;
	}

	public BrowserProperties getBrowser() {
		return browser;
	}
    public void setBrowser(BrowserProperties browser) {
	    this.browser = browser;
    }

}

