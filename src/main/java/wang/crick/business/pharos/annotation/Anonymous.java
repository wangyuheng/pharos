package wang.crick.business.pharos.annotation;

import java.lang.annotation.*;

/**
 * 不验权
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Anonymous {
}
