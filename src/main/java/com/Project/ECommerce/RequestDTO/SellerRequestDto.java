package com.Project.ECommerce.RequestDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SellerRequestDto {

    private String name;
    private String email;
    private int age;
    private String mobile;
    private String panCard;

}