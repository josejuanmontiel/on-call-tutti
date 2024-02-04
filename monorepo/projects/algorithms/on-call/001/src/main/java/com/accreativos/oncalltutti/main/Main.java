package com.accreativos.oncalltutti.main;

import com.accreativos.oncalltutti.greeter.Greeter;

class Main {
    public static void main(String[] args) {
        Greeter greeter = new Greeter();
        System.out.println(greeter.greet());
    }
}
