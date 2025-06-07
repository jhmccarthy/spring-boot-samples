package com.jimmccarthy.rugby.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.boot.devtools.restart.Restarter.disable;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private static final String PUBLIC_ENDPOINT = "/api/**";
    private static final String ADMIN_ENDPOINT = "/api/v1/rugby/owner/**";
    private static final String MGR_ENDPOINT = "/api/v1/rubgy/mgr/**";
    private static final String OWNER_ROLE = "OWNER";
    private static final String MGR_ROLE = "MGR";
    private final AppProperties appProperties;

    @Autowired
    public SecurityConfig(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    /**
     * The {@code BCryptPasswordEncoder} will internally generate a random salt. Note that the BCrypt algorithm
     * generates a {@code String} of length 60.
     *
     * @return the BCrypt password encoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Some endpoints require HTTP Basic Authentication. We don't need a full-blown security manager and the
     * {@code InMemoryUserDetailsManager} is sufficient for these users.
     *
     * @return the user service
     */
    @Bean
    public UserDetailsService userDetailsService() {
        var manager = new InMemoryUserDetailsManager();

        // Manager user
        manager.createUser(User.withUsername(appProperties.getManager().getUsername()) //
                .password(appProperties.getManager().getPassword()) //
                .roles(MGR_ROLE) //
                .build());

        // Owner user
        manager.createUser(User.withUsername(appProperties.getOwner().getUsername()) //
                .password(appProperties.getOwner().getPassword()) //
                .roles(OWNER_ROLE) //
                .build());

        return manager;
    }

    /**
     * This adapter protects the URL. The endpoint is security with HTTP Basic Authentication (username/password). The
     * request is verified using the {@code UserDetailsService}.
     * <p>
     * The session creation policy of {@code stateless} guarantees that the application will not create any session at
     * all. This control mechanism has the direct implication that cookies are not used ans each and every request needs
     * to be re-authenticated.
     */
    @Configuration
    @Order(1)
    public static class AuthConfigurationAdapter {
        /**
         * {@inheritDoc}
         */
        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            return http.httpBasic(withDefaults()) //
                    .authorizeHttpRequests(request -> {
                        request.requestMatchers(HttpMethod.POST, ADMIN_ENDPOINT).hasRole(OWNER_ROLE); //
                        request.requestMatchers(HttpMethod.POST, MGR_ENDPOINT).hasRole(MGR_ROLE); //
                        request.requestMatchers(HttpMethod.PUT, MGR_ENDPOINT).hasRole(MGR_ROLE); //
                    })
                    .csrf(csrf -> disable()) // NOSONAR - we don't need CSRF protected for these RESTful endpoints
                    .formLogin(login -> disable()) //
                    .sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //
                    .build();
        }
    }

    /**
     * This adapter allows all users, including authenticated ones, to access the public endpoints.
     * <p>
     * The session creation policy of {@code stateless} guarantees that the application will not create any session at
     * all. This control mechanism has the direct implication that cookies are not used ans each and every request needs
     * to be re-authenticated.
     */
    @Configuration
    @Order(2)
    public static class NoAuthConfigurationAdapter {
        /**
         * {@inheritDoc}
         */
        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            return http
                    .authorizeHttpRequests((authorize) -> //
                            authorize.requestMatchers(PUBLIC_ENDPOINT).permitAll() //
                                    .anyRequest().authenticated() //
                    ) //
                    .build();
        }
    }
}
