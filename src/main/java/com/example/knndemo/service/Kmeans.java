package com.example.knndemo.service;

import com.example.knndemo.mapper.FingerClusterMapper;
import com.example.knndemo.model.FingerPrint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Service
public class Kmeans {
    @Autowired
    private FingerClusterMapper fingerClusterMapper;

    //聚类的类别个数
    private int classNum;
    //聚类的类名称
    private ArrayList<String> classNames;
    //聚类的中心点
    private ArrayList<FingerPrint> classCentralPoints;
    //数据集中所有数据点
    private ArrayList<FingerPrint> points;

    /**
     * 随机初始化中心点
     */
    public List<FingerPrint> randomPoint(List<FingerPrint> fingerPrints,int classNum){
        int size = fingerPrints.size();
        List<FingerPrint> centralPoints = new ArrayList<>();
        for (int i = 0; i < classNum; i++) {
            Random random = new Random();  //是否使用随机种子Random(size)
            int index = random.nextInt(size);
            fingerPrints.get(index).setK(i);
            centralPoints.add(fingerPrints.get(index));


        }

        return centralPoints;
    }

    /**
     * kmeans聚类
     * @param points
     * @param classCentralPoints
     * @param isWrite  是否输出迭代结果
     */
    public void KmwansClusting(List<FingerPrint> points,List<FingerPrint> classCentralPoints,Integer classNum,boolean isWrite){

        int count = 0;
        double err = Integer.MAX_VALUE;
        int  iteration=0;
//err>0.01 * classNum&&
        while(iteration<=10){
            //计算数据集中各点到每个中心点的距离，并将其划分到最近那个中心点
            for (FingerPrint point : points) {
                for (FingerPrint classCentralPoint : classCentralPoints) {
                    double dist = kmeansDis(point, classCentralPoint);
                    classCentralPoint.setKmeansDistance(Math.pow(dist,0.5));
                }
                Collections.sort(classCentralPoints,(o1,o2)->o1.getKmeansDistance()-o2.getKmeansDistance()>0?1:-1);
                point.setK(classCentralPoints.get(0).getK());
            }

            //重新计算每个类的中心点
            err = 0;

            for (FingerPrint centralPoint : classCentralPoints) {
                count=0;
                double  tempRssi1=0.0;
                double  tempRssi2=0.0;
                double  tempRssi3=0.0;
                double  tempRssi4=0.0;
                double  tempRssi5=0.0;
                double  tempRssi6=0.0;
                double  tempRssi7=0.0;
                double  tempRssi8=0.0;


                for (FingerPrint point : points) {
                    if(centralPoint.getK()== point.getK()){
                        count++;
                        tempRssi1+=point.getRssi1();
                        tempRssi2+=point.getRssi2();
                        tempRssi3+=point.getRssi3();
                        tempRssi4+=point.getRssi4();
                        tempRssi5+=point.getRssi5();
                        tempRssi6+=point.getRssi6();
                        tempRssi7+=point.getRssi7();
                        tempRssi8+=point.getRssi8();


                    }

                }

//                err+=Math.pow(Math.pow(tempX- centralPoint.getX(),2)+Math.pow(tempY- centralPoint.getY(),2),0.5);
                centralPoint.setRssi1(tempRssi1/count);
                centralPoint.setRssi2(tempRssi2/count);
                centralPoint.setRssi3(tempRssi3/count);
                centralPoint.setRssi4(tempRssi4/count);
                centralPoint.setRssi5(tempRssi5/count);
                centralPoint.setRssi6(tempRssi6/count);
                centralPoint.setRssi7(tempRssi7/count);
                centralPoint.setRssi8(tempRssi8/count);
            }


            iteration++;
            //输出每次迭代内容
            if(isWrite) {
                System.out.println("第" + iteration + "次迭代结果");
                for (FingerPrint classCentralPoint : classCentralPoints) {
                    System.out.println("聚类结果为： x=" + classCentralPoint.getX() + "           y=" + classCentralPoint.getY());
                    System.out.println(classCentralPoint);
                }
            }
        }

        //输出最终结果
        for (FingerPrint classCentralPoint : classCentralPoints) {
//            System.out.println("聚类结果为： x=" + classCentralPoint.getX() + "           y=" + classCentralPoint.getY());
            System.out.println(classCentralPoint);
        }

        //插入到聚类表中
//        for (FingerPrint point : points) {
//            fingerClusterMapper.updateCluster(point);
//        }

    }

    /**
     * 计算两个特征的距离^2
     * @param point
     * @param centrolPoint
     */
    public double kmeansDis(FingerPrint point,FingerPrint centrolPoint){
        Double dist=null;
        dist = Math.pow(point.getRssi1() - centrolPoint.getRssi1(),2) +
                Math.pow(point.getRssi2() - centrolPoint.getRssi2(),2) +
                Math.pow(point.getRssi3() - centrolPoint.getRssi3(),2) +
                Math.pow(point.getRssi4() - centrolPoint.getRssi4(),2) +
                Math.pow(point.getRssi5() - centrolPoint.getRssi5(),2) +
                Math.pow(point.getRssi6() - centrolPoint.getRssi6(),2) +
                Math.pow(point.getRssi7() - centrolPoint.getRssi7(),2) +
                Math.pow(point.getRssi8() - centrolPoint.getRssi8(),2);

        return dist;
    }

}
