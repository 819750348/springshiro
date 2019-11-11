package com.shiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MyRealm extends AuthorizingRealm {
    Map map = new HashMap<String, String>();

    {
        map.put("admin", "2cfd4560539f887a5e420412b370b361");
    }

    Set set = new HashSet<String>();

    {
        set.add("user");
    }

    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        String name = (String) principalCollection.getPrimaryPrincipal();

        Set<String> role = this.getRole(name);

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo(role);

        return authorizationInfo;
    }

    public Set getRole(String name) {
//               Iterator iterator=  set.iterator();
//               String role = "";
//               if(iterator.hasNext()){
//                    role=(String)iterator.next();
//               }
        return set;
    }

    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String name = (String) authenticationToken.getPrincipal();
        String password = this.getPassword(name);
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(name, password, "myRealm");
        simpleAuthenticationInfo.setCredentialsSalt(ByteSource.Util.bytes("1"));
        return simpleAuthenticationInfo;
    }

    public String getPassword(String name) {

        return (String) map.get(name);
    }

    public static void main(String[] args) {
        Md5Hash md5Hash = new Md5Hash("123", ByteSource.Util.bytes("1"));
        System.out.print(md5Hash);
    }
}
