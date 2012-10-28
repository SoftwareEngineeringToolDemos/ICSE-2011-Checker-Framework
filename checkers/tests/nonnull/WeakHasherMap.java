import java.util.Map;
import java.util.AbstractMap;

import checkers.quals.*;
import checkers.nonnull.quals.*;
import checkers.regex.quals.*;
import checkers.initialization.quals.*;

//:: error: (commitment.fields.uninitialized)
public abstract class WeakHasherMap<K, V> extends AbstractMap<K, V> implements
        Map<K, V> {
    private Map<Object, V> hash;

    //:: error: (override.param.invalid)
    public boolean containsKey(Object key) {
        K kkey = (K) key;
        hash.containsKey(null);
        return true;
    }
}