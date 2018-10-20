package io.alexanderschaefer.u2764.common;

import java.util.HashSet;
import java.util.Set;

import androidx.core.util.Consumer;

public abstract class DefaultEventEmitter<ListenerType> implements EventEmitter<ListenerType> {

    private Set<ListenerType> listeners = new HashSet<>();

    @Override
    public void registerListener(ListenerType listener) {
        if (listener != null) listeners.add(listener);
    }

    @Override
    public void unregisterListener(ListenerType listener) {
        listeners.remove(listener);
    }

    protected void forEachListener(Consumer<ListenerType> consumer) {
        for (ListenerType listener : listeners) {
            consumer.accept(listener);
        }
    }
}
