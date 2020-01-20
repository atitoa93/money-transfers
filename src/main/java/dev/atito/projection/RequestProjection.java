package dev.atito.projection;

import com.google.common.eventbus.Subscribe;
import dev.atito.event.pool.RequestCreatedEvent;
import dev.atito.event.pool.RequestUpdatedEvent;
import dev.atito.domain.request.*;
import dev.atito.repository.request.InMemoryRequestRepository;
import dev.atito.repository.request.RequestRepository;

import java.util.UUID;

public class RequestProjection {
    private RequestRepository requestRepository = new InMemoryRequestRepository();

    public Request[] getAll() {
        return requestRepository.getAll();
    }

    public Request getOne(UUID id) {
        return requestRepository.getOne(id);
    }

    @Subscribe
    public void create(RequestCreatedEvent event) {
        requestRepository.create(event.getRequest());
    }

    @Subscribe
    public void update(RequestUpdatedEvent event) {
        requestRepository.update(event.getRequest());
    }
}
