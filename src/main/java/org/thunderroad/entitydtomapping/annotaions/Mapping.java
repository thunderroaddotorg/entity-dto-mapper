package org.thunderroad.entitydtomapping.annotaions;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to indicate that a field has a mirrored field with a different name on the other side of the conversion.
 * The name of the mirrored field is indicated by the value.
 *
 * @author Van Lommel Bart
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Mapping {

    String value();
}
