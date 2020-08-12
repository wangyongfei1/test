package com.wisdomconstruction.wisdomConstruction;

import com.wisdomconstruction.wisdomConstruction.tool.socket.DelongServerSocket;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Date;
@EnableSwagger2
@MapperScan("com.wisdomconstruction.wisdomConstruction.dao.base.mapper.*")
@SpringBootApplication()
@EnableScheduling
public class WisdomConstructionApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(WisdomConstructionApplication.class, args);
		context.getBean(DelongServerSocket.class).start();
	}
	@Bean
	public Docket docket() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.wisdomconstruction.wisdomConstruction.controller"))
				.paths(PathSelectors.any())
				.build()
				.directModelSubstitute(Date.class, Long.class)
                .tags(new Tag("home", "首页api"))
				.apiInfo(apiInfo());
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("zqjs-construction-rest")
				.description("zqsj construction rest service")
				.version("v1.0")
				.build();
	}

}
