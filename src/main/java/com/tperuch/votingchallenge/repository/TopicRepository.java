package com.tperuch.votingchallenge.repository;

import com.tperuch.votingchallenge.entity.TopicEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<TopicEntity, Long> {
}
