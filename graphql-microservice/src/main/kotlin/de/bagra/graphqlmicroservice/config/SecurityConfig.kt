package de.bagra.graphqlmicroservice.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity.CsrfSpec
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.server.SecurityWebFilterChain


@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
class SecurityConfig {
    
    @Bean
    fun customWebFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain? {
        return http.csrf{c: CsrfSpec -> c.disable()}
                // Demonstrate that method security works
                // Best practice to use both for defense in depth
                .authorizeExchange{requests -> requests.anyExchange().permitAll()}
                .httpBasic(Customizer.withDefaults())
                .build()
    }
    
    @Bean
    fun customUserDetailsService(): MapReactiveUserDetailsService {
        val userBuilder: User.UserBuilder = User.withDefaultPasswordEncoder()
        val bagra: UserDetails = userBuilder.username("bagra").password("graphql").roles("USER").build()
        val admin: UserDetails = userBuilder.username("admin").password("admin").roles("USER", "ADMIN").build()
        return MapReactiveUserDetailsService(bagra, admin)
    }
}