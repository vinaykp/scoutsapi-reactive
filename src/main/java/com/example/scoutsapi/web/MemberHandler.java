package com.example.scoutsapi.web;

import com.example.scoutsapi.model.Member;
import com.example.scoutsapi.service.MemberService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class MemberHandler {
    private final MemberService memberService;

    public MemberHandler(MemberService memberService) {
        this.memberService = memberService;
    }

    public Mono<ServerResponse> findById(ServerRequest request) {
        String id = request.pathVariable("id");
        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(memberService.findById(id), Member.class);
    }

    public Mono<ServerResponse> findAll(ServerRequest request) {
        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(memberService.findAll(), Member.class);
    }

    public Mono<ServerResponse> save(ServerRequest request) {
        Mono<Member> member = request.bodyToMono(Member.class);
        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(fromPublisher(member.flatMap(memberService::save), Member.class));
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        String id = request.pathVariable("id");
        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(memberService.deleteById(id), Void.class);
    }
}
