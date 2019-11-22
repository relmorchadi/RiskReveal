package com.scor.rr.domain.dto.TargetBuild;

import com.scor.rr.domain.TargetBuild.Search.SearchItem;
import com.scor.rr.domain.enums.SearchType;
import lombok.Data;

import java.util.List;

@Data
public class SavedSearchRequest {
    SearchType searchType;
    List<SearchItem> items;
    String label;
    Integer userId;
}
