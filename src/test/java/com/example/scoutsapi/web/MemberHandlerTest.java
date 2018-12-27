package com.example.scoutsapi.web;

import com.example.scoutsapi.model.Member;
import com.example.scoutsapi.service.MemberServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MemberHandlerTest {

    @Autowired
    ObjectMapper objectMapper;
    String mockid = "5c1a9c97c1ae12366891aa91";
    Member mockMember = new Member(new ObjectId(mockid), mockid, "TestX UserX", "testx.userx@x.com", "male", "12", "123-456-7890");
    @Autowired
    private WebTestClient webTestClient;
    @MockBean
    private MemberServiceImpl memberService;

    @Test
    public void getAllMemberSuccess() {
        Flux<Member> mockMembers = Flux.create(memberFluxSink -> {
            memberFluxSink.next(mockMember);
            memberFluxSink.complete();
        });

        Mockito
                .when(this.memberService.findAll())
                .thenReturn(mockMembers);

        this.webTestClient
                .get()
                .uri("/members")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(Member.class)
                .consumeWith(response ->
                        Assertions.assertThat(response.getResponseBody()).isNotNull())
                .hasSize(1);
    }

    @Test
    public void getOneMemberSuccess() {

        Mockito
                .when(this.memberService.findById(mockid))
                .thenReturn(Mono.just(mockMember));

        this.webTestClient
                .get()
                .uri("/members/{id}", mockid)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(Member.class)
                .consumeWith(response ->
                        Assertions.assertThat(response.getResponseBody().get_id().toString()).isEqualTo(mockid));
    }

    @Test
    public void createOneMemberSuccess() throws Exception {
        final String dummyMemberJson = objectMapper.writeValueAsString(mockMember);

        Mockito
                .when(this.memberService.save(mockMember))
                .thenReturn(Mono.just(mockMember));

        this.webTestClient
                .post()
                .uri("/members")
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(mockMember), Member.class)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void deleteOneMemberSuccess() {

        Mockito
                .when(this.memberService.deleteById(mockid))
                .thenReturn(Mono.empty());

        this.webTestClient
                .delete()
                .uri("/members/{id}", mockid)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();

    }
}
