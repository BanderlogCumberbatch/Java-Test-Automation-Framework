package org.pojo;

import lombok.Getter;

import java.util.List;

@Getter
public class EntityListResponse {

    /**
     * Список сущностей
     */
    private List<Response> entity;
}
