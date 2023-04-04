package com.Project.ECommerce.ResponseDTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder    // it is used only, when we want to create an object using builder
public class CustomerResponseDto {

    private String name;

    private int age;

    private String email;

    private String mobile;
}