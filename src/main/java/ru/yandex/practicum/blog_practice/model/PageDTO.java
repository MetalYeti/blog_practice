package ru.yandex.practicum.blog_practice.model;

import org.springframework.data.domain.Page;


public class PageDTO<T> {
    private final Page<T> page;

    public PageDTO(Page<T> page) {
        this.page = page;
    }

    public int pageNumber() {
        return page.getNumber();
    }

    public int pageSize() {
        return page.getSize();
    }

    public boolean hasPrevious() {
        return page.hasPrevious();
    }

    public boolean hasNext() {
        return page.hasNext();
    }
}
