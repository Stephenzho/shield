/**
 * 
 */
package io.stephen.shield.browser.validate.code.impl;

import io.stephen.shield.core.validate.code.ValidateCode;
import io.stephen.shield.core.validate.code.ValidateCodeRepository;
import io.stephen.shield.core.validate.code.ValidateCodeType;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 基于session的验证码存取器
 * 
 * @author zhoushuyi
 * @since 2018/4/29
 *
 */
@Component
public class SessionValidateCodeRepository implements ValidateCodeRepository {

	/**
	 * 验证码放入session时的前缀
	 */
	String SESSION_KEY_PREFIX = "SESSION_KEY_FOR_CODE_";
	
	/**
	 * 操作session的工具类
	 */
	private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

	/* (non-Javadoc)
	 * @see io.stephen.shield.code.validate.code.ValidateCodeRepository#save(org.springframework.web.context.request.ServletWebRequest, io.stephen.shield.code.validate.code.ValidateCode, io.stephen.shield.code.validate.code.ValidateCodeType)
	 */
	@Override
	public void save(ServletWebRequest request, ValidateCode code, ValidateCodeType validateCodeType) {
		sessionStrategy.setAttribute(request, getSessionKey(request, validateCodeType), code);
	}
	
	/**
	 * 构建验证码放入session时的key
	 * 
	 * @param request
	 * @return
	 */
	private String getSessionKey(ServletWebRequest request, ValidateCodeType validateCodeType) {
		return SESSION_KEY_PREFIX + validateCodeType.toString().toUpperCase();
	}

	/* (non-Javadoc)
	 * @see io.stephen.shield.code.validate.code.ValidateCodeRepository#get(org.springframework.web.context.request.ServletWebRequest, io.stephen.shield.code.validate.code.ValidateCodeType)
	 */
	@Override
	public ValidateCode get(ServletWebRequest request, ValidateCodeType validateCodeType) {
		return (ValidateCode) sessionStrategy.getAttribute(request, getSessionKey(request, validateCodeType));
	}

	/* (non-Javadoc)
	 * @see io.stephen.shield.code.validate.code.ValidateCodeRepository#remove(org.springframework.web.context.request.ServletWebRequest, io.stephen.shield.code.validate.code.ValidateCodeType)
	 */
	@Override
	public void remove(ServletWebRequest request, ValidateCodeType codeType) {
		sessionStrategy.removeAttribute(request, getSessionKey(request, codeType));
	}

}
