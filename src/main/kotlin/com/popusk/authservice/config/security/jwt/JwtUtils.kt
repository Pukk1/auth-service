package com.popusk.authservice.config.security.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.nio.file.Files
import java.nio.file.Paths
import java.security.KeyFactory
import java.security.KeyPairGenerator
import java.security.PrivateKey
import java.security.spec.PKCS8EncodedKeySpec


@Component
class JwtUtils(
    @Value("\${private.key.filename}")
    privateKeyFileName: String,
//    private val publicKeyFileName: String,
) {
//    final val ALGORITHM = "RSA"
    private val privateKey: PrivateKey

    init {
//        Base64.getDecoder().decode(privateKeyStr)
//        var pkcs8Pem: String = privateKeyStr
//        pkcs8Pem = pkcs8Pem.replace("-----BEGIN PRIVATE KEY-----", "")
//        pkcs8Pem = pkcs8Pem.replace("-----END PRIVATE KEY-----", "")
//        pkcs8Pem = pkcs8Pem.replace("\\s+".toRegex(), "")
//        val pkcs8EncodedBytes: ByteArray = Base64.getDecoder().decode(pkcs8Pem)
//        val keySpec = PKCS8EncodedKeySpec(pkcs8EncodedBytes)
//        privateKey = KeyFactory.getInstance(ALGORITHM).generatePrivate(keySpec)

        val key = Files.readAllBytes(Paths.get(privateKeyFileName))
        val keyFactory = KeyFactory.getInstance("RSA")
        val keySpec = PKCS8EncodedKeySpec(key)
        privateKey = keyFactory.generatePrivate(keySpec)
    }

//    private val logger = LoggerFactory.getLogger(this::class.java)

    fun generateToken(username: String): String {
        return generateToken(username, this.privateKey)
    }

    fun generateToken(username: String, privateKey: PrivateKey): String {
        val claims: MutableMap<String, Any?> = HashMap()
        claims["username"] = username
        return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.RS512, privateKey).compact()
    }

    fun extractUsername(authToken: String): String? {
        return validateJwtToken(authToken)["username"] as String
    }

    fun validateJwtToken(authToken: String): Claims {
        val claims = Jwts.parser().setSigningKey(this.privateKey).parseClaimsJws(authToken).body
        return claims
    }
}

//private fun getRSAKeys(): Map<String, Any> {
//    val keyPairGenerator = KeyPairGenerator.getInstance("RSA")
//    keyPairGenerator.initialize(2048)
//    val keyPair = keyPairGenerator.generateKeyPair()
//    val privateKey = keyPair.private
//    val publicKey = keyPair.public
//    val keys: MutableMap<String, Any> = HashMap()
//    keys["private"] = privateKey
//    keys["public"] = publicKey
//    return keys
//}