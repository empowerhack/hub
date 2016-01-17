package io.empowerhack.hub.repository;

import io.empowerhack.hub.domain.member.Interest;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InterestRepository extends PagingAndSortingRepository<Interest, Long> {

    Interest findOneByUid(@Param("uid") String uid);
}
