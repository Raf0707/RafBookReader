package raf.console.chitalka.presentation.start


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import raf.console.chitalka.R

@Composable
fun PrivacyPolicyScreen(
    navigateForward: () -> Unit,
) {
    var isAccepted by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            StartSettingsBottomBar(
                navigateForward = navigateForward,
                enabled = isAccepted
            )
        },
        containerColor = MaterialTheme.colorScheme.surface
    ) { paddingValues ->
        // Остальное содержимое экрана
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 16.dp)
        ) {
            // Заголовок и текст лицензии
            Text(
                text = stringResource(R.string.privacy_policy),
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            Text(
                text = """
Privacy Policy
Last updated: 16.03.2025

At RafBook, we take your privacy seriously. This Privacy Policy explains how we collect, use, and protect your personal information when you use our applications and services. By using our apps, you agree to the collection and use of information in accordance with this policy.

Information We Collect
We collect information to provide, maintain, and improve our applications and services. This may include:

- Device information (e.g., model, OS version, and unique identifiers).
- App usage data (e.g., session duration, features used).
- Crash reports for troubleshooting and stability improvements.

We use third-party services such as Firebase Analytics, Firebase Crashlytics, AdMob, Unity Ads, and AppLovin, which may collect additional data based on their own privacy policies.

How We Use Your Information
The information we collect is used for the following purposes:

- Analytics: To understand how our apps are used and improve user experience.
- Advertising: To deliver personalized or contextual advertisements through services like AdMob, Unity Ads, and AppLovin.
- Troubleshooting: To identify and fix technical issues using crash data from Firebase Crashlytics.

We do not sell your personal information to third parties.

Data Security
We implement industry-standard security measures to protect your information from unauthorized access, alteration, or disclosure. Your data is encrypted during transmission and storage.

Third-Party Services
Our applications integrate with third-party services to enhance functionality and user experience. These include:

- Firebase: For analytics and crash reporting.
- AdMob, Unity Ads, and AppLovin: For displaying advertisements.

These services collect and process data according to their own privacy policies. We recommend reviewing these policies to understand their data practices.

International Data Transfer
Your information may be transferred to and processed in countries other than your own. We ensure appropriate safeguards are in place to protect your data during these transfers.

Your Rights
While we aim to provide transparency and control over your data, it is important to note that data deletion is not supported in our current applications. If you have concerns about data collection, you can reach out to us for more information.

License Information
All our projects are governed by the GNU General Public License (GPL) version 3.0. This license grants users the freedom to use, modify, and distribute the software under the terms of the GPL. For more details, please refer to the full license text at [GNU GPL 3.0](https://www.gnu.org/licenses/gpl-3.0.html).

Contact Us
If you have any questions about this Privacy Policy or our data practices, please contact us at:

- Email: raf_android-dev@mail.ru
- Telegram: https://t.me/rafbook_reader/6

                """.trimIndent(),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Чекбокс принятия условий
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 16.dp, bottom = 24.dp)
            ) {
                Checkbox(
                    checked = isAccepted,
                    onCheckedChange = { isAccepted = it }
                )
                Text(
                    text = stringResource(R.string.privacy_policy_confirm),
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}