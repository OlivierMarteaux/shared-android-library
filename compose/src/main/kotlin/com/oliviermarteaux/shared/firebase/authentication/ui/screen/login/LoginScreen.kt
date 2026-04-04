package com.oliviermarteaux.shared.firebase.authentication.ui.screen.login

import android.R.attr.label
import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.oliviermarteaux.shared.composables.ImageScaffold
import com.oliviermarteaux.shared.composables.SharedButton
import com.oliviermarteaux.shared.composables.SharedOutlinedEmail
import com.oliviermarteaux.shared.composables.SharedOutlinedPassword
import com.oliviermarteaux.shared.composables.SharedOutlinedTextField
import com.oliviermarteaux.shared.composables.SharedScaffold
import com.oliviermarteaux.shared.composables.SharedToast
import com.oliviermarteaux.shared.compose.R
import com.oliviermarteaux.shared.extensions.isValidEmail
import com.oliviermarteaux.shared.firebase.authentication.domain.model.NewUser
import com.oliviermarteaux.shared.ui.theme.SharedPadding
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import com.oliviermarteaux.shared.composables.SharedAlertDialog

/**
 * A screen for logging in or creating an account.
 *
 * @param modifier The modifier to apply to this screen.
 * @param navigateToPasswordScreen A function to call to navigate to the password screen.
 * @param navigateToHomeScreen A function to call to navigate to the home screen.
 * @param onBackClick A function to call when the back button is clicked.
 * @param loginViewModel The view model for this screen.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    logoDrawableRes: Int,
    modifier: Modifier = Modifier,
    navigateToPasswordScreen: (String) -> Unit,
    navigateToHomeScreen: () -> Unit,
    onBackClick: () -> Unit = {},
    loginViewModel: LoginViewModel = hiltViewModel(),
    landscapeHorizontalPadding: Dp = 85.dp,
    landscapeCentralPadding: Dp = 85.dp,
    formPortraitHorizontalPadding: Dp = 24.dp,
    imageModifier: Modifier = Modifier.fillMaxWidth(),
    isNameRequested: Boolean = true,
    containerColor: Color = MaterialTheme.colorScheme.background,
    topAppBarColors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(),
){
    with (loginViewModel) {
        SharedScaffold(
            title = stringResource(R.string.sign_in),
            onBackClick = onBackClick,
            modifier = modifier,
            containerColor = containerColor,
            topAppBarColors = topAppBarColors,
        ) { innerPadding ->
            ImageScaffold(
                image = painterResource(id = logoDrawableRes),
                innerPadding = innerPadding,
                imageModifier = imageModifier,
                landscapeHorizontalPadding = landscapeHorizontalPadding,
                landscapeCentralPadding = landscapeCentralPadding,
                formPortraitHorizontalPadding = formPortraitHorizontalPadding
            ) {
                Box {
                    LoginBody(
                        newUser = newUser,
                        emailExist = emailExist,
                        isOnline = isOnline,
                        modifier = modifier
                            .padding(horizontal = SharedPadding.xl)
                            .fillMaxSize(),
                        onEmailChange = ::onEmailChange,
                        onFirstNameChange = ::onFirstNameChange,
                        onLastNameChange = ::onLastNameChange,
                        onPasswordChange = ::onPasswordChange,
                        createAccount = ::createAccount,
                        checkEmail = ::checkEmail,
                        navigateToHomeScreen = navigateToHomeScreen,
                        navigateToPasswordScreen = navigateToPasswordScreen,
                        showNetworkErrorToast = ::showNetworkErrorToast,
                        onEmailExist = ::onEmailExist,
                        isNameRequested = isNameRequested
                    )
                    if (unknownError) SharedToast(text = stringResource(R.string.an_unknown_error_occurred))
                    if (networkError) SharedToast(
                        text = stringResource(R.string.network_error_check_your_internet_connection),
                        bottomPadding = 120
                    )
                    if (accountCreationError) SharedToast(
                        text = stringResource(R.string.email_account_registration_unsuccessful),
                        bottomPadding = 80
                    )
                    if (emailNotVerifiedError) SharedToast(
                        text = stringResource(R.string.your_email_has_not_been_verified_yet) +"\n"+
                                stringResource(R.string.if_you_don_t_receive_the_email_try_another_email_provider_or_login_with_google),
                        bottomPadding = 80
                    )
                    AnimatedVisibility(emailVerification) {
                        SharedAlertDialog(
                            title = stringResource(R.string.please_verify_your_email),
                            text = stringResource(R.string.a_verification_email_has_been_sent_please_check_your_mailbox),
                            onConfirm = {
                                verifyEmail {
                                    hideEmailVerificationAlertDialog()
                                    navigateToHomeScreen()
                                }
                                        },
                            confirmText = stringResource(R.string.ok),
                            onDismiss = {
                                hideEmailVerificationAlertDialog()
                                onBackClick()
                            },
                            dismissText = stringResource(R.string.cancel)
                        )
                    }
                }
            }
        }
    }
}

/**
 * A composable for the body of the login screen.
 *
 * @param newUser The new user object.
 * @param emailExist A boolean indicating if the email exists.
 * @param isOnline A boolean indicating if the device is online.
 * @param modifier The modifier to apply to this composable.
 * @param onEmailChange A function to call when the email changes.
 * @param onFirstNameChange A function to call when the first name changes.
 * @param onLastNameChange A function to call when the last name changes.
 * @param onPasswordChange A function to call when the password changes.
 * @param createAccount A function to call to create an account.
 * @param checkEmail A function to call to check if an email exists.
 * @param navigateToHomeScreen A function to call to navigate to the home screen.
 * @param navigateToPasswordScreen A function to call to navigate to the password screen.
 * @param showNetworkErrorToast A function to call to show a network error toast.
 * @param onEmailExist A function to call when the email exists.
 */
@Composable
private fun LoginBody(
    newUser: NewUser,
    emailExist: Boolean?,
    isOnline: Boolean,
    modifier: Modifier = Modifier,
    onEmailChange: (String) -> Unit,
    onFirstNameChange: (String) -> Unit,
    onLastNameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    createAccount: (NewUser/*, () -> Unit*/) -> Unit,
    checkEmail: (String) -> Unit,
    navigateToHomeScreen: () -> Unit,
    navigateToPasswordScreen: (String) -> Unit,
    showNetworkErrorToast: () -> Unit,
    onEmailExist: (() -> Unit)-> Unit,
    isNameRequested: Boolean,
){
    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT
    // Remember scroll state only if portrait
    val scrollState = rememberScrollState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .windowInsetsPadding(WindowInsets(0, 0, 0, 0))
            .then(
                if (isPortrait) Modifier.verticalScroll(scrollState)
                else Modifier // do nothing in landscape
            )
    ) {
        Spacer(Modifier.height(24.dp))
        SharedOutlinedEmail(
            value = newUser.email,
            onValueChange = { onEmailChange(it) },
            label = stringResource(R.string.email),
            imeAction = ImeAction.Done,
            textFieldModifier = Modifier.fillMaxWidth(),
            bottomPadding = SharedPadding.xl,
            isError = newUser.email.isEmpty() || !newUser.email.isValidEmail() || newUser.email.endsWith("gmail.com", ignoreCase = true),
            errorText = when {
                newUser.email.isEmpty() -> stringResource(R.string.enter_your_email_address_to_continue)
                !newUser.email.isValidEmail() -> stringResource(R.string.incorrect_email_address)
                newUser.email.endsWith("gmail.com", ignoreCase = true) -> stringResource(R.string.please_use_sign_in_with_google_with_your_gmail_address)
                else -> null
            },
            modifier = Modifier.fillMaxWidth()
        )
        when {
            emailExist == null -> {
                Spacer(modifier = Modifier.height(SharedPadding.xl))
                SharedButton(
                    onClick = { if (isOnline) checkEmail(newUser.email) else showNetworkErrorToast() },
                    text = stringResource(R.string.next),
                    modifier = Modifier.fillMaxWidth(),
                    enabled = newUser.email.run { isValidEmail() && isNotEmpty() && !endsWith("gmail.com", ignoreCase = true)}
                )
            }

            emailExist -> {
                onEmailExist { navigateToPasswordScreen(newUser.email) }
            }

            !emailExist -> {

                val firstNameFocusRequester = remember { FocusRequester() }

                LaunchedEffect(Unit) { firstNameFocusRequester.requestFocus() }

                if (isNameRequested) {
                    SharedOutlinedTextField(
                        value = newUser.firstname,
                        onValueChange = { onFirstNameChange(it) },
                        label = stringResource(R.string.first_name),
                        isError = newUser.firstname.isEmpty(),
                        errorText = stringResource(R.string.please_enter_a_first_name),
                        bottomPadding = SharedPadding.xl,
                        modifier = Modifier
                            .focusRequester(firstNameFocusRequester)
                            .fillMaxWidth(),
                        imeAction = ImeAction.Next
                    )
                    SharedOutlinedTextField(
                        value = newUser.lastname,
                        onValueChange = { onLastNameChange(it) },
                        label = stringResource(R.string.last_name),
                        isError = newUser.lastname.isEmpty(),
                        errorText = stringResource(R.string.please_enter_a_last_name),
                        bottomPadding = SharedPadding.xl,
                        modifier = Modifier.fillMaxWidth(),
                        imeAction = ImeAction.Next,
                    )
                }
                SharedOutlinedPassword(
                    value = newUser.password,
                    onValueChange = { onPasswordChange(it) },
                    label = stringResource(R.string.new_password),
                    errorText = stringResource(R.string.password_is_not_strong_enough_use_at_least_6_characters_and_a_mix_of_letters_numbers_and_a_special_character),
                    passwordSetting = true,
                    modifier = Modifier.fillMaxWidth(),
                    bottomPadding = SharedPadding.xxl,
                    imeAction = ImeAction.Done,
                )
                SharedButton(
//                    onClick = { createAccount(newUser) { navigateToHomeScreen() } },
                    onClick = { createAccount(newUser) /*{ navigateToPasswordScreen(newUser.email) }*/ },
                    text = stringResource(R.string.save),
                    modifier = Modifier
                        .padding(vertical = SharedPadding.xl)
                        .fillMaxWidth()
                )
            }
        }
    }
}