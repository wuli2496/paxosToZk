package zk;

import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;

import java.security.NoSuchAlgorithmException;

public class DigestAuthenticationProvicerUsage
{
    public static void main(String[] args) throws NoSuchAlgorithmException
    {
        System.out.println(DigestAuthenticationProvider.generateDigest("foo:zk-book"));
    }
}
