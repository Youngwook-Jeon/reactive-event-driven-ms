package com.project.young.common.outbox;

import com.project.young.common.events.DomainEvent;
import lombok.Builder;

@Builder
public record Outbox<T extends DomainEvent>(Long correlationId, T event) {
}
