package security;

import org.apache.commons.codec.digest.DigestUtils;

public class PasswordHasher {
    private static final String extraChars = "*^$@%_+(¬‿¬)(~˘▾˘)~";


    public static String hash(String login,String password) {
        return DigestUtils.md5Hex(login + password + extraChars);
    }

}
