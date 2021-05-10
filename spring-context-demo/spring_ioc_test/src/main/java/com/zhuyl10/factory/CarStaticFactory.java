package com.zhuyl10.factory;

import com.zhuyl10.domain.Car;
import com.zhuyl10.domain.Motor;
import com.zhuyl10.domain.SteeringWheel;
import com.zhuyl10.domain.Tyre;

/**
 * @author zhuyl10
 */
public class CarStaticFactory {

    public static Car getCar(){
        Car c = new Car();
        c.setMotor(new Motor());
        c.setSteeringWheel(new SteeringWheel());
        c.setTyre(new Tyre());
        c.testMontor();
        c.testSteeringWheel();
        c.testType();
        return c;
    }
}
