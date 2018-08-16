package org.molecule.demo;

import org.springframework.stereotype.Service;

/**
 * @author Dong Zhuming
 */
@Service
public class MyTestService extends BaseTestService {
    @Override
    public void doSomething() {
        System.out.println("My work");
    }
}
