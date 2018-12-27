package com.example.scoutsapi.repository;

import com.example.scoutsapi.model.Member;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;


public interface ScoutsRepository extends ReactiveMongoRepository<Member, String> {
    @Override
    Mono<Member> findById(String memberId);
}

