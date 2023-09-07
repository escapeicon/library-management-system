package com.bjpowernode.module.charts;

import com.bjpowernode.service.ChartService;
import com.bjpowernode.service.impl.ChartServiceImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.scene.chart.PieChart.Data;

/**
 * @author admin
 */
public class PieChart implements Initializable {

    @FXML
    private javafx.scene.chart.PieChart pieChart;

    private ChartService chartService = new ChartServiceImpl();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //��ȡ����
        Map<String, Integer> typeCountMap = chartService.countPie();

        //ת��ΪSet���϶���
        Set<Map.Entry<String,Integer>> entries =  typeCountMap.entrySet();

        Data[] data = new Data[entries.size()];
        int index = 0;
        //����ȡ�������ݷ���Data������
        for(Map.Entry<String,Integer> per : entries){
            data[index++] = new Data(per.getKey(),per.getValue());
        }

        ObservableList<javafx.scene.chart.PieChart.Data> pieChartData = FXCollections.observableArrayList(data);
        pieChart.setData(pieChartData);
        pieChart.setClockwise(false);
    }

    public static void main(String[] args){
        PieChart pieChart = new PieChart();
    }
}
