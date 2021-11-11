package cn.ponyzhang.server.util;

import cn.ponyzhang.server.dto.RSetDto;

import java.util.Comparator;

public class RSetComparator implements Comparator<RSetDto> {
    @Override
    public int compare(RSetDto o1, RSetDto o2) {
        return o2.getAge().compareTo(o1.getAge());
    }
}