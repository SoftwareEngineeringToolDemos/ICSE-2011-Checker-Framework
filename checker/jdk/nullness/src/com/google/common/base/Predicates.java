/*
 * Copyright (C) 2007 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.google.common.base;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import static com.google.common.base.Preconditions.checkNotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Contains static factory methods for creating {@code Predicate} instances.
 *
 * <p>All methods returns serializable predicates as long as they're given
 * serializable parameters.
 *
 * @author Kevin Bourrillion
 */
@GwtCompatible
public final class Predicates {
  private Predicates() {}

  // TODO: considering having these implement a VisitablePredicate interface
  // which specifies an accept(PredicateVisitor) method.

  /**
   * Returns a predicate that always evaluates to {@code true}.
   */
  @GwtCompatible(serializable = true)
  @SuppressWarnings("unchecked")
      public static <T extends /*@Nullable*/ Object> Predicate<T> alwaysTrue() {
    return (Predicate<T>) AlwaysTruePredicate.INSTANCE;
  }

  /**
   * Returns a predicate that always evaluates to {@code false}.
   */
  @GwtCompatible(serializable = true)
  @SuppressWarnings("unchecked")
  public static <T extends /*@Nullable*/ Object> Predicate<T> alwaysFalse() {
    return (Predicate<T>) AlwaysFalsePredicate.INSTANCE;
  }

  /**
   * Returns a predicate that evaluates to {@code true} if the object reference
   * being tested is null.
   */
  @SuppressWarnings("unchecked")
  public static <T extends /*@Nullable*/ Object> Predicate<T> isNull() {
    return (Predicate<T>) IsNullPredicate.INSTANCE;
  }

  /**
   * Returns a predicate that evaluates to {@code true} if the object reference
   * being tested is not null.
   */
  @SuppressWarnings("unchecked")
  public static <T extends /*@Nullable*/ Object> Predicate<T> notNull() {
    return (Predicate<T>) NotNullPredicate.INSTANCE;
  }

  /**
   * Returns a predicate that evaluates to {@code true} if the given predicate
   * evaluates to {@code false}.
   */
  public static <T extends /*@Nullable*/ Object> Predicate<T> not(Predicate<T> predicate) {
    return new NotPredicate<T>(predicate);
  }

  /**
   * Returns a predicate that evaluates to {@code true} if each of its
   * components evaluates to {@code true}. The components are evaluated in
   * order, and evaluation will be "short-circuited" as soon as a false
   * predicate is found. It defensively copies the iterable passed in, so future
   * changes to it won't alter the behavior of this predicate. If {@code
   * components} is empty, the returned predicate will always evaluate to {@code
   * true}.
   */
  public static <T extends /*@Nullable*/ Object> Predicate<T> and(
      Iterable<? extends Predicate<? super T>> components) {
    return new AndPredicate<T>(defensiveCopy(components));
  }

  /**
   * Returns a predicate that evaluates to {@code true} if each of its
   * components evaluates to {@code true}. The components are evaluated in
   * order, and evaluation will be "short-circuited" as soon as a false
   * predicate is found. It defensively copies the array passed in, so future
   * changes to it won't alter the behavior of this predicate. If {@code
   * components} is empty, the returned predicate will always evaluate to {@code
   * true}.
   */
  public static <T extends /*@Nullable*/ Object> Predicate<T> and(Predicate<? super T>... components) {
    return new AndPredicate<T>(defensiveCopy(components));
  }

  /**
   * Returns a predicate that evaluates to {@code true} if both of its
   * components evaluate to {@code true}. The components are evaluated in
   * order, and evaluation will be "short-circuited" as soon as a false
   * predicate is found.
   */
  public static <T extends /*@Nullable*/ Object> Predicate<T> and(Predicate<? super T> first,
      Predicate<? super T> second) {
    return new AndPredicate<T>(Predicates.<T>asList(
        checkNotNull(first), checkNotNull(second)));
  }

  /**
   * Returns a predicate that evaluates to {@code true} if any one of its
   * components evaluates to {@code true}. The components are evaluated in
   * order, and evaluation will be "short-circuited" as soon as as soon as a
   * true predicate is found. It defensively copies the iterable passed in, so
   * future changes to it won't alter the behavior of this predicate. If {@code
   * components} is empty, the returned predicate will always evaluate to {@code
   * false}.
   */
  public static <T extends /*@Nullable*/ Object> Predicate<T> or(
      Iterable<? extends Predicate<? super T>> components) {
    return new OrPredicate<T>(defensiveCopy(components));
  }

  /**
   * Returns a predicate that evaluates to {@code true} if any one of its
   * components evaluates to {@code true}. The components are evaluated in
   * order, and evaluation will be "short-circuited" as soon as as soon as a
   * true predicate is found. It defensively copies the array passed in, so
   * future changes to it won't alter the behavior of this predicate. If {@code
   * components} is empty, the returned predicate will always evaluate to {@code
   * false}.
   */
  public static <T extends /*@Nullable*/ Object> Predicate<T> or(Predicate<? super T>... components) {
    return new OrPredicate<T>(defensiveCopy(components));
  }

  /**
   * Returns a predicate that evaluates to {@code true} if either of its
   * components evaluates to {@code true}. The components are evaluated in
   * order, and evaluation will be "short-circuited" as soon as as soon as a
   * true predicate is found.
   */
  public static <T extends /*@Nullable*/ Object> Predicate<T> or(Predicate<? super T> first,
      Predicate<? super T> second) {
    return new OrPredicate<T>(Predicates.<T>asList(
        checkNotNull(first), checkNotNull(second)));
  }

  /**
   * Returns a predicate that evaluates to {@code true} if the object being
   * tested {@code equals()} the given target or both are null.
   */
  public static <T extends /*@Nullable*/ Object> Predicate<T> equalTo(@Nullable T target) {
    // TODO: Change signature to return Predicate<Object>.
    return (target == null)
        ? Predicates.<T>isNull()
        : new IsEqualToPredicate<T>(target);
  }

  /**
   * Returns a predicate that evaluates to {@code true} if the object being
   * tested is an instance of the given class. If the object being tested
   * is {@code null} this predicate evaluates to {@code false}.
   *
   * <p>If you want to filter an {@code Iterable} to narrow its type, consider
   * using {@link com.google.common.collect.Iterables#filter(Iterable, Class)}
   * in preference.
   */
  @GwtIncompatible("Class.isInstance")
  public static Predicate<Object> instanceOf(Class<?> clazz) {
    return new InstanceOfPredicate(clazz);
  }

  /**
   * Returns a predicate that evaluates to {@code true} if the object reference
   * being tested is a member of the given collection. It does not defensively
   * copy the collection passed in, so future changes to it will alter the
   * behavior of the predicate.
   *
   * This method can technically accept any Collection<?>, but using a typed
   * collection helps prevent bugs. This approach doesn't block any potential
   * users since it is always possible to use {@code Predicates.<Object>in()}.
   *
   * @param target the collection that may contain the function input
   */
  public static <T extends /*@Nullable*/ Object> Predicate<T> in(Collection<? extends T> target) {
    return new InPredicate<T>(target);
  }

  /**
   * Returns the composition of a function and a predicate. For every {@code x},
   * the generated predicate returns {@code predicate(function(x))}.
   *
   * @return the composition of the provided function and predicate
   */
  public static <A extends /*@Nullable*/ Object, B extends /*@Nullable*/ Object> Predicate<A> compose(
      Predicate<B> predicate, Function<A, ? extends B> function) {
    return new CompositionPredicate<A, B>(predicate, function);
  }

  /** @see Predicates#alwaysTrue() */
  // Package private for GWT serialization.
  enum AlwaysTruePredicate implements Predicate<Object> {
    INSTANCE;

    public boolean apply(/*@Nullable*/ Object o) {
      return true;
    }
    @Pure @Override public String toString() {
      return "AlwaysTrue";
    }
  }

  /** @see Predicates#alwaysFalse() */
  // Package private for GWT serialization.
  enum AlwaysFalsePredicate implements Predicate<Object> {
    INSTANCE;

    public boolean apply(/*@Nullable*/ Object o) {
      return false;
    }
    @Pure @Override public String toString() {
      return "AlwaysFalse";
    }
  }

  /** @see Predicates#not(Predicate) */
  private static class NotPredicate<T extends /*@Nullable*/ Object>
      implements Predicate<T>, Serializable {
    private final Predicate<T> predicate;

    private NotPredicate(Predicate<T> predicate) {
      this.predicate = checkNotNull(predicate);
    }
    public boolean apply(T t) {
      return !predicate.apply(t);
    }
    @Pure @Override public int hashCode() {
      return ~predicate.hashCode(); /* Invert all bits. */
    }
    @Pure @Override public boolean equals(/*@Nullable*/ Object obj) {
      if (obj instanceof NotPredicate<?>) {
        NotPredicate<?> that = (NotPredicate<?>) obj;
        return predicate.equals(that.predicate);
      }
      return false;
    }
    @Pure @Override public String toString() {
      return "Not(" + predicate.toString() + ")";
    }
    private static final long serialVersionUID = 0;
  }

  private static final Joiner commaJoiner = Joiner.on(",");

  /** @see Predicates#and(Iterable) */
  private static class AndPredicate<T extends /*@Nullable*/ Object>
      implements Predicate<T>, Serializable {
    private final Iterable<? extends Predicate<? super T>> components;

    private AndPredicate(Iterable<? extends Predicate<? super T>> components) {
      this.components = components;
    }
    @SuppressWarnings("nullness")
    // Suppressed due to the type of ?
    public boolean apply(T t) {
	for (Predicate<? super T> predicate : components) {
        if (!predicate.apply(t)) {
          return false;
        }
      }
      return true;
    }
    @Pure @Override public int hashCode() {
      int result = -1; /* Start with all bits on. */
      for (Predicate<? super T> predicate : components) {
        result &= predicate.hashCode();
      }
      return result;
    }
    @Pure @Override public boolean equals(/*@Nullable*/ Object obj) {
      if (obj instanceof AndPredicate<?>) {
        AndPredicate<?> that = (AndPredicate<?>) obj;
        return iterableElementsEqual(components, that.components);
      }
      return false;
    }
    @Pure @Override public String toString() {
      return "And(" + commaJoiner.join(components) + ")";
    }
    private static final long serialVersionUID = 0;
  }

  /** @see Predicates#or(Iterable) */
  private static class OrPredicate<T extends /*@Nullable*/ Object>
      implements Predicate<T>, Serializable {
    private final Iterable<? extends Predicate<? super T>> components;

    private OrPredicate(Iterable<? extends Predicate<? super T>> components) {
      this.components = components;
    }
    @SuppressWarnings("nullness")
    // Suppressed due to the type of ?
    public boolean apply(T t) {
      for (Predicate<? super T> predicate : components) {
        if (predicate.apply(t)) {
          return true;
        }
      }
      return false;
    }
    @Pure @Override public int hashCode() {
      int result = 0; /* Start with all bits off. */
      for (Predicate<? super T> predicate : components) {
        result |= predicate.hashCode();
      }
      return result;
    }
    @Pure @Override public boolean equals(/*@Nullable*/ Object obj) {
      if (obj instanceof OrPredicate<?>) {
        OrPredicate<?> that = (OrPredicate<?>) obj;
        return iterableElementsEqual(components, that.components);
      }
      return false;
    }
    @Pure @Override public String toString() {
      return "Or(" + commaJoiner.join(components) + ")";
    }
    private static final long serialVersionUID = 0;
  }

  /** @see Predicates#equalTo(Object) */
  private static class IsEqualToPredicate<T extends /*@Nullable*/ Object>
      implements Predicate<T>, Serializable {
      private final T target;

    private IsEqualToPredicate(T target) {
      this.target = target;
    }
    public boolean apply(/*@Nullable*/ T t) {
	return (target == null) ? t == null : target.equals(t);
    }
    @Pure @Override public int hashCode() {
	return (target == null) ? 0 : target.hashCode();
    }
    @Pure @Override public boolean equals(/*@Nullable*/ Object obj) {
      if (obj instanceof IsEqualToPredicate) {
        IsEqualToPredicate<?> that = (IsEqualToPredicate<?>) obj;
        return (target == null) ? (that.target == null) : target.equals(that.target);
      }
      return false;
    }
    @Pure @Override public String toString() {
      return "IsEqualTo(" + target + ")";
    }
    private static final long serialVersionUID = 0;
  }

  /** @see Predicates#instanceOf(Class) */
  private static class InstanceOfPredicate
      implements Predicate<Object>, Serializable {
    private final Class<?> clazz;

    private InstanceOfPredicate(Class<?> clazz) {
      this.clazz = checkNotNull(clazz);
    }
    public boolean apply(Object o) {
	return Platform.isInstance(clazz, o);
    }
    @Pure @Override public int hashCode() {
      return clazz.hashCode();
    }
    @Pure @Override public boolean equals(/*@Nullable*/ Object obj) {
      if (obj instanceof InstanceOfPredicate) {
        InstanceOfPredicate that = (InstanceOfPredicate) obj;
        return clazz == that.clazz;
      }
      return false;
    }
    @Pure @Override public String toString() {
      return "IsInstanceOf(" + clazz.getName() + ")";
    }
    private static final long serialVersionUID = 0;
  }

  /** @see Predicates#isNull() */
  // enum singleton pattern
  private enum IsNullPredicate implements Predicate<Object> {
    INSTANCE;

    public boolean apply(/*@Nullable*/ Object o) {
      return o == null;
    }
    @Pure @Override public String toString() {
      return "IsNull";
    }
  }

  /** @see Predicates#notNull() */
  // enum singleton pattern
  private enum NotNullPredicate implements Predicate<Object> {
    INSTANCE;

    public boolean apply(/*@Nullable*/ Object o) {
      return o != null;
    }
    @Pure @Override public String toString() {
      return "NotNull";
    }
  }

  /** @see Predicates#in(Collection) */
  private static class InPredicate<T extends /*@Nullable*/ Object>
      implements Predicate<T>, Serializable {
      private final Collection<? extends /*@Nullable*/ Object> target;

    private InPredicate(Collection<? extends /*@Nullable*/ Object> target) {
      this.target = checkNotNull(target);
    }

    public boolean apply(/*@Nullable*/ T t) {
      try {
        return target.contains(t);
      } catch (NullPointerException e) {
        return false;
      } catch (ClassCastException e) {
        return false;
      }
    }

    @Pure @Override public boolean equals(/*@Nullable*/ Object obj) {
      if (obj instanceof InPredicate<?>) {
        InPredicate<?> that = (InPredicate<?>) obj;
        return target.equals(that.target);
      }
      return false;
    }

    @Pure @Override public int hashCode() {
      return target.hashCode();
    }

    @Pure @Override public String toString() {
      return "In(" + target + ")";
    }
    private static final long serialVersionUID = 0;
  }

  /** @see Predicates#compose(Predicate, Function) */
  private static class CompositionPredicate<A extends /*@Nullable*/ Object, B extends /*@Nullable*/ Object>
      implements Predicate<A>, Serializable {
    final Predicate<B> p;
    final Function<A, ? extends B> f;

    private CompositionPredicate(Predicate<B> p, Function<A, ? extends B> f) {
      this.p = checkNotNull(p);
      this.f = checkNotNull(f);
    }

    public boolean apply(A a) {
      return p.apply(f.apply(a));
    }

    @Pure @Override public boolean equals(/*@Nullable*/ Object obj) {
      if (obj instanceof CompositionPredicate<?, ?>) {
        CompositionPredicate<?, ?> that = (CompositionPredicate<?, ?>) obj;
        return f.equals(that.f) && p.equals(that.p);
      }
      return false;
    }

    @Pure @Override public int hashCode() {
      /*
       * TODO:  To leave the door open for future enhancement, this
       * calculation should be coordinated with the hashCode() method of the
       * corresponding composition method in Functions.  To construct the
       * composition:
       *    predicate(function2(function1(x)))
       *
       * There are two different ways of composing it:
       *    compose(predicate, compose(function2, function1))
       *    compose(compose(predicate, function2), function1)
       *
       * It would be nice if these could be equal.
       */
      return f.hashCode() ^ p.hashCode();
    }

    @Pure @Override public String toString() {
      return p.toString() + "(" + f.toString() + ")";
    }

    private static final long serialVersionUID = 0;
  }

  /**
   * Determines whether the two Iterables contain equal elements. More
   * specifically, this method returns {@code true} if {@code iterable1} and
   * {@code iterable2} contain the same number of elements and every element of
   * {@code iterable1} is equal to the corresponding element of {@code
   * iterable2}.
   *
   * <p>This is not a general-purpose method; it assumes that the iterations
   * contain no {@code null} elements.
   */
  private static boolean iterableElementsEqual(
      Iterable<?> iterable1, Iterable<?> iterable2) {
    Iterator<?> iterator1 = iterable1.iterator();
    Iterator<?> iterator2 = iterable2.iterator();
    while (iterator1.hasNext()) {
      if (!iterator2.hasNext()) {
        return false;
      }
      if (!iterator1.next().equals(iterator2.next())) {
        return false;
      }
    }
    return !iterator2.hasNext();
  }

  private static <T extends /*@Nullable*/ Object> List<Predicate<? super T>> asList(
      Predicate<? super T> first, Predicate<? super T> second) {
    return Arrays.<Predicate<? super T>>asList(first, second);
  }

  private static <T> List<T> defensiveCopy(T... array) {
    return defensiveCopy(Arrays.asList(array));
  }

  static <T> List<T> defensiveCopy(Iterable<T> iterable) {
    ArrayList<T> list = new ArrayList<T>();
    for (T element : iterable) {
      list.add(checkNotNull(element));
    }
    return list;
  }
}
