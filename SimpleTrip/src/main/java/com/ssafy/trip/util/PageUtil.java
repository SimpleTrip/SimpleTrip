package com.ssafy.trip.util;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class PageUtil {
    public static Map<String, Object> getStartAndListSize(Map<String, String> map) {
        Map<String, Object> param = new HashMap<>();
        int currentPage = Integer.parseInt(map.get("pgno") == null ? "1" : map.get("pgno"));
        int sizePerPage = Integer.parseInt(map.get("spp") == null ? "10" : map.get("spp"));
        int start = currentPage * sizePerPage - sizePerPage;
        param.put("start", start);
        param.put("listsize", sizePerPage);
        return param;
    }
}
