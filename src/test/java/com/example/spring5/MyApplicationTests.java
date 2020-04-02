package com.example.spring5;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

//@SpringBootTest
//@SpringBootConfiguration
@RunWith(SpringRunner.class)
class MyApplicationTests {

	@BeforeEach
	void setup(){
		System.out.println("setup");
	}


	@BeforeAll
	static void setup2(){
		System.out.println("setup2");
	}

	@Before
	public void setup3(){
		System.out.println("setup3");
	}

	@After
	void clean(){
		System.out.println("clean");
	}

	@Test
	void test1() {
		System.out.println("test1");
	}
	@Test
	void test2() {
		System.out.println("test2");
	}

}
