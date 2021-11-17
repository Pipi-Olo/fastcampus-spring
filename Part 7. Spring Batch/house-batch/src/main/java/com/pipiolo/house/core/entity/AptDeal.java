package com.pipiolo.house.core.entity;

import com.pipiolo.house.core.dto.AptDealDto;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@EntityListeners(AuditingEntityListener.class)
@Table(name = "apt_deal")
@Entity
public class AptDeal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long aptDealId;

    @ManyToOne
    @JoinColumn(name = "apt_id")
    private Apt apt;

    @Column(nullable = false)
    private Double exclusiveArea;

    @Column(nullable = false)
    private LocalDate dealDate;

    @Column(nullable = false)
    private Long dealAmount;

    @Column(nullable = false)
    private Integer floor;

    @Column(nullable = false)
    private Boolean dealCanceled;

    @Column
    private LocalDate dealCanceledDate;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public static AptDeal from(AptDealDto dto, Apt apt) {
        AptDeal aptDeal = new AptDeal();
        aptDeal.setApt(apt);
        aptDeal.setExclusiveArea(dto.getExclusiveArea());
        aptDeal.setDealDate(dto.getDealDate());
        aptDeal.setDealAmount(dto.getDealAmount());
        aptDeal.setFloor(dto.getFloor());
        aptDeal.setDealCanceled(dto.getDealCanceled());
        aptDeal.setDealCanceledDate(dto.getDealCanceledDate());
        return aptDeal;
    }
}
