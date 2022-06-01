package support.response;

import org.springframework.context.annotation.Import;
import support.config.ServletConfig;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({ServletConfig.class, ServletResponseAdvice.class })
public @interface EnableServletResponse {
}
