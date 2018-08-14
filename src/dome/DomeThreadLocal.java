package dome;

import org.junit.Test;

//这个可以解决资源不需要共享的多线程并发问题。
public class DomeThreadLocal {
	@Test
	public void fun1() {
		ThreadLocal<String> tl = new ThreadLocal<String>();// tl有多个线程的副本
		tl.set("gaoxin");// 注意只能存一个值。tl这个对象其实是一个map，主键是当前的线程，
		String a = tl.get();//其他的线程取不到这个值
		System.out.println(a);
	}
}
