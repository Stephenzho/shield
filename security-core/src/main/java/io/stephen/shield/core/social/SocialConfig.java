package io.stephen.shield.core.social;

import io.stephen.shield.core.properties.SecurityProperties;
import io.stephen.shield.core.social.support.ShieldSpringSocialConfigurer;
import io.stephen.shield.core.social.support.SocialAuthenticationFilterPostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

/**
 * @author zhoushuyi
 * @since 2018/4/30
 */
@Configuration
@EnableSocial
@Order(1)
public class SocialConfig extends SocialConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired(required = false)
    private ConnectionSignUp connectionSignUp;

    @Autowired(required = false)
    private SocialAuthenticationFilterPostProcessor socialAuthenticationFilterPostProcessor;

    @Autowired
    protected AuthenticationFailureHandler shieldAuthenticationFailureHandler;

    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        JdbcUsersConnectionRepository repository = new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, Encryptors.noOpText());
    //    repository.setTablePrefix(null);    //数据库UserConnection表名前缀

        if (null != connectionSignUp) {
            repository.setConnectionSignUp(connectionSignUp);
        }
        return repository;
    }


    @Bean
    public SpringSocialConfigurer shieldSocialSecurityConfig() {
        String filterProcessesUrl = securityProperties.getSocial().getFilterProcessesUrl();
        String signUpUrl = securityProperties.getBrowser().getSignUpUrl();

        ShieldSpringSocialConfigurer shieldSpringSocialConfigurer = new ShieldSpringSocialConfigurer(filterProcessesUrl);
        shieldSpringSocialConfigurer.signupUrl(signUpUrl);

        shieldSpringSocialConfigurer.setSocialAuthenticationFilterPostProcessor(socialAuthenticationFilterPostProcessor);
        return shieldSpringSocialConfigurer;
    }

    @Bean
    public ProviderSignInUtils providerSignInUtils(ConnectionFactoryLocator connectionFactoryLocator) {
        return new ProviderSignInUtils(connectionFactoryLocator, getUsersConnectionRepository(connectionFactoryLocator));
    }
}
