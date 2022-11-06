package com.konkuk.batnam.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ListResponseDto<T> {

    private Integer page;
    private Integer pageSize;
    private Integer count;
    private List<T> result;

    public static <T> ListResponseDto<T> toListResponse(List<T> entityList,int count, Pageable pageable) {
        return new ListResponseDto<>(pageable.getPageNumber(), pageable.getPageSize(), count, entityList);
    }
}

