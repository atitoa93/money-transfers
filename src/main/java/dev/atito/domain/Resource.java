package dev.atito.domain;

import java.util.UUID;

public interface Resource extends Cloneable<Resource> {
    UUID getId();
}
