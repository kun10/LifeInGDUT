import org.junit.Test;


public class a {
	
	@Test
	public void test(){
		StringBuilder sb = new StringBuilder(); 
		System.out.println(sb.length());
		System.out.println(sb.substring(0, sb.length()-1));
		System.out.println(sb.toString());
	}
}
