package security;

import org.apache.commons.codec.digest.DigestUtils;

public class LoginBuilderForGoogle {

    public static String build(String name, String email){
        StringBuilder stringBuilder = new StringBuilder(name);
        String hash = DigestUtils.md5Hex(email).substring(0,4);
        System.out.println(hash);
        stringBuilder.append("#");
        stringBuilder.append(hash);
        return stringBuilder.toString();
    }

}
