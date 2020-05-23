package com.gregorriegler.seamer.demos;

import com.gregorriegler.seamer.Seamer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class AspectJSeamTest {

    public static final String SEAM_ID = "AspectJ";

    @BeforeAll
    void recordInvocations() {
        Seamer.reset(SEAM_ID);

        AspectJDemo aspectJDemo = new AspectJDemo();
        aspectJDemo.blackbox("hello", 1);
        aspectJDemo.blackbox("world", 2);
        aspectJDemo.doNotProxyThis("don't seam me!");
        aspectJDemo.blackbox("hello", 3);
        aspectJDemo.blackbox("world", 4);
    }

    public static class AspectJDemo {

        public String doNotProxyThis(String arg1) {
            return arg1;
        }

        @com.gregorriegler.seamer.core.annotation.Seam(SEAM_ID)
        public String blackbox(String arg1, Integer arg2) {
            return arg1 + arg2;
        }
    }

    @Test
    void verify() {
        Seamer.verify(SEAM_ID);
    }
}