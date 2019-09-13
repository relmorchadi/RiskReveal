package com.scor.rr.domain.dto;


import com.scor.rr.domain.UserTag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    private Integer userId;
    private String userName;
    private Set<UserTag> userTags;
}
