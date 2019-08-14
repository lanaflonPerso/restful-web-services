package org.munozy.rest.webservices.restfulwebservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@SpringBootApplication
public class RestfulWebServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestfulWebServicesApplication.class, args);
	}

	/**
	 * Use for internationalization.
	 * See {@link org.munozy.rest.webservices.restfulwebservices.controller.UserController#helloUser(long)}
	 *
	 * @return
	 */
	@Bean
	public LocaleResolver localeResolver() {
		AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();
		localeResolver.setDefaultLocale(Locale.FRANCE);

		return localeResolver;
	}

	/**
	 * jackson-dataformat-xml is definitely causing this issue (not rendering json for Swagger "/v2/api-docs").
	 * For fixing, you must to override and add a RequestMappingHandlerAdapter to force it to use JSON as
	 * the media type. (Note, this is for Spring Boot, if you have Spring MVC, then I think you have to override
	 * AnnotationMethodHandlerAdapter bean instead.
	 *
	 * @return
	 */
	@Bean
	public RequestMappingHandlerAdapter requestHandler() {
		RequestMappingHandlerAdapter adapter = new RequestMappingHandlerAdapter();
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		List<MediaType> mediaTypeList = new ArrayList<>();
		mediaTypeList.add(MediaType.APPLICATION_JSON);
		converter.setSupportedMediaTypes(mediaTypeList);
		adapter.getMessageConverters().add(converter);
		return adapter;
	}
}
