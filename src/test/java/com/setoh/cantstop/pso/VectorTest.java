package com.setoh.cantstop.pso;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.data.Percentage;
import org.junit.Test;

public class VectorTest {

    @Test
    public void testEmptyConstructor(){
        Vector v = new Vector();
        assertThat(v.getX()).isZero();
        assertThat(v.getY()).isZero();
        assertThat(v.getZ()).isZero();
    }

    @Test
    public void testConstructor(){
        Vector v = new Vector(1.,2.,3.);
        assertThat(v.getX()).isBetween(0.99,1.01);
        assertThat(v.getY()).isBetween(1.99,2.01);
        assertThat(v.getZ()).isBetween(2.99,3.01);
    }

    @Test
    public void testSetters(){
        Vector v = new Vector();
        v.set(1., 2., 3.);
        assertThat(v.getX()).isBetween(0.99,1.01);
        assertThat(v.getY()).isBetween(1.99,2.01);
        assertThat(v.getZ()).isBetween(2.99,3.01);
    }

    @Test
    public void testAdd(){
        Vector v1 = new Vector(3.,2.,1.);
        Vector v2 = new Vector(1., 2., 3.);
        v1.add(v2);
        assertThat(v1.getX()).isBetween(3.99,4.01);
        assertThat(v1.getY()).isBetween(3.99,4.01);
        assertThat(v1.getZ()).isBetween(3.99,4.01);
        assertThat(v2.getX()).isBetween(0.99,1.01);
        assertThat(v2.getY()).isBetween(1.99,2.01);
        assertThat(v2.getZ()).isBetween(2.99,3.01);
    }

    @Test
    public void testSub(){
        Vector v1 = new Vector(3.,2.,1.);
        Vector v2 = new Vector(1., 2., 3.);
        v1.sub(v2);
        assertThat(v1.getX()).isBetween(1.99,2.01);
        assertThat(v1.getY()).isBetween(-0.01,0.01);
        assertThat(v1.getZ()).isBetween(-2.01,-1.99);
        assertThat(v2.getX()).isBetween(0.99,1.01);
        assertThat(v2.getY()).isBetween(1.99,2.01);
        assertThat(v2.getZ()).isBetween(2.99,3.01);
    }

    @Test
    public void testMul(){
        Vector v1 = new Vector(3.,2.,1.);
        v1.mul(2);
        assertThat(v1.getX()).isBetween(5.99,6.01);
        assertThat(v1.getY()).isBetween(3.99,4.01);
        assertThat(v1.getZ()).isBetween(1.99,2.01);
    }

    @Test
    public void testDiv(){
        Vector v1 = new Vector(12.,8.,2.);
        v1.div(2);
        assertThat(v1.getX()).isBetween(5.99,6.01);
        assertThat(v1.getY()).isBetween(3.99,4.01);
        assertThat(v1.getZ()).isBetween(0.99,1.01);
    }

    @Test
    public void testNormalize(){
        Vector v1 = new Vector(4.,3.,0.);
        v1.normalize();
        assertThat(v1.getX()).isCloseTo(4./5., Percentage.withPercentage(0.01));
        assertThat(v1.getY()).isCloseTo(3./5., Percentage.withPercentage(0.01));
        assertThat(v1.getZ()).isCloseTo(0./5., Percentage.withPercentage(0.01));
    }

    @Test
    public void testLimit(){
        Vector v1 = new Vector(4.,3.,0.);
        v1.limit(1.);
        assertThat(v1.getX()).isCloseTo(4./5., Percentage.withPercentage(0.01));
        assertThat(v1.getY()).isCloseTo(3./5., Percentage.withPercentage(0.01));
        assertThat(v1.getZ()).isCloseTo(0./5., Percentage.withPercentage(0.01));
    }
}
