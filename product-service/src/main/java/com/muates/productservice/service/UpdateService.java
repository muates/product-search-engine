package com.muates.productservice.service;

public interface UpdateService<T, R> {
    T update(T value, R target);
}
