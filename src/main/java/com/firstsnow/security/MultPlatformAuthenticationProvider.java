package com.firstsnow.security;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.cache.NullUserCache;

/**
 * 多平台的用户验证实现
 * Created by ganhaitian on 2016/3/4.
 */
public class MultPlatformAuthenticationProvider implements AuthenticationProvider,InitializingBean,
        MessageSourceAware{

    protected final Log logger = LogFactory.getLog(getClass());

    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
    protected boolean hideUserNotFoundExceptions = true;
    private UserDetailsService userDetailsService;
    private UserCache userCache = new NullUserCache();
    private boolean forcePrincipalAsString = false;
    private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        // Determine username
        String username = (authentication.getPrincipal() == null) ? "NONE_PROVIDED" : authentication.getName();

        boolean cacheWasUsed = true;
        UserDetails user = this.userCache.getUserFromCache(username);
        int platformId = 0;

        if(user == null){
            cacheWasUsed = false;

            try{

                user = userDetailsService.loadUserByUsername(username);
                if(user == null)
                    throw new AuthenticationServiceException(
                            "UserDetailsService returned null, which is an interface contract violation");

            }catch(UsernameNotFoundException notFound){
                logger.debug("User '" + username + "' not found");

                if (hideUserNotFoundExceptions) {
                    throw new BadCredentialsException(messages.getMessage(
                            "AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
                } else {
                    throw notFound;
                }
            }

            UseridTokenAunthenticationToken useridToken = (UseridTokenAunthenticationToken)authentication;
            if(useridToken.getCredentials() == null){
                logger.debug("Authentication failed: no credentials provided");

                throw new BadCredentialsException(messages.getMessage(
                        "AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"), useridToken);
            }

            platformId = useridToken.getPlatformId();
            Platform platform = Platform.getByCode(platformId);
            if(platform == null){
                throw new AuthenticationServiceException(
                        "Platform doesn't exist");
            }

            String userid = useridToken.getPrincipal().toString();
            String token = useridToken.getCredentials().toString();

            // 验证userid和token
            TokenVerifyService tokenVerifyService = platform.getTokenVerifyService();

            if(!tokenVerifyService.verify(userid,token)){
                logger.debug("Authentication failed: user id token verify failed");

                throw new BadCredentialsException(messages.getMessage(
                        "AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"), user);
            }
        }

        if (!cacheWasUsed) {
            this.userCache.putUserInCache(user);
        }

        Object principalToReturn = user;

        if (forcePrincipalAsString) {
            principalToReturn = user.getUsername();
        }

        return createSuccessAuthentication(principalToReturn, authentication, user,platformId);
    }

    /**
     * Creates a successful {@link Authentication} object.<p>Protected so subclasses can override.</p>
     *  <p>Subclasses will usually store the original credentials the user supplied (not salted or encoded
     * passwords) in the returned <code>Authentication</code> object.</p>
     *
     * @param principal that should be the principal in the returned object (defined by the {@link
     *        #isForcePrincipalAsString()} method)
     * @param authentication that was presented to the provider for validation
     * @param user that was loaded by the implementation
     *
     * @return the successful authentication token
     */
    protected Authentication createSuccessAuthentication(Object principal, Authentication authentication,
                                                         UserDetails user,int platformId) {
        // Ensure we return the original credentials the user supplied,
        // so subsequent attempts are successful even with encoded passwords.
        // Also ensure we return the original getDetails(), so that future
        // authentication events after cache expiry contain the details
        UseridTokenAunthenticationToken result = new UseridTokenAunthenticationToken(principal,
                authentication.getCredentials(), platformId,authoritiesMapper.mapAuthorities(user.getAuthorities()));
        result.setDetails(authentication.getDetails());

        return result;
    }

    public boolean supports(Class<?> authentication) {
        if(authentication == UseridTokenAunthenticationToken.class)
            return true;
        return false;
    }

    public void afterPropertiesSet() throws Exception {

    }

    public void setMessageSource(MessageSource messageSource) {

    }

    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
}
