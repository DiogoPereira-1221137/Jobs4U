package hello;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;

import org.junit.Test;

/**
	A comment
*/
public class GreeterTest4 {
	
	private Greeter4 greeter = new Greeter4();

	@Test
	public void greeterSaysHello() {
		assertThat(greeter.sayHello(), containsString("Hello world!"));
	}

}
