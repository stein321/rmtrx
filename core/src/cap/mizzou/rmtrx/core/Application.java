package cap.mizzou.rmtrx.core;

import javax.inject.Qualifier;
import java.lang.annotation.*;

/**
 *
 */
@Qualifier
@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Application {
}
