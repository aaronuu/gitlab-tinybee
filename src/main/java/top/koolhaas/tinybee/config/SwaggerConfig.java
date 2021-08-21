package top.koolhaas.tinybee.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

/**
 * @program: tinybee
 * @description:
 * @author: hackerdom
 * @created: 2021/08/21
 */
@Configuration
@EnableSwagger2
@EnableKnife4j
public class SwaggerConfig {

    @Bean
    @Primary
    public Docket swaggerPersonApi10() {
        ApiKey apiKey = new ApiKey("Authorization", "Authorization", "header");
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("tinybee-api-v1.0")
                .select()
                .apis(RequestHandlerSelectors.basePackage("top.koolhaas.tinybee"))
                .paths(regex("/*"))
                .build()
                .apiInfo(new ApiInfoBuilder()
                        .version("v1.0")
                        .title("Tinybee API")
                        .termsOfServiceUrl("https://koolhaas.top")
                        .contact(new Contact("yu.zhang", "https://koolhaas.top", "uuzhangyu@gmail.com"))
                        .description("API v1.0").build()).securitySchemes(Lists.<SecurityScheme>newArrayList(apiKey));
    }
}
