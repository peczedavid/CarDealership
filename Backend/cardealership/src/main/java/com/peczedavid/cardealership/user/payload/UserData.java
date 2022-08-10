package com.peczedavid.cardealership.user.payload;

import com.peczedavid.cardealership.region.Region;
import com.peczedavid.cardealership.user.User;

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
