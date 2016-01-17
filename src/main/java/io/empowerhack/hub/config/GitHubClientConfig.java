package io.empowerhack.hub.config;

import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;

@Configuration
public class GitHubClientConfig {

    @Autowired
    private OAuth2RestTemplate oAuth2RestTemplate;

    @Bean
    @Scope(value = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public GitHubClient client() {
        GitHubClient client = new GitHubClient();
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() != "anonymousUser") { // @TODO: requires improvement
            String token = this.oAuth2RestTemplate.getAccessToken().getValue();
            client.setOAuth2Token(token);
        }

        return client;
    }

    @Bean
    @Scope(value="prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public OrganizationService organization() {
        return new OrganizationService(this.client());
    }
}
