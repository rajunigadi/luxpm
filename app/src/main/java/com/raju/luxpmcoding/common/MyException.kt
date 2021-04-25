package com.raju.luxpmcoding.common

class SignInException : Exception() {
    override val message: String
        get() = "Sign in failed"
}

class SignUpException : Exception() {
    override val message: String
        get() = "Sign up failed"
}

class InvalidNameException : Exception() {
    override val message: String
        get() = "Please enter valid name"
}

class InvalidMobileException : Exception() {
    override val message: String
        get() = "Please enter valid mobile number"
}

class InvalidDobException : Exception() {
    override val message: String
        get() = "Please enter valid date of birth"
}

class InvalidEmailException : Exception() {
    override val message: String
        get() = "Please enter valid email"
}

class InvalidPasswordException : Exception() {
    override val message: String
        get() = "Please enter valid password"
}

class InvalidConfirmPasswordException : Exception() {
    override val message: String
        get() = "Password & confirm password doesn't match."
}

class TermsException : Exception() {
    override val message: String
        get() = "Please accept terms & conditions"
}