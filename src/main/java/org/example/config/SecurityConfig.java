package org.example.config;

import org.example.security.jwt.JwtAuthFilter;
import org.example.security.jwt.JwtService;
import org.example.service.impl.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableWebSecurity //essa anotação já é uma @configuration
@EnableSwagger2
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UsuarioServiceImpl usuarioService;

    @Autowired
    private JwtService jwtService;

    //password enconder criptografa e descrip a senha dos usuarios
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(); //algoritmo de criptografia
    }

    // toda vez que tiver um request de "auth", vai entrar nesse filtro, que adicionamos no método
    // "protected void configure(HttpSecurity http)" logo abaixo,
    // é aqui que vamos comparar se as senhas batem e se o usuário que entramos existe
    @Bean
    public OncePerRequestFilter jwtFilter(){
        return new JwtAuthFilter(jwtService, usuarioService);
    }

    //verifica se o usuário autenticado acima tem autorização
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() //desabilitando config entre aplicação web e back (nao temos aplicacao web)
                .authorizeRequests() //vamos definir quem acessa o que -> para acessas a api de clientes precisa
                //ter role de USER
//                .antMatchers("/api/clientes/**")
//                .hasAnyRole("USER", "ADMIN")
//                .antMatchers("/api/produtos/**")
//                .hasRole("ADMIN")
//                .antMatchers("/api/pedidos/**")
//                .hasRole("USER")
                .antMatchers(HttpMethod.POST,"/logins/**")
                .permitAll()
                .antMatchers("/swagger-ui/#/**")
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement()//agora ele não vai mais criar sessões, cada requisição vai precisar passar o token para podermos reconhecer o usuario
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class); //fazemos com que nosso filtro seja rodado antes do filtro UsernamePasswordAuthenticationFilter
    }

    //autentica usuário
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //essa eh uma autenticacao em memoria sem bd
        auth.
                userDetailsService(usuarioService) //carrega os usuários
                .passwordEncoder(passwordEncoder()); //compara as senhas do usuario
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        //ignorando urls do swagger para que não passem pelo HttpSecurity
        web.ignoring().antMatchers("/static/css/**", "/static/js/**", "*.ico");

        // swagger
        web.ignoring().antMatchers("/v2/api-docs",
                "/v3/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui/**",
                "/webjars/**",
                "/csrf/**");

    }
}
