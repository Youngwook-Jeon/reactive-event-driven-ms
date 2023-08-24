package com.project.young.common.orchestrator;

import com.project.young.common.messages.Request;
import org.reactivestreams.Publisher;

import java.util.UUID;

public interface RequestSender {

    Publisher<Request> send(UUID id);
}
