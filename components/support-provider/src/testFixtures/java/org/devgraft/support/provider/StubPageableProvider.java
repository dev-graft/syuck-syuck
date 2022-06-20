package org.devgraft.support.provider;

import org.springframework.data.domain.Pageable;

public class StubPageableProvider extends PageableProvider {
    public Integer pageable_page_argument;
    public Integer pageable_size_argument;
    public Pageable pageable_returnValue = Pageable.unpaged();
    @Override
    public Pageable pageable(Integer page, Integer size) {
        this.pageable_page_argument = page;
        this.pageable_size_argument = size;
        return this.pageable_returnValue;
    }
}
