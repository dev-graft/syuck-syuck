package org.devgraft.support.provider;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class PageableProvider {
    public Pageable pageable(Integer page, Integer size) {
        Pageable pageable = Pageable.unpaged();
        if (size != null) {
            pageable = Pageable.ofSize(size);
            if (page != null) {
                pageable = PageRequest.of(page, size);
            }
        }
        return pageable;
    }
}
