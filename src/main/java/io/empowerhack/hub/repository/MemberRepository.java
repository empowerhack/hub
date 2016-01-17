package io.empowerhack.hub.repository;

import io.empowerhack.hub.domain.Member;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends PagingAndSortingRepository<Member, Long> {

    Member findByUsername(@Param("username") String username);
}
