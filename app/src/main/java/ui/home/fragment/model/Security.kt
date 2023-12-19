package ui.home.fragment.model

import android.text.TextUtils
import android.util.Base64
import java.io.IOException
import java.lang.IllegalArgumentException
import java.security.InvalidKeyException
import java.security.KeyFactory
import java.security.NoSuchAlgorithmException
import java.security.PublicKey
import java.security.Signature
import java.security.SignatureException
import java.security.spec.InvalidKeySpecException
import java.security.spec.X509EncodedKeySpec

class Security {
    private val KEY_FACTORY_ALGORITHM = "RSA"
    private val SIGNATURE_ALGORITHM = "SHA1withRSA"

    fun verifyPurchase(base64PublicKey:String?, signedData:String, signature:String?):Boolean{
        if(TextUtils.isEmpty(signedData) || TextUtils.isEmpty(base64PublicKey) || TextUtils.isEmpty(signature)){
            return false
        }

        val key = generatePublicKey(base64PublicKey)
        return verify(key, signedData, signature)
    }

    private fun verify(publicKey: PublicKey, signedData: String, signature: String?):Boolean {
        val signatureBytes: ByteArray = try{
            Base64.decode(signature,Base64.DEFAULT)
        }catch(e:IllegalArgumentException){
            return false
        }

        try {
            val signatureAlgorithm = Signature.getInstance(SIGNATURE_ALGORITHM)
            signatureAlgorithm.initVerify(publicKey)
            signatureAlgorithm.update(signedData.toByteArray())
            return signatureAlgorithm.verify(signatureBytes)
        }catch(e: NoSuchAlgorithmException){
            throw RuntimeException(e)
        }catch (e: InvalidKeyException){

        }catch (e :SignatureException){

        }

        return  false
    }

    private fun generatePublicKey(encodedPublicKey: String?): PublicKey {
        return try{
            val decodedKey = Base64.decode(encodedPublicKey, Base64.DEFAULT)
            val keyFactory = KeyFactory.getInstance(KEY_FACTORY_ALGORITHM)
            keyFactory.generatePublic((X509EncodedKeySpec(decodedKey)))
        }catch (e: NoSuchAlgorithmException){
            throw RuntimeException(e)
        }catch (e:InvalidKeySpecException){
            val msg = "Invalid key specification: $e"
            throw IOException(msg)
        }
    }
}