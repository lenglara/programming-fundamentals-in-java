package eventHandler;

import eventListener.Listener;

import java.io.Serializable;
import java.util.EventObject;
import java.util.LinkedList;
import java.util.List;

public class Handler <E extends EventObject> implements Serializable {
    private List<Listener<E>> listenerList = new LinkedList<>();

    public void add(Listener<E> listener) { this.listenerList.add(listener); }
    public void remove(Listener<E> listener) { this.listenerList.remove(listener); }
    public void handle(E event){
        for (Listener<E> listener : listenerList) listener.onEvent(event); }
}
