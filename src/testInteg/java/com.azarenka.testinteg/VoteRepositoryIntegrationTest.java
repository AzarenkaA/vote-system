package com.azarenka.testinteg;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.azarenka.votingsystem.domain.Vote;
import com.azarenka.votingsystem.repository.IVoteRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import javax.annotation.Resource;

/**
 * Test for Vote Repository.
 * <p>
 * (c) ant-azarenko@mail.ru 2020
 * </p>
 *
 * @author Anton Azarenka
 * Date 24.12.2020
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@TestPropertySource(properties = "spring.liquibase.change-log=classpath:changelog/vote-test-data.xml")
public class VoteRepositoryIntegrationTest {

    @Resource
    private IVoteRepository voteRepository;

    @Test
    public void testFindByUserId() {
        List<Vote> expectedVotes = Arrays.asList(
            buildVote("821edd24-5e58-4105-b3a7-2cc3171343d9", "4993f33d-cd83-4b87-a4d4-57a11e65aa9b", "0143508d-8658-4741-85cf-682a5d4bc344"),
            buildVote("58a3980c-3869-4b32-9031-cb02c48d5166", "4993f33d-cd83-4b87-a4d4-57a11e65aa9b", "15fa1690-9522-4a00-938f-c4a6d4e3cf73"),
            buildVote("c729907e-d680-482a-9f78-f454aca0e991", "4993f33d-cd83-4b87-a4d4-57a11e65aa9b", "46e1d5b5-6d38-4c75-995b-f8a0863f40bb"));
        List<Vote> actualVotes = voteRepository.findByUserId("4993f33d-cd83-4b87-a4d4-57a11e65aa9b");
        assertEquals(expectedVotes.size(), actualVotes.size());
        IntStream.range(0, expectedVotes.size()).forEach(i -> {
            verifyVote(expectedVotes.get(i), actualVotes.get(i));
        });
    }

    @Test
    public void testFindByRestaurantId() {
        List<Vote> expectedVotes = Arrays.asList(
            buildVote("821edd24-5e58-4105-b3a7-2cc3171343d9", "4993f33d-cd83-4b87-a4d4-57a11e65aa9b", "0143508d-8658-4741-85cf-682a5d4bc344"),
            buildVote("27808957-7f1c-410b-af45-58f166574163", "59ad497a-c2bf-425e-a129-709dc3d10b27", "0143508d-8658-4741-85cf-682a5d4bc344"));
        List<Vote> actualVotes = voteRepository.findByRestaurantId("0143508d-8658-4741-85cf-682a5d4bc344");
        assertEquals(expectedVotes.size(), actualVotes.size());
        IntStream.range(0, expectedVotes.size()).forEach(i -> {
            verifyVote(expectedVotes.get(i), actualVotes.get(i));
        });
    }

    private void verifyVote(Vote expectedVote, Vote actualVote) {
        assertEquals(expectedVote.getId(), actualVote.getId());
        assertEquals(expectedVote.getUserId(), actualVote.getUserId());
        assertEquals(expectedVote.getRestaurantId(), actualVote.getRestaurantId());
    }

    private Vote buildVote(String id, String userId, String restaurantId) {
        Vote vote = new Vote();
        vote.setId(id);
        vote.setUserId(userId);
        vote.setRestaurantId(restaurantId);
        return vote;
    }
}
