package com.peczedavid.cardealership.user.payload;

import com.peczedavid.cardealership.region.Region;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponse {
    
    private Long id;

    private String username;

    private Region region;

    private boolean admin;    
}
