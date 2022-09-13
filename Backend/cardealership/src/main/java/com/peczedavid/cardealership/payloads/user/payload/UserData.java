package com.peczedavid.cardealership.payloads.user.payload;

import com.peczedavid.cardealership.models.Region;
import com.peczedavid.cardealership.models.User;

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
public class UserData {
    
    private Long id;

    private String username;

    private Region region;

    private boolean admin;

    public UserData(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.region = user.getRegion();
        this.admin = user.isAdmin();
    }
}
