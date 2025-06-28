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
fun UserAgreementScreen(
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
                text = stringResource(R.string.user_agreement),
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            Text(
                text = """
USER AGREEMENT  
for RafBook Mobile Application  
(under GNU GPL v3 terms)

1. General Provisions  
    1.1. This User Agreement (hereinafter referred to as the "Agreement") governs the relationship between self-employed Rafail Rustamovich Kikmatulin (hereinafter referred to as the "Developer") and the user (hereinafter referred to as the "User") of the RafBook mobile application (hereinafter referred to as the "Application"), distributed under GNU General Public License version 3 (GNU GPL v3).  
    1.2. The Application is open-source software. The full text of GNU GPL v3 is available at: [https://www.gnu.org/licenses/gpl-3.0.html](https://www.gnu.org/licenses/gpl-3.0.html).  
    1.3. By using the Application, the User confirms that they have read, understood, and fully accept the terms of this Agreement and GNU GPL v3.

2. Application Usage Terms  
    2.1. Basic functionality access:  
         2.1.1. The Application is available for use without mandatory registration.  

    2.2. User Rights:  
        2.3.1. Install the Application on unlimited compatible devices.  
        2.3.2. Use the Application according to its intended functionality.  
        2.3.3. Discontinue using the Application at any time by uninstalling it.  

3. License Terms (GNU GPL v3)  
    3.1. Source code rights:  
        3.1.1. The Application is distributed under GNU GPL v3, which guarantees the User:  
            3.1.1.1. The right to study and modify source code.  
            3.1.1.2. The right to distribute original or modified versions  

    3.2. Limitations:  
        3.2.1. All derivative works must be distributed under the same license.  
        3.2.2. No additional legal restrictions may be imposed on rights granted by GNU GPL v3.  

4. Liability and Limitations  
    4.1. Warranties:  
        4.1.1. The Application is provided "as is". The Developer doesn't guarantee uninterrupted operation or that it will meet User expectations.  

    4.2. Developer's Liability:  
        4.2.1. Shall not be responsible for:  
            4.2.1.1. Damages caused by using or inability to use the Application.  
            4.2.1.2. Actions of third parties (including advertisers and payment systems).  

5. Privacy and Data  
    5.1. Data collection:  
        5.1.1. For unauthorized users, data collection is limited to anonymous statistics (e.g., error frequency).  
        5.1.2. When registering via Google, data is processed according to Google and Google Play privacy policies.  

    5.2. Data storage:  
        5.2.1. Local device data (settings, cache) isn't transmitted to the Developer without User consent.  

6. Final Provisions  
    6.1. Agreement modifications:  
        6.1.1. The Developer may make changes by notifying Users through Application updates.  
        6.1.2. Continued use after changes implies acceptance.  

    6.2. Contacts:  
        6.2.1. Email: raf_android-dev@mail.ru
        6.2.2. Jurisdiction: Russian Federation, Moscow  

    6.3. Consent:  
        6.3.1. By installing the Application, the User confirms acceptance of:  
            6.3.1.1. This Agreement terms.  
            6.3.1.2. Privacy Policy (where applicable).  
            6.3.1.3. GNU GPL v3 terms.  

7. Miscellaneous  
    7.1. Using the Application doesn't require providing personal information, though the Developer may collect: name, email, vehicle registration number when creating an account, and location data via GPS/Wi-Fi for Application functionality (legitimate interest) according to the Application's Privacy Policy.  
    7.2. The User guarantees full understanding and unconditional acceptance of all Agreement terms.  
    7.3. The User guarantees not to use the Application for purposes other than specified in this Agreement.  
    7.4. All actions performed using the Application are deemed to occur in Moscow time (Russian Federation).  

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
                    text = stringResource(R.string.user_agreement_confirm),
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}