package com.example.scoutsapi.service;

import com.example.scoutsapi.model.Member;
import com.example.scoutsapi.repository.ScoutsRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MemberServiceImpl implements MemberService {

    private ScoutsRepository scoutsRepository;

    public MemberServiceImpl(ScoutsRepository scoutsRepository) {
        this.scoutsRepository = scoutsRepository;
    }

    @Override
    public Mono<Member> findById(String id) {
        return scoutsRepository.findById(id);
    }

    @Override
    public Flux<Member> findAll() {
        return scoutsRepository.findAll();
    }

    @Override
    public Mono<Member> save(Member member) {
        return scoutsRepository.save(member);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return scoutsRepository.deleteById(id);
    }
}
