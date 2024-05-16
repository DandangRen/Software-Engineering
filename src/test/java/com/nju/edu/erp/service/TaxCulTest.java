package com.nju.edu.erp.service;

import com.nju.edu.erp.service.Impl.TaxCulImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

public class TaxCulTest {//税款计算的白盒测试

    @Autowired
    TaxCul taxCul;

    @Test
    @Transactional
    @Rollback(value = true)
    public void tax_test10(){//测试税款计算普通情况
        TaxCul taxCul = new TaxCulImpl();
        Assertions.assertEquals(1791,taxCul.Tax(13000));
    }

    @Test
    @Transactional
    @Rollback(value = true)
    public void tax_test11(){//测试税款计算普通情况
        TaxCul taxCul = new TaxCulImpl();
        Assertions.assertEquals(350,taxCul.Tax(5000));
    }

    @Test
    @Transactional
    @Rollback(value = true)
    public void tax_test12(){//测试税款计算普通情况
        TaxCul taxCul = new TaxCulImpl();
        Assertions.assertEquals(5192,taxCul.Tax(20000));
    }

    @Test
    @Transactional
    @Rollback(value = true)
    public void tax_test13(){//测试税款计算普通情况
        TaxCul taxCul = new TaxCulImpl();
        Assertions.assertEquals(12642,taxCul.Tax(35000));
    }

    @Test
    @Transactional
    @Rollback(value = true)
    public void tax_test14(){//测试税款计算普通情况
        TaxCul taxCul = new TaxCulImpl();
        Assertions.assertEquals(21263,taxCul.Tax(50000));
    }

    @Test
    @Transactional
    @Rollback(value = true)
    public void tax_test15(){//测试税款计算普通情况
        TaxCul taxCul = new TaxCulImpl();
        Assertions.assertEquals(35658,taxCul.Tax(75000));
    }

    @Test
    @Transactional
    @Rollback(value = true)
    public void tax_test2(){//测试税款计算下界情况
        TaxCul taxCul = new TaxCulImpl();
        Assertions.assertEquals(0,taxCul.Tax(0));
    }

    @Test
    @Transactional
    @Rollback(value = true)
    public void tax_test3(){//测试税款计算上界情况
        TaxCul taxCul = new TaxCulImpl();
        Assertions.assertEquals(1116699058,taxCul.Tax(Integer.MAX_VALUE));
    }
}
