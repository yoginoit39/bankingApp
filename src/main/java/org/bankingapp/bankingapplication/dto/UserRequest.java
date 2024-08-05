package org.bankingapp.bankingapplication.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;




@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

    private Long id;
    private String firstName;
    private String lastName;
    private String otherName;
    private String address;
    private String gender;
    private String placeOfOrigin;
    private String phoneNumber;
    private String email;

}
