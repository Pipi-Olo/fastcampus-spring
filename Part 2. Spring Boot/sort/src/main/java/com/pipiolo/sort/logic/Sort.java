package com.pipiolo.sort.logic;

import org.springframework.stereotype.Component;

import java.util.List;

public interface Sort <T extends Comparable<T>> {
    List<T> sort(List<T> list);
}
