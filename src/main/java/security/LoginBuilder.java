package security;

import org.apache.commons.codec.digest.DigestUtils;

public class LoginBuilder {

    public static String build(String email){
        StringBuilder stringBuilder = new StringBuilder();
        String hash = DigestUtils.md5Hex(email).substring(0,4);
        int indexOf = email.indexOf('@');
        stringBuilder.append(email.substring(0,indexOf).replace('.','_'));
        stringBuilder.append(hash);
        return stringBuilder.toString();
    }

}
