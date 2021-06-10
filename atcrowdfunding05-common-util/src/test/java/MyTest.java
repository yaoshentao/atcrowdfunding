import com.atguigu.crowd.util.CrowdUtil;
import org.junit.Test;

/**
 * ClassName:    MyTest
 * Package:    PACKAGE_NAME
 * Description:
 * Datetime:    2021/5/31   22:02
 * Author:   yaoshentao
 */
public class MyTest {

    @Test
    public void testMD5(){
        String source = "123123";
        String encode = CrowdUtil.md5(source);
        System.out.println(encode);
    }

    @Test
    public void Mytest2(){
        StudentTest s = new StudentTest();
        //StudentTest.Child c = new StudentTest.Child();
    }
}
