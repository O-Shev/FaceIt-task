package com.leshkins.faceittask.dto;

import java.util.List;

public record ArbeitnowJobBoardApiDTO(List<JobDTO> data,
                                      PaginationLinks links,
                                      MetaInfo meta) {
    public record PaginationLinks(
            String first,
            String last,
            String prev,
            String next
    ) {}

    public record MetaInfo(
            int currentPage,
            int from,
            String path,
            int perPage,
            int to,
            String terms,
            String info
    ) {}

}
