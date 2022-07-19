package gr.makris.smartconnect.model.users

data class UserModel(
    var userId: String,
    var firstname: String,
    var lastname: String,
    var email: String,
    var password: String,
    var enabled: Boolean,
    var provider: String?
)
