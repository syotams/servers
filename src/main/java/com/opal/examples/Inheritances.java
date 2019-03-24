package com.opal.examples;

public class Inheritances {

    interface A { void print(); }
    interface B { void print(Car c); }
    interface C {
        String print(Motor m); // no clash with interface A / B, different signature and return type is ok
        void print(); // no clashes with interface A::print for they have same signature and return type
        // String print(); // CLASH with interface A::print for they have same signature and different return type
    }

    interface Printable extends A, B, C {}

    public static void main(String[] args) {
        Printable print = new Printable() {
            @Override
            public String print(Motor m) {
                System.out.println(m);
                return m.toString();
            }

            @Override
            public void print(Car c) {
                System.out.println(c);
            }

            @Override public void print() {
                System.out.println("Yohoo");
            }
        };

        print.print();
        print.print(new Car());
        print.print(new Motor());
    }
}
