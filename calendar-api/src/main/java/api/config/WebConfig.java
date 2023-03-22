package api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {


    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new AuthUserResolver());
    }
    //addArgumentResolvers 이 메소드를 스프링 WebMvc 프레임워크 내에서 호출하는 타이밍에
    //내가 만든 AuthUserResolver 도 추가를 해준다.
}

