package com.nju.edu.erp.service.Impl;

import com.nju.edu.erp.service.TaxCul;

import java.util.HashMap;
import java.util.Map;

public class TaxCulImpl implements TaxCul {
    private final Map<Integer, Double> PersonalTax = new HashMap<>();
    private final Map<Integer, Integer> TaxLevel = new HashMap<>();
    private final Integer standard = 5000;
    private final Integer[] fullTax = new Integer[]{90, 900,2600,2500,3000,8750};
    private final Double[] otherTax = new Double[]{0.02,0.05};
    private void setTaxMap(){
        PersonalTax.put(0,0.0);
        PersonalTax.put(5000,0.03);
        PersonalTax.put(8000,0.1);
        PersonalTax.put(17000,0.2);
        PersonalTax.put(30000,0.25);
        PersonalTax.put(40000,0.3);
        PersonalTax.put(60000,0.35);
        PersonalTax.put(85000,0.45);
    }
    private void setLevelMap(){
        TaxLevel.put(1,5000);
        TaxLevel.put(2,8000);
        TaxLevel.put(3,17000);
        TaxLevel.put(4,30000);
        TaxLevel.put(5,40000);
        TaxLevel.put(6,60000);
        TaxLevel.put(7,85000);
    }
    @Override
    public Integer Tax(Integer should_pay) {
        setTaxMap();
        setLevelMap();
        Double personal = 0.0;
        Double result = 0.0;
        Integer otherTax = OtherTax(should_pay);
        double TaxPercent = 0.0;
        if (should_pay<=TaxLevel.get(1)){
            return otherTax;
        }else {
           Integer real = should_pay - standard;
           if (should_pay < TaxLevel.get(2)){
               TaxPercent = PersonalTax.get(TaxLevel.get(1));
               personal = real * TaxPercent;
           }else
            if (should_pay > TaxLevel.get(2) && should_pay < TaxLevel.get(3)){
                Integer fulltax = fullTax[0];
                real = should_pay - fulltax - standard;
                TaxPercent = PersonalTax.get(TaxLevel.get(2));
                personal = real * TaxPercent + fulltax;
            }else
            if (should_pay > TaxLevel.get(3) && should_pay < TaxLevel.get(4)){
                Integer fulltax = fullTax[1]+fullTax[0];
                real = should_pay - fulltax - standard;
                TaxPercent = PersonalTax.get(TaxLevel.get(3));
                personal = real * TaxPercent + fulltax;
            }else
            if (should_pay > TaxLevel.get(4) && should_pay < TaxLevel.get(5)){
                Integer fulltax = fullTax[2]+fullTax[1]+fullTax[0];
                real = should_pay - fulltax - standard;
                TaxPercent = PersonalTax.get(TaxLevel.get(4));
                personal = real * TaxPercent + fulltax;
            }else
            if (should_pay > TaxLevel.get(5) && should_pay < TaxLevel.get(6)){
                Integer fulltax = fullTax[3]+fullTax[2]+fullTax[1]+fullTax[0];
                real = should_pay - fulltax - standard;
                TaxPercent = PersonalTax.get(TaxLevel.get(5));
                personal = real * TaxPercent + fulltax;
            }else
            if (should_pay > TaxLevel.get(6) && should_pay < TaxLevel.get(7)){
                Integer fulltax = fullTax[4]+fullTax[3]+fullTax[2]+fullTax[1]+fullTax[0];
                real = should_pay - fulltax - standard;
                TaxPercent = PersonalTax.get(TaxLevel.get(6));
                personal = real * TaxPercent + fulltax;
            }else
            if (should_pay > TaxLevel.get(7)){
                Integer fulltax = fullTax[5]+fullTax[4]+fullTax[3]+fullTax[2]+fullTax[1]+fullTax[0];
                real = should_pay - fulltax - standard;
                TaxPercent = PersonalTax.get(TaxLevel.get(7));
                personal = (real * TaxPercent) + fulltax;
            }
        }
        result = personal + otherTax;
        System.out.println(result);
        return result.intValue();
    }

    private Integer OtherTax(Integer should_pay){
        Double result = 0.0;
        result = should_pay * (otherTax[0] + otherTax[1]);
        return result.intValue();
    }
}
