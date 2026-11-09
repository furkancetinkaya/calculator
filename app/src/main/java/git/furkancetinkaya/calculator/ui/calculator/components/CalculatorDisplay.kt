package git.furkancetinkaya.calculator.ui.calculator.components

import android.content.ClipData
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.toClipEntry
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import git.furkancetinkaya.calculator.R
import git.furkancetinkaya.calculator.domain.input.AngleUnit
import kotlinx.coroutines.launch

@Composable
internal fun CalculatorDisplay(
    displayText: String,
    isAngleUnitButtonVisible: Boolean,
    angleUnit: AngleUnit,
    onAngleUnitToggled: () -> Unit,
) {
    val context = LocalContext.current
    val clipboard = LocalClipboard.current
    val scope = rememberCoroutineScope()
    val copyActionLabel = stringResource(R.string.display_copy_action)
    val clipboardLabel = stringResource(R.string.display_clip_label)
    val copiedMessage = stringResource(R.string.display_copied)

    val copyDisplay: () -> Unit = {
        scope.launch {
            clipboard.setClipEntry(
                ClipData.newPlainText(clipboardLabel, displayText).toClipEntry()
            )
            Toast.makeText(context, copiedMessage, Toast.LENGTH_SHORT).show()
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(60.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // Display the deg/rad button only when inside a trigonometric function.
        if (isAngleUnitButtonVisible) {
            Button(
                onClick = onAngleUnitToggled,
                modifier = Modifier
                    .height(60.dp)
                    .weight(0.2f),
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                ),
            ) {
                val unit = when (angleUnit) {
                    AngleUnit.Degrees -> "deg"
                    AngleUnit.Radians -> "rad"
                }
                Text(text = unit)
            }
        }

        BoxWithConstraints(
            modifier = Modifier
                .weight(0.8f)
                .height(60.dp)
                .clickable(onClickLabel = copyActionLabel, onClick = copyDisplay),
        ) {
            val scrollState = rememberScrollState()

            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .horizontalScroll(scrollState)
                    .widthIn(min = maxWidth),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = displayText,
                    maxLines = 1,
                    softWrap = false,
                    fontSize = 50.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }

            // Keep the newest characters visible as the expression grows.
            LaunchedEffect(displayText, scrollState.maxValue) {
                scrollState.scrollTo(scrollState.maxValue)
            }
        }
    }
}
