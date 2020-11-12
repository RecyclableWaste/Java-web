package IOC.annotation;

import java.lang.annotation.*;


/**
 * 自定义Component注解
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface Component {
    String value() default "";
}