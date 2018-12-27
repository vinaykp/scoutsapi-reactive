package com.example.scoutsapi.service;

import com.example.scoutsapi.model.Member;
import com.example.scoutsapi.repository.ScoutsRepository;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberServiceTest {

    String mockid = "5c1a9c97c1ae12366891aa91";
    Member mockMember = new Member(new ObjectId(mockid), mockid, "TestX UserX", "testx.userx@x.com", "male", "12", "123-456-7890");
    @MockBean
    private ScoutsRepository scoutsRepository;
    @Autowired
    private MemberService memberService;

    @Test
    public void findByIdSuccess() {
        Mockito
                .when(this.scoutsRepository.findById(mockid))
                .thenReturn(Mono.just(mockMember));

        Mono<Member> member = memberService.findById(mockid);
        assert (member.block().getMemberId().equals(mockid));
    }

    @Test
    public void findAllSuccess() {
        Flux<Member> mockMembers = Flux.create(memberFluxSink -> {
            memberFluxSink.next(mockMember);
            memberFluxSink.complete();
        });
        Mockito
                .when(this.scoutsRepository.findAll())
                .thenReturn(mockMembers);

        Flux<Member> member = memberService.findAll();
        StepVerifier.create(member)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    public void saveSuccess() {
        Mockito
                .when(this.scoutsRepository.save(mockMember))
                .thenReturn(Mono.just(mockMember));

        Mono<Member> member = memberService.save(mockMember);
        assert (member.block().getMemberId().equals(mockid));
    }


    @Test
    public void deleteByIdSuccess() {
        Mockito
                .when(this.scoutsRepository.deleteById(mockid))
                .thenReturn(Mono.empty());

        Mono<Void> member = memberService.deleteById(mockid);
        StepVerifier.create(member).verifyComplete();

    }
}
