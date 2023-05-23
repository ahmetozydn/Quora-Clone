package com.kernelsoft.quora_clone.presentation.login

import android.annotation.SuppressLint
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.kernelsoft.quora_clone.MainActivity
import com.kernelsoft.quora_clone.R
import com.kernelsoft.quora_clone.data.model.User
import com.kernelsoft.quora_clone.ui.theme.DarkRed
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Register(navController: NavController, onFinishActivity: () -> Unit) {

    val name = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val confirmPassword = remember { mutableStateOf("") }

    val isNameFieldEmpty = remember { mutableStateOf(false) }
    val isEmailFieldEmpty = remember { mutableStateOf(false) }
    val isPasswordFieldEmpty = remember { mutableStateOf(false) }
    val isConfirmPasswordFieldEmpty = remember { mutableStateOf(false) }

    val passwordVisibility = remember { mutableStateOf(false) }
    val confirmPasswordVisibility = remember { mutableStateOf(false) }

    val showSnackBar = remember { mutableStateOf(false) }
    val snackBarMessage = remember { mutableStateOf("") }

    val auth = FirebaseAuth.getInstance()
    val contex = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    Scaffold(scaffoldState = scaffoldState) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
                contentAlignment = Alignment.TopCenter
            ) {
                Image(painter = painterResource(R.drawable.img_1), contentDescription = "image")
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.70f)
                    .clip(RoundedCornerShape(30.dp))
                    .background(Color.White)
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Sign Up", fontSize = 30.sp,
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 2.sp
                        )
                    )
                    Spacer(modifier = Modifier.padding(20.dp))
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        OutlinedTextField(
                            value = name.value,
                            onValueChange = {

                                name.value = it
                                if (isNameFieldEmpty.value) {
                                    isNameFieldEmpty.value = false
                                }
                            },
                            label = { Text(text = "Name") },
                            placeholder = { Text(text = "Name") },
                            singleLine = true,
                            isError = isNameFieldEmpty.value,
                            modifier = Modifier.fillMaxWidth(0.8f)
                        )

                        OutlinedTextField(
                            value = email.value,
                            onValueChange = {
                                if (isEmailFieldEmpty.value) {
                                    isEmailFieldEmpty.value = false
                                }
                                email.value = it
                            },
                            label = { Text(text = "Email Address") },
                            placeholder = { Text(text = "Email Address") },
                            singleLine = true,
                            isError = isEmailFieldEmpty.value,
                            modifier = Modifier.fillMaxWidth(0.8f)
                        )
                        OutlinedTextField(
                            value = password.value,
                            onValueChange = {
                                if (isPasswordFieldEmpty.value) {
                                    isPasswordFieldEmpty.value = false
                                }
                                password.value = it
                            },
                            label = { Text(text = "Password") },
                            placeholder = { Text(text = "Password") },
                            singleLine = true,
                            isError = isPasswordFieldEmpty.value,
                            modifier = Modifier.fillMaxWidth(0.8f),
                            trailingIcon = {
                                IconButton(onClick = {
                                    passwordVisibility.value = !passwordVisibility.value
                                }) {
                                    Icon(
                                        imageVector = ImageVector.vectorResource(R.drawable.password_hide),
                                        tint = if (passwordVisibility.value) MaterialTheme.colors.primary else Color.Gray,
                                        contentDescription = "hide password"
                                    )
                                }
                            },
                            visualTransformation = if (passwordVisibility.value) VisualTransformation.None
                            else PasswordVisualTransformation()
                        )

                        OutlinedTextField(
                            value = confirmPassword.value,
                            onValueChange = {
                                if (isConfirmPasswordFieldEmpty.value) {
                                    isConfirmPasswordFieldEmpty.value = false
                                }
                                confirmPassword.value = it
                            },
                            label = { Text(text = "Confirm Password") },
                            placeholder = { Text(text = "Confirm Password") },
                            singleLine = true,
                            isError = isConfirmPasswordFieldEmpty.value,
                            modifier = Modifier.fillMaxWidth(0.8f),
                            trailingIcon = {
                                IconButton(onClick = {
                                    confirmPasswordVisibility.value = !confirmPasswordVisibility.value
                                }) {
                                    Icon(
                                        imageVector = ImageVector.vectorResource(R.drawable.password_hide),
                                        tint = if (confirmPasswordVisibility.value) MaterialTheme.colors.primary else Color.Gray,
                                        contentDescription = "hide password"
                                    )
                                }
                            },
                            visualTransformation = if (confirmPasswordVisibility.value) VisualTransformation.None
                            else PasswordVisualTransformation()
                        )
                        Spacer(modifier = Modifier.padding(10.dp))
                        Button(
                            onClick = {
                                if (name.value.trim().isEmpty() || password.value.trim().isEmpty() || email.value.trim().isEmpty() || confirmPassword.value.trim().isEmpty()) {
                                    if (name.value.trim().isEmpty()) { isNameFieldEmpty.value = true }
                                    if (email.value.trim().isEmpty()) { isEmailFieldEmpty.value = true }
                                    if (password.value.trim().isEmpty()) { isPasswordFieldEmpty.value = true }
                                    if (confirmPassword.value.isEmpty()) { isConfirmPasswordFieldEmpty.value = true }
                                    snackBarMessage.value = "Empty Fields!"
                                    showSnackBar.value = true
                                    return@Button
                                }
                                if(password.value.trim() != confirmPassword.value.trim()){
                                    snackBarMessage.value = "Passwords doesn't match."
                                    showSnackBar.value = true
                                    return@Button
                                }
                                auth.createUserWithEmailAndPassword(email.value.trim(),password.value.trim()).addOnCompleteListener{ task ->
                                    if(task.isSuccessful){
                                        val user = FirebaseAuth.getInstance().currentUser
                                        val sampleUser = User(userName = name.value,email.value)

                                        user?.let { firebaseUser ->
                                            val userData = hashMapOf(
                                                "user_name" to name,
                                                "mail" to email
                                            )
                                            val db = Firebase.firestore
                                            db.collection("users")
                                                .document(firebaseUser.uid)    // firebaseUser.uid                                                  // firebaseUser.uid
                                                .set(sampleUser)
                                                .addOnSuccessListener {
                                                    snackBarMessage.value = "firestore successfully stored the user"
                                                    showSnackBar.value = true
                                                    // User account and data saved successfully
                                                    // Proceed with any additional actions or navigation
                                                    val intent = Intent(contex,MainActivity::class.java)
                                                    contex.startActivity(intent)
                                                    snackBarMessage.value = "Successful!"
                                                    showSnackBar.value = true
                                                    onFinishActivity.invoke()
                                                }
                                                .addOnFailureListener { exception ->
                                                    val message = exception.message
                                                    snackBarMessage.value = message.toString()
                                                    showSnackBar.value = true
                                                    // Failed to save user data
                                                    // Handle the error or display an error message to the user
                                                }
                                        }


                                    }else{
                                        val exception = task.exception as? FirebaseAuthException
                                        val errorMessage = exception?.message
                                        showSnackBar.value = true
                                        errorMessage.let {
                                            if (it != null) {
                                                snackBarMessage.value = it
                                            }
                                        }
                                    }
                                }

                                /*if(emailValue.value.isNotEmpty() && passwordValue.value.isNotEmpty()){
                             auth.createUserWithEmailAndPassword(emailValue.value, passwordValue.value).addOnCompleteListener { task ->
                                 if(task.isSuccessful){
                                     //finishActivity.value = true
                                     Toast.makeText(contex,"successful!",Toast.LENGTH_LONG).show()

                                     *//*  val user = FirebaseAuth.getInstance().currentUser
                                          user?.let { firebaseUser ->
                                              val userData = hashMapOf(
                                                  "username" to username,
                                                  "email" to email
                                              )
                                              val db = Firebase.firestore
                                              db.collection("users")
                                                  .document(firebaseUser.uid)
                                                  .set(userData)
                                                  .addOnSuccessListener {
                                                      // User account and data saved successfully
                                                      // Proceed with any additional actions or navigation
                                                  }
                                                  .addOnFailureListener { exception ->
                                                      // Failed to save user data
                                                      // Handle the error or display an error message to the user
                                                  }
                                          }*//*
                                    }else{
                                        val exception = task.exception
                                        Toast.makeText(contex,"fail",Toast.LENGTH_LONG).show()                                    }
                                }
                            }*/
                            }, modifier = Modifier
                                .fillMaxWidth(0.8f)
                                .background(DarkRed)
                                .height(50.dp)
                        ) {
                            Text(text = "Sign Up", fontSize = 20.sp)
                        }
                        Spacer(modifier = Modifier.padding(20.dp))
                        Text(
                            text = "Login Instead",
                            modifier = Modifier.clickable(onClick = {
                                /*navController.navigate("login_page"){
                                    popUpTo = navController.graph.startDestination
                                    launchSingleTop = true
                                }*/
                                navController.popBackStack()
                            })
                        )
                        Spacer(modifier = Modifier.padding(20.dp))

                    }

                }
            }
        }
    }
    if(showSnackBar.value){
        LaunchedEffect(Unit){
            coroutineScope.launch {
                val job = coroutineScope.launch {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = snackBarMessage.value,
                        duration = SnackbarDuration.Indefinite,
                    )
                }
                delay(3000)
                job.cancel()
            }
        }
        showSnackBar.value = false
    }

}
