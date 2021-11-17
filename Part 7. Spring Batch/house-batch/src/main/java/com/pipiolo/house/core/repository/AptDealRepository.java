package com.pipiolo.house.core.repository;

import com.pipiolo.house.core.entity.Apt;
import com.pipiolo.house.core.entity.AptDeal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AptDealRepository extends JpaRepository<AptDeal, Long> {

    Optional<AptDeal> findAptDealByAptAndExclusiveAreaAndDealDateAndDealAmountAndFloor(
            Apt apt, Double exclusiveArea, LocalDate dealDate, Long dealAmount, Integer floor);

    /* N+1 문제 발생 해결 */
    @Query("select ad from AptDeal ad join fetch ad.apt where ad.dealCanceled = 0 and ad.dealDate = ?1")
    List<AptDeal> findByDealCanceledIsFalseAndDealDateEquals(LocalDate dealDate);
}
