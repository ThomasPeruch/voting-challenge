package com.tperuch.votingchallenge.repository;

import com.tperuch.votingchallenge.entity.SessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends JpaRepository<SessionEntity, Long> {

    SessionEntity findByTopicId(Long topicId);

}
