package io.empowerhack.hub.repository;

import io.empowerhack.hub.domain.member.Skill;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillRepository extends PagingAndSortingRepository<Skill, Long> {

    Skill findOneByUid(@Param("uid") String uid);
}
