package com.example.scoutsapi.service;

import com.example.scoutsapi.model.Member;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MemberService {
    Mono<Member> findById(String id);

    Flux<Member> findAll();

    Mono<Member> save(Member member);

    Mono<Void> deleteById(String id);
}
