package com.Project.ECommerce.ResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SellerResponseDto {

    private String name;
    private String email;
    private int age;
    private String mobile;
    private String panCard;
}
