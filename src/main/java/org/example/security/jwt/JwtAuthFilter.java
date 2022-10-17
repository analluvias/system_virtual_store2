package org.example.security.jwt;


import org.example.service.impl.UsuarioServiceImpl;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//implementa o filtro do spring framework
//esse é um filtro que intercepta uma requisição e obtem as informações
//adicionando nessas informações um usuário autenticado, somnte se o token estiver correto
//depois, no do filter ele vai passar pra frente para
//o spring security verificar as autorities desse usuário

public class JwtAuthFilter extends OncePerRequestFilter {

    private JwtService jwtService;

    private UsuarioServiceImpl usuarioService;

    public JwtAuthFilter(JwtService jwtService, UsuarioServiceImpl usuarioService) {
        this.jwtService = jwtService;
        this.usuarioService = usuarioService;
    }

    //aqui vamos obter o token que foi enviado via requisição do postman (pelo header)
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authorization = httpServletRequest.getHeader("Authorization");

        //se a autorização não está nula e começa com o Bearer(token do tipo Bearer)
        if (authorization != null && authorization.startsWith("Bearer")){
            //pegando apenas o token depois do nome Bearer (são separados por espaço)
            String token = authorization.split(" ")[1];

            boolean isValid = jwtService.tokenValido(token);

            if (isValid){
                String loginUsuario = jwtService.obterLoginUsuario(token);

                //aqui estamos procurando o usuário na base de dados e pegando suas
                //autorozações
                //se ele não existir vamos lançar uma exceção
                UserDetails usuario = usuarioService.loadUserByUsername(loginUsuario);

                //criando um objeto do tipo que já existe no springboot
                //passando o usuário que pegamos do banco
                //passando nulo em suas credenciais
                //passando as autoridades dele (user, admin)
                UsernamePasswordAuthenticationToken user =
                        new UsernamePasswordAuthenticationToken(usuario, null,
                                usuario.getAuthorities());

                //informando que essa autenticação criada acima é uma autenticação web
                user.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

                //aqui estamos jogando o usuário que a gente carregou para dentro do contexto do
                //spring Security
                SecurityContextHolder.getContext().setAuthentication(user);
            }
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
