package com.aion.server.configuration;

import com.aion.server.component.paypal.dto.ApiConfig;
import com.aion.server.component.paypal.dto.PaypalCredential;
import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaypalConfig {

    @Bean(name = "httpClient")
    public PayPalHttpClient httpClient(@Qualifier("environnement") PayPalEnvironment environment) {
        return new PayPalHttpClient(environment);
    }

    @Bean(name = "sandBoxhttpClient")
    public PayPalHttpClient sandBoxHttpClient(@Qualifier("sandbox") PayPalEnvironment environment) {
        return new PayPalHttpClient(environment);
    }

    @Bean(name = "environnement")
    public PayPalEnvironment environment(final PaypalCredential credential,
                                         final ApiConfig config) {

        return new PayPalEnvironment(credential.getClientId(), credential.getCliendSecret(), config.getBaseUrl(), config.getWebUrl());
    }

    @Bean(name = "sandbox")
    public PayPalEnvironment sandBox(final PaypalCredential credential) {
        return new PayPalEnvironment.Sandbox(credential.getClientId(), credential.getCliendSecret());
    }

    @Bean
    public ApiConfig configs(@Value("${paypal.clientid}") final String baseUrl,
                             @Value("${paypal.clientid}") final String webUrl) {

        return new ApiConfig(baseUrl, webUrl);
    }

    @Bean
    public PaypalCredential paypalCredential(@Value("${paypal.clientid}") final String clientId,
                                             @Value("${paypal.clientsecret}") final String clientSecret) {

        return new PaypalCredential(clientId, clientSecret);
    }
}
