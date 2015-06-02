import org.junit.Test;


public class a {
	
	public void test(){
		StringBuilder sb = new StringBuilder(); 
		System.out.println(sb.length());
		System.out.println(sb.substring(0, sb.length()-1));
		System.out.println(sb.toString());
	}
	
	@Test
	public void test2(){
		String str ="haha1;haha2;haha3";
		int index = str.indexOf(';');
		if(index!=-1){
			String str1 = str.substring(0, index);
			System.out.println(str1);
			str = str.substring(index+1, str.length());
			index = str.indexOf(';');
			if(index!=-1){
				String str2 = str.substring(0, index);
				System.out.println(str2);
				str = str.substring(index+1, str.length());
			}
		}
		System.out.println(str);
		
		
	}
	
//	public String getIndex(String str){
//		int index = str.indexOf(';');
//		System.out.println(str.substring(0, index));
//		
//	}
}
