package com.example.dermacare.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await

class AuthRepository {

    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    // Get current logged in user
    val currentUser: FirebaseUser? get() = firebaseAuth.currentUser

    // Register new user
    suspend fun registerUser(email: String, password: String): Result<FirebaseUser> {

        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            Result.success(result.user!!)
        } catch (e: FirebaseAuthWeakPasswordException) {
            Result.failure(Exception("Password is too weak. Use at least 6 characters"))
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            Result.failure(Exception("Invalid email format"))
        } catch (e: FirebaseAuthUserCollisionException) {
            Result.failure(Exception("An account with this email already exists"))
        } catch (e: Exception) {
            if (e.message?.contains("network") == true) {
                Result.failure(Exception("No internet connection. Please try again"))
            } else {
                Result.failure(Exception("Registration failed. Please try again"))
            }
        }
    }
    // Google Sign-In
    suspend fun signInWithGoogle(idToken: String): Result<FirebaseUser> {
        return try {
            val credential = com.google.firebase.auth.GoogleAuthProvider.getCredential(idToken, null)
            val result = firebaseAuth.signInWithCredential(credential).await()
            Result.success(result.user!!)
        } catch (e: Exception) {
            Result.failure(Exception("Google Sign-In failed. Please try again"))
        }
    }
    // Login existing user
    suspend fun loginUser(email: String, password: String): Result<FirebaseUser> {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            Result.success(result.user!!)
        }catch (e: FirebaseAuthInvalidUserException) {
            Result.failure(Exception("No account found with this email"))
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            Result.failure(Exception("Incorrect password. Please try again"))
        } catch (e: Exception) {
            if (e.message?.contains("network") == true) {
                Result.failure(Exception("No internet connection. Please try again"))
            } else {
                Result.failure(Exception("Login failed. Please try again"))
            }
        }
    }

    // Logout user
    fun logoutUser() {
        firebaseAuth.signOut()
    }
}