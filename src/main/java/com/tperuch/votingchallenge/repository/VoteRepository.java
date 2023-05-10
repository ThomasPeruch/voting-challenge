package com.tperuch.votingchallenge.repository;

import com.tperuch.votingchallenge.entity.VoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository extends JpaRepository<VoteEntity, Long> {
    boolean existsByAssociateIdAndSessionId(Long associateId, Long sessionId);
}
