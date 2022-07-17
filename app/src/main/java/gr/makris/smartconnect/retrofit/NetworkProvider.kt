package gr.makris.smartconnect.retrofit

import gr.makris.smartconnect.data.user.User
import gr.makris.smartconnect.model.common.DataResult
import gr.makris.smartconnect.model.common.DataResultErrorModel
import gr.makris.smartconnect.model.common.SmartConnectErrorModel
import gr.makris.smartconnect.response.users.GetUsersResponse

interface NetworkProvider {

    suspend fun getUsersAsync(): DataResult<GetUsersResponse, SmartConnectErrorModel>


}