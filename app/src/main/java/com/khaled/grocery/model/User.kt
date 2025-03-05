import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("name") private var _name: String,
    @SerializedName("email") private var _email: String,
    @SerializedName("password") private var _password: String,
    @SerializedName("phone") private var _phone: String
) {
    // Custom getters and setters for 'name'
    var name: String
        get() = _name
        set(value) {
            _name = value
        }

    // Custom getters and setters for 'email'
    var email: String
        get() = _email
        set(value) {
            _email = value
        }

    // Custom getters and setters for 'password'
    var password: String
        get() = _password
        set(value) {
            _password = value
        }

    // Custom getters and setters for 'phone'
    var phone: String
        get() = _phone
        set(value) {
            _phone = value
        }
}