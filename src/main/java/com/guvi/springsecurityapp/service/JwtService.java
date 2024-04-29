package com.guvi.springsecurityapp.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

//service we need to use token generation
@Component
public class JwtService {

    private  static  final String SECRET="ehejfiuhdhdhfhsgffkfdh374sfgdjfkgg76jhududy3gdgdhfhfhfh";

    //method for extracting username
    public String extractUsername(String token){
        return  extractClaim(token, Claims::getSubject);
    }


        //method to extract expiration date from token
      public Date extractExpiration(String token){
          return  extractClaim(token, Claims::getExpiration);

      }

    private <T> T extractClaim(String token, Function<Claims,T> claimsResolver) {
            //to extract information from token
        //all information
       final Claims claims = extractAllClaims(token);
       return claimsResolver.apply(claims);

    }


    public  Claims extractAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignkey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    //method to check if token expired
    private  Boolean isTokenExpired(String token){
        //check if token expired with current data and time .
        return  extractExpiration(token).before(new Date());
    }


    //method-validateToken,generateToken,createToken
    public  Boolean validateToken(String token, UserDetails userDetails){
      final  String username = extractUsername(token);
      //compare user details 7 check if token is expired or not
        return  (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    //create method to generate token
    public String generateToken(String username){
        //create an map to store information of token

        Map<String, Object> claims=new HashMap<>();
        return createToken(claims,username)
    }

    private String createToken(Map<Strging, Object> claims, String username) {
        return  Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*30))
                .signWith(getSignkey(), SignatureAlgorithm.HS256)
                .compact();

    }

    private Key getSignkey() {
        byte[] keyBytes= Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
