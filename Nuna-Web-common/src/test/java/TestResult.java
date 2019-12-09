import com.liuqi.nuna.common.e.DataResultConvertException;
import com.liuqi.nuna.common.res.DataResult;
import com.liuqi.nuna.core.security.NunaUser;

import java.util.ArrayList;
import java.util.List;

/**
 * 类说明 <br>
 * <p>
 * 构造说明 :
 * <pre>
 *
 * </pre>
 *
 * @author : alexliu
 * @version v1.0 , Create at 12:24 PM 2019/12/9
 */
public class TestResult {


    public void test1(){


        DataResult res = new DataResult();

        res.setData("string","string123");
        res.setData("int",1);
        res.setData("boolean",false);

        NunaUser user = new NunaUser();
        user.setAccount("11111");
        res.setData("obj" , user);

        List<NunaUser> list = new ArrayList<>();
        list.add(user);

        user = new NunaUser();
        user.setAccount("2222");
        list.add(user);

        res.setData("list" , list);


        try{
            System.out.println(res.getDataAsString("string"));
            System.out.println(res.getDataAsInteger("int"));
            System.out.println(res.getDataAsBoolean("boolean"));
            System.out.println(res.getDataAsObject("obj" , NunaUser.class));
            System.out.println(res.getDataAsList("list" , NunaUser.class));
        }catch (DataResultConvertException e){
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        new TestResult().test1();
    }
}
