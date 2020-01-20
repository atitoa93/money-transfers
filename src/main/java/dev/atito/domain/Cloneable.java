package dev.atito.domain;

public interface Cloneable<T extends Cloneable<T>> {
    T clone();
}
