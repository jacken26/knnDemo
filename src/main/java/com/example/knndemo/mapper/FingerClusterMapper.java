package com.example.knndemo.mapper;


import com.example.knndemo.model.FingerPrint;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FingerClusterMapper {
    List<FingerPrint> selectAll();

    void updateCluster(FingerPrint fingerPrints);
}
