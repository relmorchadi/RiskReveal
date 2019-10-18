package com.scor.rr.domain.dto;

import com.scor.rr.domain.TargetBuild.PLTManagerView;
import com.scor.rr.domain.TargetBuild.UserTag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PltTagResponse {

    List<PLTManagerView> plts;
    List<UserTag> userTags;
}
