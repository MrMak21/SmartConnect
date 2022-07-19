package gr.makris.smartconnect.model.error

import com.google.gson.annotations.SerializedName
import gr.makris.smartconnect.model.common.Model
import gr.makris.smartconnect.response.base.CustomError

open class CommonResponse(
    @SerializedName("status") val status: String? = Model.INVALID_STRING,
    @SerializedName("timestamp") val timestamp: String? = Model.INVALID_STRING,
    @SerializedName("errors") val errors: List<CustomError>? = null,

//    @SerializedName("error") val error: String? = Model.INVALID_STRING,
//    @SerializedName("error_description") val error_description: String? = Model.INVALID_STRING,
//    @SerializedName("uuid") val uuid: String? = null,
//    @SerializedName("resumeOptions") val resumeOptions: List<String>? = null,
//    @SerializedName("processInstanceId") val processInstanceId: String? = null,
) {
    companion object {
        const val FIELD_NAME_ORIGINAL_UUID = "originalUuid"

        const val RESUME_OPTION_OTP = "OTP"
        const val RESUME_OPTION_LOTP = "LOTP"

        const val WORKFLOW_ERROR_CODE: String = "WFL008"
        const val RETRY_ERROR_CODE: String = "AUTHZ0413"
    }

    //In case of SCA verification, uuid is different from rfUuid when a successful POST resume is executed so we inject the original uuid of the payment manually
    private var originalUuid: String? = null

//    fun needsScaAuthorization(): Boolean {
//        return resumeOptions != null && !uuid.isNullOrBlank()
//    }
//
//    fun needsApproval(): Boolean {
//        return resumeOptions == null && !uuid.isNullOrBlank()
//    }
//
//    fun canBeScaVerified(): Boolean {
//        return !resumeOptions.isNullOrEmpty() && !getSelectedResumeOption().isBlank() && !uuid.isNullOrBlank()
//    }

    fun isWorkFlowCase(): Boolean {
        return getErrorCode() == WORKFLOW_ERROR_CODE
    }

//    fun getSelectedResumeOption(): String {
//        if (resumeOptions?.contains(RESUME_OPTION_OTP) == true) {
//            return RESUME_OPTION_OTP
//        }
//        if (resumeOptions?.contains(RESUME_OPTION_LOTP) == true) {
//            return RESUME_OPTION_LOTP
//        }
//        return ""
//    }
//
//
//    fun getOriginalUuid(): String {
//        return if (originalUuid?.trim().isNullOrBlank()) {
//            uuid
//        } else {
//            originalUuid
//        } ?: ""
//    }
//
//    fun setOriginalUuid(originalUuid: String?) {
//        this.originalUuid = originalUuid
//    }
//
//    fun getScaUuid(): String {
//        return uuid ?: ""
//    }

    fun getErrorCode(): String {
        return if (errors != null && errors.isNotEmpty()) {
            errors[0].code
        } else {
            return ""
        } ?: ""
    }

//    open fun isSuccess(): Boolean {
//        return errors == null && error == null
//    }
}