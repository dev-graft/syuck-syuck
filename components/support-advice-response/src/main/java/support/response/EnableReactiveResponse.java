package support.response;

import org.springframework.context.annotation.Import;
import support.config.ReactiveConfig;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({ReactiveConfig.class })
public @interface EnableReactiveResponse {
}
