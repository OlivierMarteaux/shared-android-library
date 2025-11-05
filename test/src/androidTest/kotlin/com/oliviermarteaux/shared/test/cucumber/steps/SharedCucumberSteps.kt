package com.oliviermarteaux.shared.test.cucumber.steps

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.oliviermarteaux.shared.test.di.ComposeRuleHolderInterface
import io.cucumber.java.en.And
import io.cucumber.java.en.Then
import io.cucumber.java.en.When

//_ This class requires picocontainer to inject dependency
/**
 * Provides reusable step definitions for UI testing with Jetpack Compose and Cucumber.
 *
 * This class is designed to interact with Compose UI elements through a [ComposeRuleHolderInterface],
 * enabling automated tests to perform actions and assertions in a human-readable, BDD-style format.
 *
 * ### Constructor:
 * @param composeRuleHolder An implementation of [ComposeRuleHolderInterface] that provides the
 * underlying `ComposeTestRule` used to query and interact with Compose nodes.
 *
 * ### Step Definitions:
 * - `iClickOnButton(fabLabel: String)`
 *   Clicks on a button or FAB with the given content description or label.
 *
 * - `iClickOnCard(cardText: String)`
 *   Clicks on a card with the specified text.
 *
 * - `iEnterText(name: String, textFieldLabel: String)`
 *   Enters the given text into a text field with the provided label.
 *
 * - `iShouldSeeTextInLastListItem(text: String, item: String)`
 *   Verifies that the last item in a list contains the specified text.
 *
 * - `iShouldSeeText(text: String)`
 *   Verifies that a text node (e.g., toast message) is displayed.
 *
 * - `iAmOnTheItemScreen(screen: String, item: String, itemName: String)`
 *   Asserts that the screen displays the item with the given name.
 *
 * ### Example Usage in Cucumber Feature File:
 * ```
 * When I click on the "Add" FAB button
 * And I enter "John Doe" in the "Name" field
 * Then I should see "John Doe" added at the end of the "Contacts" list
 * ```
 *
 * ### Notes:
 * - Relies on `contentDescription` or `text` to identify Compose nodes.
 * - Useful for end-to-end tests that interact with the Compose UI in a human-readable BDD style.
 */
class SharedCucumberSteps(private val composeRuleHolder: ComposeRuleHolderInterface) {

    private val composeRule = composeRuleHolder.composeRule

    @When("I click on the {string} FAB button")
    @When("I click on the {string} button")
    fun iClickOnButton(fabLabel: String) {
        // Use contentDescription or tag for your FABs
        composeRule.onNodeWithContentDescription(fabLabel).performClick()
    }

    @When("I click on the {string} card")
    fun iClickOnCard(cardText: String) {
        // Use contentDescription or tag for your FABs
        composeRule.onNodeWithText(cardText).performClick()
    }

    @When("I enter {string} in the {string} field")
    fun iEnterText(name:String, textFieldLabel: String) {
        composeRule.onNodeWithText(textFieldLabel).performTextInput(name)
    }

    @And("I should see {string} added at the end of the {string} list")
    fun iShouldSeeTextInLastListItem(text: String, item: String){
        composeRule.onNodeWithText(text).assertIsDisplayed()
        val itemsNodes = composeRule.onAllNodes(hasClickAction())
        val lastItemNode = itemsNodes[itemsNodes.fetchSemanticsNodes().size - 2]
        lastItemNode.assertTextContains(text)
    }

    @And("I should see a toast message {string}")
    fun iShouldSeeText(text: String) {
        composeRule.onNodeWithText(text).assertIsDisplayed()
    }

    @Then("I should arrive on the {string} screen for the {string} item named {string}")
    fun iAmOnTheItemScreen(screen: String, item: String, itemName: String, ) {
        composeRule.onNodeWithText(itemName).assertIsDisplayed()
    }
}