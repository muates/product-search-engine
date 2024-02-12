package com.muates.productservice.transformer;

public interface ProductTransformer<V, R> {
    R transform(V value);
}
