package gr.makris.smartconnect.retrofit

import gr.makris.smartconnect.retrofit.NetworkClientFactory.getRetrofitInstance

class NetworkProviderImpl: NetworkProvider {

    private val smartConnectApi: ApiInterface = getRetrofitInstance()


}