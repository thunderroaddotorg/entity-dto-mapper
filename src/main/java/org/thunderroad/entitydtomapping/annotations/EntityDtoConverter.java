package org.thunderroad.entitydtomapping.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to indicate that a entity and DTO bean are not mirrored fields and conversion will be done by the converter field indicated in the annotation.
 *
 *
 * @author Van Lommel Bart
 * @since 1.3
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface EntityDtoConverter {

    Class<?> converter();
}
