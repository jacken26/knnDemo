package com.example.knndemo;

import com.example.knndemo.mapper.FingerTestMapper;
import com.example.knndemo.model.FingerPrint;
import com.example.knndemo.service.KnnService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class KnnDemoApplicationTests {
    @Autowired
    private FingerTestMapper fingerTestMapper;
    @Autowired
    private KnnService knn;

    @Test
    void contextLoads() {
//        FingerPrint testFinger = fingerTestMapper.selectById(10);
        List<FingerPrint> testFingers = fingerTestMapper.selectAll();
        List<FingerPrint> trainDatas = knn.getTrainDatas();
        List<Double> errors=new ArrayList<>();
        for (FingerPrint testFinger : testFingers) {
            knn.calDistances(trainDatas,testFinger);
            List<FingerPrint> neighbors = knn.nearestNeighbors(trainDatas, 1);
            List<FingerPrint> kNeighbors = knn.calNearestNeighborsWeight(neighbors, testFinger);
            double v = knn.distError(kNeighbors, testFinger);
            errors.add(v);
            System.out.println("--------------------------------------------------------------");
        }
//        knn.calDistances(trainDatas,testFinger);
//        List<FingerPrint> neighbors = knn.nearestNeighbors(trainDatas, 4);
//        List<FingerPrint> kNeighbors = knn.calNearestNeighborsWeight(neighbors, testFinger);
//        knn.distError(kNeighbors,testFinger);
        double sumError=0;
        for (Double error : errors) {
            sumError+=error;
        }
        System.out.println(" ------Æ½¾ùÎó²î-------------");
        System.out.println(sumError/errors.size());

    }



    }




