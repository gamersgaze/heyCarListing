package com.heycar.challenge.repository;


import com.heycar.challenge.entities.ListingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ListingRepository extends JpaRepository<ListingEntity, Long>, ListingRepositoryCustom {

    @Query(value = "SELECT listing FROM ListingEntity listing WHERE listing.dealerId=:dealerId AND listing.code In :codeIds")
    List<ListingEntity> findListingsByDealerId(@Param("dealerId") Long dealerId, @Param("codeIds") Set<String> codeIds);

}
