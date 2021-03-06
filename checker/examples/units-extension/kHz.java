import java.lang.annotation.*;

import org.checkerframework.checker.units.qual.Prefix;
import org.checkerframework.checker.units.qual.UnitsMultiple;
import org.checkerframework.checker.units.qual.UnitsRelations;
import org.checkerframework.framework.qual.*;

/**
 * Kilohertz (kHz), a unit of frequency, and an alias of @Hz(Prefix.kilo).
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE_USE, ElementType.TYPE_PARAMETER})
@TypeQualifier
@SubtypeOf(Frequency.class)
@UnitsRelations(FrequencyRelations.class)
// declares it to be an alias of @Hz(Prefix.kilo)
@UnitsMultiple(quantity=Hz.class, prefix=Prefix.kilo)
// No prefix defined in the annotation itself
public @interface kHz {}
