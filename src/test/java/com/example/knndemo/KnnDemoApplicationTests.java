package com.example.knndemo;

import com.example.knndemo.mapper.FingerClusterMapper;
import com.example.knndemo.mapper.FingerTestMapper;
import com.example.knndemo.mapper.FingertrainMapper;
import com.example.knndemo.model.FingerPrint;
import com.example.knndemo.service.Kmeans;
import com.example.knndemo.service.Knn;
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
    private FingertrainMapper fingertrainMapper;
    @Autowired
    private Knn knn;

    @Autowired
    private Kmeans kmeans;
    @Autowired
    private FingerClusterMapper fingerClusterMapper;

    //knn
//    @Test
//    void contextLoads1() {
////        FingerPrint testFinger = fingerTestMapper.selectById(10);
//        List<FingerPrint> testFingers = fingerTestMapper.selectAll();
//        List<FingerPrint> trainDatas = knn.getTrainDatas();
//        List<Double> errors=new ArrayList<>();
//        for (FingerPrint testFinger : testFingers) {
//            knn.calDistances(trainDatas,testFinger);
//            List<FingerPrint> neighbors = knn.nearestNeighbors(trainDatas, 8);
//            List<FingerPrint> kNeighbors = knn.calNearestNeighborsWeight(neighbors, testFinger);
//            double v = knn.distError(kNeighbors, testFinger);
//            errors.add(v);
//            System.out.println("--------------------------------------------------------------");
//        }
//
//        double sumError=0;
//        for (Double error : errors) {
//            sumError+=error;
//        }
//        System.out.println(" ------Æ½¾ùÎó²î-------------");
//        System.out.println(sumError/errors.size());
//
//    }



   // kmeans
    @Test
    void contextLoads() {
        int classNum=3;
        List<FingerPrint> fingers = fingertrainMapper.selectBetweenId(20,26);
//        List<FingerPrint> fingers =fingertrainMapper.selectAll();
        List<FingerPrint> centralPoints = kmeans.randomPoint(fingers, classNum);
        kmeans.KmwansClusting(fingers,centralPoints,classNum,true);
        List<FingerPrint> fingerPrints = fingerClusterMapper.selectAll();
//           fingerClusterMapper.updateCluster(fingers);
    }



    }




