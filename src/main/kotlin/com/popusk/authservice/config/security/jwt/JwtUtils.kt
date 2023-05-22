package com.popusk.authservice.config.security.jwt

import io.jsonwebtoken.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.nio.file.Files
import java.nio.file.Paths
import java.security.KeyFactory
import java.security.PrivateKey
import java.security.PublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import java.util.*


@Component
class JwtUtils(
    @Value("\${private.key.filename}")
    privateKeyFileName: String,
    @Value("\${public.key.filename}")
    publicKeyFileName: String,

    @Value("\${jwt.expiration-time:1800000}") //по деволту полчаса
    private val expirationTime: Long,
) {
    private val privateKey: PrivateKey
    private val publicKey: PublicKey

    init {
        val key = Files.readAllBytes(Paths.get(privateKeyFileName))
        val keyFactory = KeyFactory.getInstance("RSA")
        val keySpec = PKCS8EncodedKeySpec(key)
        privateKey = keyFactory.generatePrivate(keySpec)
    }

    init {
        val key = Files.readAllBytes(Paths.get(publicKeyFileName))
        val keyFactory = KeyFactory.getInstance("RSA")
        val keySpec = X509EncodedKeySpec(key)
        publicKey = keyFactory.generatePublic(keySpec)
    }

    fun generateToken(username: String): String {
        return generateToken(username, this.privateKey)
    }

    fun generateToken(username: String, privateKey: PrivateKey): String {
        val claims: MutableMap<String, Any?> = HashMap()
        claims["username"] = username
        val expirationTime = Date(Date().time + this.expirationTime)
        return Jwts.builder().setClaims(claims).setExpiration(expirationTime)
            .signWith(SignatureAlgorithm.RS512, privateKey).compact()
    }

    fun validateToken(authToken: String): Claims {
        try {
            return Jwts.parser().setSigningKey(this.publicKey).parseClaimsJws(authToken).body
        } catch (ex: SignatureException) {
            throw java.lang.IllegalArgumentException("Invalid JWT signature")
        } catch (ex: MalformedJwtException) {
            throw java.lang.IllegalArgumentException("Invalid JWT token")
        } catch (ex: ExpiredJwtException) {
            throw java.lang.IllegalArgumentException("Expired JWT token")
        } catch (ex: UnsupportedJwtException) {
            throw java.lang.IllegalArgumentException("Unsupported JWT token")
        } catch (ex: IllegalArgumentException) {
            throw java.lang.IllegalArgumentException("JWT claims string is empty.")
        }
    }
}
