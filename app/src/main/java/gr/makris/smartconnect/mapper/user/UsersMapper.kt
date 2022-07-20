package gr.makris.smartconnect.mapper.user

import gr.makris.smartconnect.data.user.User
import gr.makris.smartconnect.model.users.LoginUserModel
import gr.makris.smartconnect.model.users.UserModel
import gr.makris.smartconnect.response.users.GetUsersResponse
import gr.makris.smartconnect.response.users.LoginUserResponse

fun GetUsersResponse.toUsersListModel(): List<UserModel> {
    return this.users.map { it.toUserModel() }
}

fun User.toUserModel(): UserModel {
    return UserModel(
        userId = userId,
        firstname = firstname,
        lastname = lastname,
        email = email,
        password = password,
        enabled = enabled,
        provider = provider
    )
}

fun LoginUserResponse.toLoginUserModel(): LoginUserModel {
    return LoginUserModel(
        user = this.user.toUserModel(),
        accessToken = this.accessToken,
        refreshToken = this.refreshToken
    )
}