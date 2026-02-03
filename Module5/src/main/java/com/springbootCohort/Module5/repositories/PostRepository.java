package com.springbootCohort.Module5.repositories;

import com.springbootCohort.Module5.entities.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<PostEntity, Long> {
}
