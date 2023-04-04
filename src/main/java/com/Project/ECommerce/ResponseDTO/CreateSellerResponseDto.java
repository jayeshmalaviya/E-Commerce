package com.Project.ECommerce.ResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateSellerResponseDto {

    private int id;
    private String name;
    private String email;
    private String mobile;
    private String panCard;
}
