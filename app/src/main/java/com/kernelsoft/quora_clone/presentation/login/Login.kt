package com.kernelsoft.quora_clone.presentation.login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.kernelsoft.quora_clone.MainActivity
import com.kernelsoft.quora_clone.R
import com.kernelsoft.quora_clone.presentation.navigation.NavigationGraph
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class Login : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavigationGraph(navController)
        }
        // val finishActivity = remember { mutableStateOf(false) }
        /*LoginPage(this@Login,finishActivity)
         if(finishActivity.value){
             val intent = Intent(this@Login, MainActivity::class.java)
             this@Login.startActivity(intent)
             finish()
         }
     }*/
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginPage(navController: NavController) { //login: Login, finishActivity: MutableState<Boolean>

    //val keyboardController = LocalSoftwareKeyboardController.current
    val passwordValue = remember { mutableStateOf("") }
    val emailValue = remember { mutableStateOf("") }

    val passwordVisibility = remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

    val isPasswordTextFieldEmpty = remember { mutableStateOf(false) }
    val isEmailTextFieldEmpty = remember { mutableStateOf(false) }

    var isButtonClicked by remember { mutableStateOf(false) }
    val showSnackBar = remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    val auth = FirebaseAuth.getInstance()
    val contex = LocalContext.current
    val snackBarMessage = remember { mutableStateOf("") }
    Scaffold (scaffoldState = scaffoldState){
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White), contentAlignment = Alignment.TopCenter
            ) {

                Image(painter = painterResource(R.drawable.img), contentDescription = "image")
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.60f)
                    .clip(RoundedCornerShape(30.dp)) //RoundedCornerShape(topLeft = 30.dp, topRight = 30.dp)
                    .background(Color.White)
                    .padding(10.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {//scrollablecolumn
                    Text(
                        text = "Sign In",
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        ),
                        fontSize = 30.sp
                    )
                    Spacer(modifier = Modifier.padding(20.dp))
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        OutlinedTextField(
                            value = emailValue.value,
                            onValueChange = {
                                emailValue.value = it
                                if (isEmailTextFieldEmpty.value) {
                                    isEmailTextFieldEmpty.value = false
                                }
                            },
                            label = { Text(text = "Email") },
                            isError = isEmailTextFieldEmpty.value,
                            placeholder = { Text(text = "Email Address", fontSize = 16.sp) },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(0.8f),
                            /*   onImeActionPerformed = { _, _ ->
                                   focusRequester.requestFocus()
                               }*/
                        )

                        OutlinedTextField(
                            value = passwordValue.value,
                            onValueChange = {
                                passwordValue.value = it
                                if (isPasswordTextFieldEmpty.value) {
                                    isPasswordTextFieldEmpty.value = false
                                }
                            },
                            trailingIcon = {
                                IconButton(onClick = {
                                    passwordVisibility.value = !passwordVisibility.value
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Favorite, // password_hide
                                        tint = if (passwordVisibility.value) MaterialTheme.colors.primary else Color.Gray,
                                        contentDescription = "null"
                                    )
                                }
                            },
                            isError = isPasswordTextFieldEmpty.value,
                            label = { Text("Password") },
                            placeholder = { Text(text = "Password", fontSize = 16.sp) },
                            singleLine = true,
                            visualTransformation = if (passwordVisibility.value) VisualTransformation.None
                            else PasswordVisualTransformation(),
                            modifier = Modifier
                                .fillMaxWidth(0.8f)
                                .focusRequester(focusRequester = focusRequester),
                            /*  onImeActionPerformed = { _, controller ->
                                  controller?.hideSoftwareKeyboard()
                              }*/
                        )
                        Spacer(modifier = Modifier.padding(10.dp))
                        Button(
                            enabled = !isButtonClicked,
                            onClick = {
                                if (emailValue.value.isEmpty() || passwordValue.value.isEmpty()) {
                                    if (emailValue.value.isEmpty()) {
                                        isEmailTextFieldEmpty.value = true
                                    }
                                    if (passwordValue.value.isEmpty()) {
                                        isPasswordTextFieldEmpty.value = true
                                    }
                                    snackBarMessage.value = "Empty Fields"
                                    showSnackBar.value = true
                                    return@Button
                                }
                                if (!isButtonClicked) {
                                    isButtonClicked = true
                                    auth.signInWithEmailAndPassword(
                                        emailValue.value,
                                        passwordValue.value
                                    )
                                        .addOnCompleteListener { task ->
                                            if (task.isSuccessful) {
                                                // successful
                                                val user = auth.currentUser                                                // successful
                                                val intent = Intent(contex, MainActivity::class.java)
                                                contex.startActivity(intent) //TODO(finish activity)
                                            } else {
                                                // failed
                                                isButtonClicked = false
                                                val exception = task.exception as? FirebaseAuthException
                                                val errorMessage = exception?.message
                                                snackBarMessage.value = errorMessage.toString()
                                                showSnackBar.value = true
                                            }
                                        }
                                }

                            },
                            modifier = Modifier
                                .fillMaxWidth(0.8f)
                                .height(50.dp)
                        ) {
                            Text(text = "Sign In", fontSize = 20.sp)
                        }
                        Spacer(modifier = Modifier.padding(20.dp))
                        Text(
                            text = "Create An Account",
                            modifier = Modifier.clickable(onClick = {
                                navController.navigate("register")
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
                delay(2500)
                job.cancel()
            }

        }
        showSnackBar.value = false
    }
}



