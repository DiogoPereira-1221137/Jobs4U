package lapr4.jobs4u.rank.domain;

import lapr4.jobs4u.rank.domain.Rank;
import lapr4.jobs4u.jobApplication.domain.Id;
import lapr4.jobs4u.jobopening.domain.JobReference;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RankTest {

    private JobReference jobReference;
    private JobReference jobReference2;
    private ArrayList<Id> candidatesRanking;
    private Rank rank;

    @BeforeEach
    void setUp() {
        jobReference = new JobReference(123);
        jobReference2 = new JobReference(456);
        candidatesRanking = new ArrayList<>();
        candidatesRanking.add(new Id(jobReference));
        candidatesRanking.add(new Id(jobReference2));
        rank = new Rank(jobReference, candidatesRanking);
    }

    @Test
    void testJobReference() {
        assertEquals(jobReference, rank.jobReference());
    }

    @Test
    void testNotEqualsJobReference() {
        assertNotEquals(jobReference2, rank.jobReference());
    }


    @Test
    void testChangeJobReference() {
        JobReference newJobReference = new JobReference(789);
        rank.changeJobReference(newJobReference);
        assertEquals(newJobReference, rank.jobReference());
    }

    @Test
    void testRankingList() {
        List<Id> rankingList = rank.rankingList();
        assertEquals(candidatesRanking, rankingList);
    }

    @Test
    void testNotEqualsRankingList() {
        List<Id> rankingList = new ArrayList<>();
        assertNotEquals(candidatesRanking, rankingList);
    }

    @Test
    void testChangeRankingList() {
        ArrayList<Id> newRankingList = new ArrayList<>();
        newRankingList.add(new Id(jobReference));
        newRankingList.add(new Id(jobReference2));
        rank.changeRankingList(newRankingList);
        assertEquals(newRankingList, rank.rankingList());
    }

    @Test
    void testEquals() {
        Rank sameRank = new Rank(jobReference, candidatesRanking);
        assertEquals(rank, sameRank);
    }

    @Test
    void testNotEquals() {
        Rank differentRank = new Rank(new JobReference(901), candidatesRanking);
        assertNotEquals(rank, differentRank);
    }


    @Test
    void testHashCode() {
        Rank sameRank = new Rank(jobReference, candidatesRanking);
        assertEquals(rank.hashCode(), sameRank.hashCode());
    }

    @Test
    void testSameAs() {
        Rank sameRank = new Rank(jobReference, candidatesRanking);
        assertTrue(rank.sameAs(sameRank));
    }

    @Test
    void testIdentity() {
        assertEquals(jobReference, rank.identity());
    }

    @Test
    void testNotSameIdentity() {
        assertNotEquals(jobReference2, rank.identity());
    }

    @Test
    void testCompareTo() {
        Rank otherRank = new Rank(new JobReference(123), new ArrayList<>());
        assertEquals(0, rank.compareTo(jobReference)); // Assuming jobReference.compareTo(jobReference) returns 0
    }


    @Test
    void testNotEqualsDifferentObject() {
        assertNotEquals(rank, new Object());
    }

    @Test
    void testNotEqualsDifferentCandidatesRanking() {
        ArrayList<Id> differentRankingList = new ArrayList<>();
        differentRankingList.add(new Id(new JobReference(789)));
        Rank differentRank = new Rank(jobReference, differentRankingList);
        assertNotEquals(rank, differentRank);
    }

    @Test
    void testEmptyCandidatesRankingList() {
        ArrayList<Id> emptyRankingList = new ArrayList<>();
        Rank emptyRank = new Rank(jobReference, emptyRankingList);
        assertEquals(emptyRankingList, emptyRank.rankingList());
    }

    @Test
    void testLargeCandidatesRankingList() {
        ArrayList<Id> largeRankingList = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            largeRankingList.add(new Id(new JobReference(i)));
        }
        Rank largeRank = new Rank(new JobReference(999), largeRankingList);
        assertEquals(1000, largeRank.rankingList().size());
    }

}
