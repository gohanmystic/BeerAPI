package com.BeerAPI.common.support;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SigningKeyResolver;
import io.jsonwebtoken.SigningKeyResolverAdapter;
import io.jsonwebtoken.impl.TextCodec;
import io.jsonwebtoken.impl.crypto.MacProvider;
import lombok.Getter;

@Component
public class SecretSupport {

    private static final String HS512_ALGORITHM = SignatureAlgorithm.HS512.getValue();

    @Getter
    private final Map<String, String> secrets = new ConcurrentHashMap<>();

    @PostConstruct
    public void setup() {
        refreshSecrets();
    }

    public byte[] getHS512SecretBytes() {
        return TextCodec.BASE64.decode(secrets.get(HS512_ALGORITHM));
    }

    public Map<String, String> refreshSecrets() {

        final SecretKey key = MacProvider.generateKey(SignatureAlgorithm.HS512);
        secrets.put(HS512_ALGORITHM, TextCodec.BASE64.encode(key.getEncoded()));

        return secrets;
    }

    @Getter
    private SigningKeyResolver signingKeyResolver = new SigningKeyResolverAdapter() {

        @Override
        public byte[] resolveSigningKeyBytes(@SuppressWarnings("rawtypes") JwsHeader header, Claims claims) {

            return TextCodec.BASE64.decode(secrets.get(header.getAlgorithm()));
        }
    };
}
