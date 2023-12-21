package dat.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import dat.ApplicationConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;

public class TokenFactory {

    // Singleton
    private static TokenFactory instance;

    // Properties
    private final String ISSUER = Objects.requireNonNull(getProperties())[0];
    private final String TOKEN_EXPIRE_TIME = Objects.requireNonNull(getProperties())[1];
    private final String SECRET_KEY = Objects.requireNonNull(getProperties())[2];

    // Logger
    private final Logger LOGGER = LoggerFactory.getLogger(TokenFactory.class);

    // SignToken class
    private final SignVerifyToken signature = new SignVerifyToken(ISSUER, TOKEN_EXPIRE_TIME, SECRET_KEY);

    public static TokenFactory getInstance() {
        if (instance == null) {
            instance = new TokenFactory();
        }
        return instance;
    }

    // Get properties from pom file
    private String[] getProperties() {
        try {
            String[] properties = new String[3];
            properties[0] = ApplicationConfig.getProperty("issuer");
            properties[1] = ApplicationConfig.getProperty("token.expiration.time");
            properties[2] = ApplicationConfig.getProperty("secret.key");
            return properties;
        } catch (IOException e) {
            LOGGER.error("Could not get properties", e);
        }
        return null;
    }

    public String[] parseJsonObject(String jsonString, Boolean tryLogin) throws Exception {
        try {


            ObjectMapper mapper = new ObjectMapper();
            Map json = mapper.readValue(jsonString, Map.class);
            String username = json.get("username").toString();
            String password = json.get("password").toString();


            return new String[]{username, password};

        } catch (JsonProcessingException | NullPointerException e){
            throw new Exception("Could not parse json object");
        }


    }

    public String createToken(String userName) {

        try {

            Date date = new Date();
            return signature.signToken(userName, date);
        } catch (JOSEException e) {
            throw new RuntimeException("Signing failed", e);
        }
    }

    public UserDTO verifyToken(String token)  {
        try {
            SignedJWT signedJWT = signature.parseTokenAndVerify(token);
            JWTClaimsSet claimsSet = signedJWT.getJWTClaimsSet();
            return signature.getJWTClaimsSet(claimsSet);
        } catch (ParseException | JOSEException e) {

            throw new RuntimeException("Token is expired");
        }
    }
}
