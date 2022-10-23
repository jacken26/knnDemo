package com.example.knndemo.mapper;

import com.example.knndemo.model.FingerPrint;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FingerTestMapper {
    FingerPrint selectById(Integer id);

    List<FingerPrint> selectAll();
    List<FingerPrint> selectBetweenId(@Param("left") Integer left, @Param("right") Integer right);
    void insertFingerPrint(FingerPrint fingerPrint);

    void insertFingerBatch(List<FingerPrint> finger);
}
