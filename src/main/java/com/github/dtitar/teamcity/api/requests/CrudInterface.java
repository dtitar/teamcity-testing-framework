package com.github.dtitar.teamcity.api.requests;

public interface CrudInterface {

    Object create(Object object);

    Object get(String id);

    Object update(String id, Object object);

    Object delete(String id);
}
