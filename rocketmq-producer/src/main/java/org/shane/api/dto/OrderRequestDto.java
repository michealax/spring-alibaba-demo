package org.shane.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDto {
    private Integer id;

    private String orderId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Boolean isDel;
}
