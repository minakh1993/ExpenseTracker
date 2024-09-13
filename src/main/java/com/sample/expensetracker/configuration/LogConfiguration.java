package com.sample.expensetracker.configuration;

import com.tosan.tools.mask.starter.business.enumeration.MaskType;
import com.tosan.tools.mask.starter.config.SecureParameter;
import com.tosan.tools.mask.starter.config.SecureParametersConfig;
import com.tosan.tools.mask.starter.configuration.MaskBeanConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.Set;

/**
 * @author M.khoshnevisan
 * @since 9/13/2024
 */
@Configuration
public class LogConfiguration {

    private Set<SecureParameter> SECURE_PARAMETERS = new HashSet<>() {{
        addAll(MaskBeanConfiguration.SECURED_PARAMETERS);
        add(new SecureParameter("Authorization", MaskType.COMPLETE));
        add(new SecureParameter("password", MaskType.COMPLETE));
        add(new SecureParameter("username", MaskType.SEMI));
        add(new SecureParameter("nationalCode", MaskType.LEFT));
        add(new SecureParameter("amount", MaskType.SEMI));
    }};

    @Bean("http-server-util-secured-parameters")
    public SecureParametersConfig secureParametersConfig() {
        return new SecureParametersConfig(SECURE_PARAMETERS);
    }
}