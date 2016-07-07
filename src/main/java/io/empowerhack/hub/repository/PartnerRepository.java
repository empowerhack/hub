package io.empowerhack.hub.repository;

import io.empowerhack.hub.domain.Partner;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Partner entity.
 */
@SuppressWarnings("unused")
public interface PartnerRepository extends JpaRepository<Partner,Long> {

}
