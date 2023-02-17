package org.example.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.example.domain.entity.Login;
import org.example.system_virtualStore2Application;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

//essa classe tem tudo o que nós poderemos precisar para lidar com o token
@Service
public class JwtService {

    //tempo de expiração -> setei em 30min
    @Value("${EXPIRATION_TIME}")
    private String expirationTime;

    //chave única dessa aplicação -> setei como anabanana
    @Value("${KEY}")
    private String key;

    public String gerarToken(Login login){

        long expString = Long.valueOf(expirationTime);

        //a hora de expiração data de criacao + 30 min
        LocalDateTime dataHoraExpiracao = LocalDateTime.now().plusMinutes(expString);

        //transformando a hora para instante
        Instant instant = dataHoraExpiracao.atZone(ZoneId.systemDefault()).toInstant();

        //transformando de instante para data
        Date data = Date.from(instant);

        return Jwts //para gerar um token twt
                .builder()
                .setSubject(login.getUser())//faz parte do payload -> fazemos uma identificação do usuario aqui
                .setExpiration(data) //data de expiração desse token
                .signWith(SignatureAlgorithm.HS512, key) //faz a chave de assinatura do token
                .compact(); //gera um token em formato de string

    }


    //se o token tiver expirado recebemos um erro
    private Claims obterClaims(String token) throws ExpiredJwtException {
        return Jwts
                .parser() //decodifica
                .setSigningKey(key) //com a chave de assinatura
                .parseClaimsJws(token) //e o token gerado
                .getBody(); //retorna os claims do token que geramos como Subject e data de expiração
    }

    //vamos verificar se o token está válidp
    public boolean tokenValido(String token){
        try {
            Claims claims = obterClaims(token);
            //guardando a data de expiração
            Date dataExpiracao = claims.getExpiration();
            //de date para localdatetime
            LocalDateTime data = dataExpiracao.
                    toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

            // o token é válido quando agora está antes da hora de expiração do token
            return LocalDateTime.now().isBefore(data);
        }
        catch (Exception e){
            return false;
        }
    }

    //lança exceção se o token estiver expirado
    public String obterLoginUsuario(String token) throws ExpiredJwtException{
        //retornando somente o login do usuario (que está no subject/payload)
        return (String) obterClaims(token).getSubject();
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext contexto = SpringApplication.run(system_virtualStore2Application.class);
        JwtService service = contexto.getBean(JwtService.class);

        Login login = Login.builder().user("fulano").build();

        String token = service.gerarToken(login);
        System.out.println(token);

        //verificando se o token está valido
        boolean istokenValido = service.tokenValido(token);
        System.out.println("O token está válido? "+ istokenValido);

        System.out.println(service.obterLoginUsuario(token));
    }
}
