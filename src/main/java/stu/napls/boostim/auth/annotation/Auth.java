package stu.napls.boostim.auth.annotation;

import java.lang.annotation.*;

/**
 * @Author Tei Michael
 * @Date 12/29/2019
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Auth {
    String value() default "";
}
