package ui.home.fragment

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.android.billingclient.api.AcknowledgePurchaseParams
import com.android.billingclient.api.AcknowledgePurchaseResponseListener
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingFlowParams
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.ConsumeParams
import com.android.billingclient.api.ConsumeResponseListener
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.PurchasesUpdatedListener
import com.android.billingclient.api.QueryProductDetailsParams
import com.android.billingclient.api.QueryPurchasesParams
import com.fbf.common.base.BaseCommonFragment
import com.fbf.powerofbrain.R
import com.fbf.powerofbrain.databinding.FragmentHomeBinding
import com.google.android.gms.ads.AdRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ui.awards.fragment.AwardsFragment
import ui.home.fragment.model.Security
import ui.play.fragment.PlayFragment
import ui.settinigs.fragment.SettingsFragment
import util.Constants
import util.Preferance
import java.io.IOException
import java.util.concurrent.Executors

class HomeFragment : BaseCommonFragment(), PurchasesUpdatedListener {
    private lateinit var binding: FragmentHomeBinding
    private val playFragment = PlayFragment()
    private val settingsFragment = SettingsFragment()
    private val awardsFragment = AwardsFragment()
    private lateinit var billingClient: BillingClient
    var responce: String? = null
    var des: String? = null
    var sku: String? = null
    var isSuccess = false
    var isRemoveAdds = false

    private val purchasesUpdatedListener =
        PurchasesUpdatedListener { billingResult, purchases ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && purchases != null) {
                for (purchase in purchases) {

                }
            } else if (billingResult.responseCode == BillingClient.BillingResponseCode.ITEM_ALREADY_OWNED) {
                Toast.makeText(requireActivity(), "Item already owned", Toast.LENGTH_SHORT).show()
            } else if (billingResult.responseCode == BillingClient.BillingResponseCode.FEATURE_NOT_SUPPORTED) {
                Toast.makeText(requireActivity(), "Feature not supported", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(
                    requireActivity(),
                    "Error ${billingResult.debugMessage}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        val view = binding.root

        soundClick =
            MediaPlayer.create(requireContext(), R.raw.sound_click)
        soundEnable = Preferance.getBooleanPreferance(requireActivity(), Constants.SOUND_ENABLE)

        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)

        billingClient = BillingClient.newBuilder(requireActivity())
            .setListener(purchasesUpdatedListener)
            .enablePendingPurchases()
            .build()

        queryPurchase()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.playButton.setOnClickListener(this)
        binding.settingsImageView.setOnClickListener(this)
        binding.awardsImageView.setOnClickListener(this)
        binding.adsImageView.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        if (soundEnable) {
            soundClick.start()
        }
        when (view) {
            binding.playButton -> {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.mainFrameLayout, playFragment)
                    .addToBackStack("")
                    .commit()
            }

            binding.settingsImageView -> {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.mainFrameLayout, settingsFragment)
                    .addToBackStack("")
                    .commit()
            }

            binding.awardsImageView -> {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.mainFrameLayout, awardsFragment)
                    .addToBackStack("")
                    .commit()
            }

            binding.adsImageView -> {
                billingClient.startConnection(object : BillingClientStateListener {
                    override fun onBillingServiceDisconnected() {

                    }

                    override fun onBillingSetupFinished(p0: BillingResult) {
                        val productList = listOf(
                            QueryProductDetailsParams.Product.newBuilder()
                                .setProductId("")
                                .setProductType(BillingClient.ProductType.SUBS)
                                .build()
                        )

                        val params = QueryProductDetailsParams.newBuilder()
                            .setProductList(productList)
                        billingClient.queryProductDetailsAsync(params.build()) { billingResult, productDetailsList ->

                            for (productDetails in productDetailsList) {
                                val offerToken =
                                    productDetails.subscriptionOfferDetails?.get(0)?.offerToken
                                val productDetailsParamsList = listOf(
                                    offerToken?.let {
                                        BillingFlowParams.ProductDetailsParams.newBuilder()
                                            .setProductDetails(productDetails)
                                            .setOfferToken(it)
                                            .build()
                                    }
                                )
                                val billingFlowParams = BillingFlowParams.newBuilder()
                                    .setProductDetailsParamsList(productDetailsParamsList)
                                    .build()
                                val billingResult = billingClient.launchBillingFlow(
                                    requireActivity(),
                                    billingFlowParams
                                )

                            }
                        }
                    }

                })
            }
        }
    }

    override fun onPurchasesUpdated(result: BillingResult, listOfPurchase: MutableList<Purchase>?) {

    }

    fun handlePurchase(purchase: Purchase) {
        val consumeParams = ConsumeParams.newBuilder()
            .setPurchaseToken(purchase.purchaseToken)
            .build()
        val listener = ConsumeResponseListener { billingResult, s ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {

            }
        }
        billingClient.consumeAsync(consumeParams, listener)
        if (purchase.purchaseState == Purchase.PurchaseState.PURCHASED) {
            if (!varifyValidSignature(purchase.originalJson, purchase.signature)) {
                Toast.makeText(requireActivity(), "Error: invalid Purchase", Toast.LENGTH_SHORT)
                    .show()
                return
            }
            if (!purchase.isAcknowledged) {
                val acknowledgePurchaseParams = AcknowledgePurchaseParams
                    .newBuilder()
                    .setPurchaseToken(purchase.purchaseToken)
                    .build()
                billingClient.acknowledgePurchase(
                    acknowledgePurchaseParams,
                    acknowledgePurchaseResponceListener
                )
                Toast.makeText(requireActivity(), "Subscribed!!!!", Toast.LENGTH_SHORT).show()
                isSuccess = true
                Toast.makeText(requireActivity(), "GONNNEEEE", Toast.LENGTH_SHORT).show()

            } else {
                Toast.makeText(requireActivity(), "Already subscribed!!!!!!", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    var acknowledgePurchaseResponceListener = AcknowledgePurchaseResponseListener { billingResult ->

        if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
            Toast.makeText(requireActivity(), "Subscribed", Toast.LENGTH_SHORT).show()
            isSuccess = true
        }
    }

    private fun varifyValidSignature(signedData: String, signature: String): Boolean {
        return try {
            val base64Key = ""
            val security = Security()
            security.verifyPurchase(base64Key, signedData, signature)
        } catch (e: IOException) {
            false
        }
    }

    private fun getPrice() {
        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingServiceDisconnected() {

            }

            override fun onBillingSetupFinished(billingResult: BillingResult) {
                val executorService = Executors.newSingleThreadExecutor()
                executorService.execute {
                    val productList = listOf(
                        QueryProductDetailsParams.Product.newBuilder()
                            .setProductId("")
                            .setProductType(BillingClient.ProductType.SUBS)
                            .build()
                    )
                    val params = QueryProductDetailsParams.newBuilder().setProductList(productList)
                    billingClient.queryProductDetailsAsync(params.build()) { billingResult, productDetailsList ->
                        for (productDetails in productDetailsList) {
                            responce =
                                productDetails.subscriptionOfferDetails?.get(0)?.pricingPhases?.pricingPhaseList?.get(
                                    0
                                )?.formattedPrice
                            sku = productDetails.name
                            val ds = productDetails.description
                            des = "$sku : $ds : price: $responce"
                        }
                    }
                }

                lifecycleScope.launch(Dispatchers.Default) {
                    withContext(Dispatchers.IO) {
                        Thread.sleep(1000)
                    }
                    Toast.makeText(
                        requireActivity(),
                        "Error: $responce $des $sku",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }

        })
    }

    private fun queryPurchase() {
        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingServiceDisconnected() {

            }

            override fun onBillingSetupFinished(billingResult: BillingResult) {
                val executorService = Executors.newSingleThreadExecutor()
                executorService.execute {
                    lifecycleScope.launch(Dispatchers.Default) {
                        withContext(Dispatchers.IO) {
                            try {
                                billingClient.queryPurchasesAsync(
                                    QueryPurchasesParams.newBuilder()
                                        .setProductType(BillingClient.ProductType.SUBS).build()
                                ) { billingResult, purchaseList ->

                                    for (purchase in purchaseList) {
                                        if (purchase != null && purchase.isAcknowledged){
                                            isSuccess = true
                                            isRemoveAdds = true
                                            Toast.makeText(requireActivity(), "ADDDDDDDDD", Toast.LENGTH_SHORT).show()
                                            sku = purchase.originalJson.toString()
                                        }
                                    }
                                }

                            } catch (e: Exception) {
                                isRemoveAdds = false
                            }
                        }
                    }
                }

                lifecycleScope.launch(Dispatchers.Default) {
                    withContext(Dispatchers.IO) {
                        try {
                            Thread.sleep(1000)
                        } catch (e: InterruptedException) {
                            e.printStackTrace()
                        }
                    }
                }
            }

        })
    }

    override fun onDestroy() {
        super.onDestroy()
        billingClient.endConnection()
    }

}