package med.voll.api.infra.config.springdoc;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfiguration {

    private String INFO_TITLE, INFO_DESCRIPTION, INFO_CONTACT_NAME, INFO_CONTACT_EMAIL;

    @Bean
    public OpenAPI customOpenAPI() {
        this.setData();

        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")))
                .info(new Info()
                        .title(INFO_TITLE)
                        .description(INFO_DESCRIPTION)
                        .contact(new Contact()
                                .name(INFO_CONTACT_NAME)
                                .email(INFO_CONTACT_EMAIL)
                        )
                );
    }

    private void setData() {
        this.INFO_TITLE = "Voll Med API";
        this.INFO_DESCRIPTION = "Api rest didática feita para por em prática conhecimentos adquiridos pelas aulas de SpringBoot V3 da alura";
        this.INFO_CONTACT_NAME = "Thiago Barbosa";
        this.INFO_CONTACT_EMAIL = "curiosidades381@gmail.com";
    }

}
