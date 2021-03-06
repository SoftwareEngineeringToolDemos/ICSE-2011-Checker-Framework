/*
 * @test
 * @summary Test that verifies defaults in a non-public class in the same
 * file.
 *
 * @compile -XDrawDiagnostics -Xlint:unchecked -processor org.checkerframework.checker.nullness.NullnessChecker -AprintErrorStack -Alint DefaultNonPublicClass.java
 * @compile -XDrawDiagnostics -Xlint:unchecked -processor org.checkerframework.checker.nullness.NullnessChecker -AprintErrorStack -Alint DefaultNonPublicClass.java
 */

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

class Test {
  Integer foo() {
    return 123;
  }
}

public class DefaultNonPublicClass {
  public static void main(String[] args) {
    Test ti = new Test();
    @NonNull Integer ls = ti.foo();
  }
}
