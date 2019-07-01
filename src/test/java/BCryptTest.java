import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

public class BCryptTest {



    @Test
    public void test(){
        String encoded=new BCryptPasswordEncoder().encode("1");
        System.out.println(encoded);
    }

}
