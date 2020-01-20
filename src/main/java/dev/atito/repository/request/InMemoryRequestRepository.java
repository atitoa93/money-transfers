package dev.atito.repository.request;

import dev.atito.domain.request.Request;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryRequestRepository implements RequestRepository {
    ConcurrentHashMap<UUID, Request> db = new ConcurrentHashMap<>();

    @Override
    public Request[] getAll() {
        return db.values().toArray(new Request[0]);
    }

    @Override
    public Request getOne(UUID id) {
        return db.get(id);
    }

    @Override
    public Request create(Request request) {
        db.put(request.getId(), request);
        return request;
    }

    @Override
    public Request update(Request request) {
        return db.computeIfPresent(request.getId(), (id, oldRequest) -> request);
    }

    @Override
    public Request delete(UUID id) {
        return db.remove(id);
    }
}
