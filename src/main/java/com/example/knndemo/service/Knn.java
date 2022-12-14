package com.example.knndemo.service;

import com.example.knndemo.mapper.FingerTestMapper;
import com.example.knndemo.mapper.FingertrainMapper;
import com.example.knndemo.model.FingerPrint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class Knn {

    @Autowired
    private FingerTestMapper fingerTestMapper;

    @Autowired
    private FingertrainMapper fingertrainMapper;



    //获得数据集内容
    public List<FingerPrint> getTrainDatas() {
        return fingertrainMapper.selectAll();
    }


    /**
     * 计算测试指纹到指纹数据库中指纹的距离
     * @param trainDatas
     * @param testData
     */
    public void calDistances(List<FingerPrint> trainDatas, FingerPrint testData) {
        double dist;
        for (FingerPrint finger : trainDatas) {
            dist = Math.pow(finger.getRssi1() - testData.getRssi1(),2) +
                    Math.pow(finger.getRssi2() - testData.getRssi2(),2) +
                    Math.pow(finger.getRssi3() - testData.getRssi3(),2) +
                    Math.pow(finger.getRssi4() - testData.getRssi4(),2) +
                    Math.pow(finger.getRssi5() - testData.getRssi5(),2) +
                    Math.pow(finger.getRssi6() - testData.getRssi6(),2) +
                    Math.pow(finger.getRssi7() - testData.getRssi7(),2) +
                    Math.pow(finger.getRssi8() - testData.getRssi8(),2);

            finger.setDistanct(Math.pow(dist,0.5));
        }
    }

    // k 个相邻点
    public List<FingerPrint> nearestNeighbors(List<FingerPrint> trainDatas, Integer k) {
        Collections.sort(trainDatas, (o1, o2) -> {
            return o1.getDistanct() - o2.getDistanct() > 0.0 ? 1 : -1;
        });

        List<FingerPrint> res = trainDatas.subList(0, k);

        return res;
    }

    public List<FingerPrint> calNearestNeighborsWeight(List<FingerPrint> neighbors, FingerPrint testFinger) {

        double sumDistance = 0;
        for (FingerPrint neighbor : neighbors) {
            if (isSumFinger(neighbor, testFinger)) {
                neighbor.setDistanct(0.0);
            }
            sumDistance += neighbor.getDistanct();
        }


        System.out.println(" k  neighbors--------------------------------------------------------"  );
        double kSum=0.0;
        for (FingerPrint neighbor : neighbors) {
           kSum+=1/neighbor.getDistanct();
        }
        for (FingerPrint neighbor : neighbors) {
            neighbor.setWeight(1/ neighbor.getDistanct()/kSum);
        }
        return neighbors;

    }

    /**
     * 判断是否为同一指纹
     * @param o
     * @param z
     * @return
     */
    public boolean isSumFinger(FingerPrint o, FingerPrint z) {
        if (o.getRssi1() == z.getRssi1() && o.getRssi2() == z.getRssi2() && o.getRssi3() == z.getRssi3() && o.getRssi4() == z.getRssi4() &&
                o.getRssi5() == z.getRssi5() && o.getRssi6() == z.getRssi6() && o.getRssi7() == z.getRssi7() && o.getRssi8() == z.getRssi8()) {
            return true;
        }

        return false;
    }

    /**
     * 计算位置并输出
     * @param neighbors
     * @param testFinger
     * @return
     */
    public double distError(List<FingerPrint> neighbors, FingerPrint testFinger) {
        double prex = 0.0;
        double prey = 0.0;
        double error = 0.0;
        for (FingerPrint neighbor : neighbors) {
            prex += neighbor.getX() * neighbor.getWeight();
            prey += neighbor.getY() * neighbor.getWeight();
        }
        System.out.println("实际位置的 x坐标为："+testFinger.getX());
        System.out.println("实际位置的 y坐标为："+testFinger.getY());
        System.out.println("预计位置的 x坐标为："+prex);
        System.out.println("预计位置的 y坐标为："+prey);


        error = Math.pow((prex - testFinger.getX()) * (prex - testFinger.getX()) +
                (prey - testFinger.getY()) * (prey - testFinger.getY()),0.5);

        System.out.println("error为:"+ error);
        return error;

    }




}
