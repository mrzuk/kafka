/**
 * Created by U0145084 on 2016-12-21.
 */
public class TestMessageClass {
	String test1;
	String test2;

	TestMessageClass(String val1, String val2){
		this.test1 = val1;
		this.test2 = val2;
	}

	public boolean equals(TestMessageClass obj){
		return this.test1.equals(obj.test1) && this.test2.equals(obj.test2);
	}
}