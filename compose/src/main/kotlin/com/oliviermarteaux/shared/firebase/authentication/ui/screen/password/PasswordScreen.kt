package com.oliviermarteaux.shared.firebase.authentication.ui.screen.password

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.oliviermarteaux.shared.composables.ImageScaffold
import com.oliviermarteaux.shared.composables.SharedAlertDialog
import com.oliviermarteaux.shared.composables.SharedButton
import com.oliviermarteaux.shared.composables.SharedOutlinedPassword
import com.oliviermarteaux.shared.composables.SharedScaffold
import com.oliviermarteaux.shared.composables.SharedToast
import com.oliviermarteaux.shared.composables.extensions.cdButtonSemantics
import com.oliviermarteaux.shared.composables.spacer.SpacerLarge
import com.oliviermarteaux.shared.compose.R
import com.oliviermarteaux.shared.ui.theme.SharedPadding

/**
 * A screen for entering a password to sign in.
 *
 * @param modifier The modifier to apply to this screen.
 * @param navigateToHomeScreen A function to call to navigate to the home screen.
 * @param navigateToPasswordResetScreen A function to call to navigate to the password reset screen.
 * @param onBackClick A function to call when the back button is clicked.
 * @param passwordViewModel The view model for this screen.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordScreen(
    logoDrawableRes: Int,
    modifier: Modifier = Modifier,
    navigateToHomeScreen: () -> Unit,
    navigateToPasswordResetScreen: (String) -> Unit,
    onBackClick: () -> Unit = {},
    passwordViewModel: PasswordViewModel = hiltViewModel(),
    landscapeHorizontalPadding: Dp = 85.dp,
    landscapeCentralPadding: Dp = 85.dp,
    formPortraitHorizontalPadding: Dp = 24.dp,
    imageModifier: Modifier = Modifier.fillMaxWidth(),
    containerColor: Color = MaterialTheme.colorScheme.background,
    topAppBarColors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(),
){
    SharedScaffold(
        containerColor = containerColor,
        topAppBarColors = topAppBarColors,
        modifier = modifier,
        title = stringResource(R.string.sign_in),
        onBackClick = onBackClick
    ){ contentPadding ->
        Box {
            with (passwordViewModel) {
                PasswordBody(
                    logoDrawableRes = logoDrawableRes,
                    email = email.lowercase(),
                    password = password,
                    contentPadding = contentPadding,
                    onPasswordChange = ::onPasswordChange,
                    navigateToHomeScreen = navigateToHomeScreen,
                    navigateToPasswordResetScreen = navigateToPasswordResetScreen,
                    signIn = ::signIn,
                    modifier = modifier,
                    imageModifier = imageModifier,
                    landscapeHorizontalPadding = landscapeHorizontalPadding,
                    landscapeCentralPadding = landscapeCentralPadding,
                    formPortraitHorizontalPadding = formPortraitHorizontalPadding
                )
                if(unknownError) SharedToast(text = stringResource(R.string.an_unknown_error_occurred))
                if(networkError) SharedToast(
                    text = stringResource(R.string.network_error_check_your_internet_connection),
                    bottomPadding = 120
                )
                if(incorrectPassword)SharedToast(
                    text = stringResource(R.string.incorrect_password),
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

/**
 * A composable for the body of the password screen.
 *
 * @param email The user's email address.
 * @param password The user's password.
 * @param onPasswordChange A function to call when the password changes.
 * @param navigateToHomeScreen A function to call to navigate to the home screen.
 * @param navigateToPasswordResetScreen A function to call to navigate to the password reset screen.
 * @param signIn A function to call to sign in the user.
 */
@Composable
private fun PasswordBody(
    logoDrawableRes: Int,
    email: String,
    password: String,
    contentPadding: PaddingValues,
    onPasswordChange: (String) -> Unit,
    navigateToHomeScreen: () -> Unit,
    navigateToPasswordResetScreen: (String) -> Unit,
    signIn: (String, () -> Unit) -> Unit,
    modifier: Modifier,
    landscapeHorizontalPadding: Dp,
    landscapeCentralPadding: Dp,
    formPortraitHorizontalPadding: Dp,
    imageModifier: Modifier
) {
    ImageScaffold(
        image = painterResource(id = logoDrawableRes),
        modifier = modifier,
        imageModifier = imageModifier,
        landscapeHorizontalPadding = landscapeHorizontalPadding,
        landscapeCentralPadding = landscapeCentralPadding,
        formPortraitHorizontalPadding = formPortraitHorizontalPadding,
        innerPadding = contentPadding,
    ){

        val configuration = LocalConfiguration.current
        val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT
        // Remember scroll state only if portrait
        val scrollState = rememberScrollState()

        Column (
            modifier = Modifier
                .fillMaxSize()
                .then(
                    if (isPortrait) Modifier.verticalScroll(scrollState)
                    else Modifier // do nothing in landscape
                ),
        ){
            Spacer(Modifier.height(24.dp))
            Text(
                text = stringResource(
                    R.string.welcome_back_you_ve_already_used_to_sign_in_enter_your_password_for_that_account,
                    email
                ),
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(SharedPadding.medium))

            SharedOutlinedPassword(
                value = password,
                onValueChange = { onPasswordChange(it) },
                label = stringResource(R.string.password),
                imeAction = ImeAction.Done,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(SharedPadding.medium))

            val textTroubleSignIn = stringResource(R.string.trouble_signing_in)
            val cdTroubleSignIn = stringResource(
                R.string.button_double_tap_to_open_the_password_reset_screen,
                textTroubleSignIn
            )
            SharedButton(
                text = textTroubleSignIn,
                modifier = Modifier
                    .fillMaxWidth()
                    .cdButtonSemantics(cdTroubleSignIn)
            ) { navigateToPasswordResetScreen(email) }
            Spacer(modifier = Modifier.height(SharedPadding.medium))

            val textSignIn = stringResource(R.string.sign_in)
            val cdSignIn = stringResource(R.string.button_double_tap_to_sign_in, textSignIn)
            SharedButton(
                text = textSignIn,
                modifier = Modifier
                    .fillMaxWidth()
                    .cdButtonSemantics(cdSignIn)
            ) {
                signIn(password) { navigateToHomeScreen()
                } }
            SpacerLarge()
        }
    }
}