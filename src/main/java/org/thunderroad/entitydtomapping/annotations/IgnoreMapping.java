package org.thunderroad.entitydtomapping.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to indicate that a field does not have a mirrored field on the other side of the conversion.
 *
 * @author Van Lommel Bart
 * @since 1.1
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface IgnoreMapping {
}
