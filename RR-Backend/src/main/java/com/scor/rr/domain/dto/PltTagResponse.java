package com.scor.rr.domain.dto;

import com.scor.rr.domain.PltManagerView;
import com.scor.rr.domain.UserTag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PltTagResponse {

    List<PltManagerView> plts;
    List<UserTag> userTags;
}
