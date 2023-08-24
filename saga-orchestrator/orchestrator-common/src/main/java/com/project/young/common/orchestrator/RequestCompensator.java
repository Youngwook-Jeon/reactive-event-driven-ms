package com.project.young.common.orchestrator;

import com.project.young.common.messages.Request;
import org.reactivestreams.Publisher;

import java.util.UUID;

public interface RequestCompensator {

    Publisher<Request> compensate(UUID id);
}
