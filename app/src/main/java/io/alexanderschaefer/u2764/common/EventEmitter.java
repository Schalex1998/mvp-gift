package io.alexanderschaefer.u2764.common;

public interface EventEmitter<ListenerType> {
    void registerListener(ListenerType listener);

    void unregisterListener(ListenerType listener);
}
