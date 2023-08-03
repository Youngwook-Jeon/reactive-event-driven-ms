package com.project.young.common.events;

import java.time.Instant;

public interface DomainEvent {
    Instant createdAt();
}
