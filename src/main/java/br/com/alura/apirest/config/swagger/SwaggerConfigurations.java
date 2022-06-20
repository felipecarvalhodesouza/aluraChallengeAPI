package br.com.alura.apirest.config.swagger;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.alura.apirest.modelo.Usuario;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfigurations {

    @Bean
    public Docket restApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("br.com.alura.apirest"))
                .paths(PathSelectors.ant("/**"))
                .build()
                .apiInfo(new ApiInfoBuilder()
                			.title("Alura Challenge API - API Financeira")
                			.contact(new Contact("Felipe Carvalho de Souza", "https://www.linkedin.com/in/desouzafelipecarvalho/", "desouzafelipecarvalho@gmail.com"))
                			.description(new StringBuilder()
                						.append("Implementação do segundo desafio de back-end da plataforma de cursos online Alura. \n")
                						.append("Trata-se de uma API RESTful para controle de operações financeiras, utilizando autenticação JWT. \n")
                						.append("Detalhes sobre a implementação podem ser acessadas no repositório https://github.com/felipecarvalhodesouza/aluraChallengeAPI. \n\n")
                						.append("Necessário autenticação para todas as requisições. \n")
                						.append("Usuário teste: usuario@email.com \n" )
                						.append("Senha: 123456 \n\n")
                						.append("Usar token 'Bearer ' + token gerado pelo controller de autenticação \n\n")
                						.append("Categorias de despesas: \n")
                						.append("Alimentação \n")
                						.append("Saúde \n")
                						.append("Moradia \n")
                						.append("Transporte \n")
                						.append("Educação \n")
                						.append("Lazer \n")
                						.append("Imprevistos \n")
                						.append("Outros \n")
                						.toString())
                			.build())
                .ignoredParameterTypes(Usuario.class)
                .globalOperationParameters(Arrays.asList(
                        new ParameterBuilder()
                        .name("Authorization")
                        .description("Header para token JWT")
                        .modelRef(new ModelRef("string"))
                        .parameterType("header")
                        .required(false)
                        .build()));
    }
    

}
