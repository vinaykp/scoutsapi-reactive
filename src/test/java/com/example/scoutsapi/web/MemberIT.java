package com.example.scoutsapi.web;

import com.example.scoutsapi.model.Member;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient(timeout = "36000")
public class MemberIT {

    @Autowired
    ObjectMapper objectMapper;
    String mockid = "5c1a9c97c1ae12366891aa91";
    Member mockMember = new Member(new ObjectId(mockid), mockid, "TestX UserX", "testx.userx@x.com", "male", "12", "123-456-7890");
    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void createOneMemberSuccess() throws Exception {
        final String dummyMemberJson = objectMapper.writeValueAsString(mockMember);

        this.webTestClient
                .post()
                .uri("/members")
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(mockMember), Member.class)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void getAllMemberSuccess() {
        this.webTestClient
                .get()
                .uri("/members")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(Member.class)
                .consumeWith(response ->
                        Assertions.assertThat(response.getResponseBody()).isNotNull());
    }

    @Test
    public void getOneMemberSuccess() {
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
    public void deleteOneMemberSuccess() {
        this.webTestClient
                .delete()
                .uri("/members/{id}", mockid)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();

    }
}
