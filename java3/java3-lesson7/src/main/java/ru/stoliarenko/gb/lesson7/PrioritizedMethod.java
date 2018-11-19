package ru.stoliarenko.gb.lesson7;

import java.lang.reflect.Method;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

public final class PrioritizedMethod implements Comparable<PrioritizedMethod>{

    private Method method;
    private int priority;
    
    public PrioritizedMethod(@NonNull final Method method, final int priority) {
        this.method = method;
        this.priority = (priority < 1) ? (1) : (priority > 10 ? 10 : priority);
    }
    
    public void setFirst() {
        this.priority = 11;
    }
    public void setLast() {
        this.priority = 0;
    }
    
    public void invoke() throws Exception {
        Object instance = method.getDeclaringClass().getConstructor().newInstance();
        method.invoke(instance, null);
    }
    
    @Override
    public int compareTo(@Nullable final PrioritizedMethod o) {
        if (o == null) throw new NullPointerException("Cant compare with null");
        if (this == o) return 0;
        return o.priority - this.priority;
    }
    
    @Override
    public String toString() {
        return method.getName();
    }
}
