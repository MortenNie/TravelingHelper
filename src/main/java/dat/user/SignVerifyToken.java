package dat.user;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;


import java.text.ParseException;
import java.util.Date;


public class SignVerifyToken {

    private final String ISSUER, TOKEN_EXPIRE_TIME, SECRET_KEY;

    public SignVerifyToken(String ISSUER, String TOKEN_EXPIRE_TIME, String SECRET_KEY) {
        this.ISSUER = ISSUER;
        this.TOKEN_EXPIRE_TIME = TOKEN_EXPIRE_TIME;
        this.SECRET_KEY = SECRET_KEY;
    }

    public String signToken(String userName, Date date) throws JOSEException {
        JWTClaimsSet claims = createClaims(userName, date);
        JWSObject jwsObject = createHeaderAndPayload(claims);
        return signTokenWithSecretKey(jwsObject);
    }

    private JWTClaimsSet createClaims(String username, Date date) {
        return new JWTClaimsSet.Builder()
                .subject(username)
                .issuer(ISSUER)
                .claim("username", username)
                .expirationTime(new Date(date.getTime() + Integer.parseInt(TOKEN_EXPIRE_TIME)))
                .build();
    }

    private JWSObject createHeaderAndPayload(JWTClaimsSet claimsSet) {
        return new JWSObject(new JWSHeader(JWSAlgorithm.HS256), new Payload(claimsSet.toJSONObject()));
    }

    private String signTokenWithSecretKey(JWSObject jwsObject) {
        try {
            JWSSigner signer = new MACSigner(SECRET_KEY.getBytes());
            jwsObject.sign(signer);
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException("Signing failed", e);
        }
    }

    public SignedJWT parseTokenAndVerify(String token) throws ParseException, JOSEException{
        SignedJWT signedJWT = SignedJWT.parse(token);
        JWSVerifier verifier = new MACVerifier(SECRET_KEY.getBytes());

        if (!signedJWT.verify(verifier)) {
            throw new JOSEException("Invalid token signature");
        }
        return signedJWT;
    }

    public UserDTO getJWTClaimsSet(JWTClaimsSet claimsSet)  {

        if (new Date().after(claimsSet.getExpirationTime()))
            throw new RuntimeException("Token is expired");

        String username = claimsSet.getClaim("username").toString();


        return new UserDTO(username);
    }

}