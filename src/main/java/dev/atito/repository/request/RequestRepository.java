package dev.atito.repository.request;

import dev.atito.domain.request.Request;

import java.util.UUID;

public interface RequestRepository {
    Request[] getAll();
    Request getOne(UUID id);
    Request create(Request request);
    Request update(Request Request);
    Request delete(UUID id);
}
