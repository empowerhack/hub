package io.empowerhack.hub.repository;

import io.empowerhack.hub.domain.member.Social;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SocialRepository extends PagingAndSortingRepository<Social, Long> {

    Social findOneByUid(@Param("uid") String uid);
}
