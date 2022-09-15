package com.bnyro.translate.ui.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CompareArrows
import androidx.compose.material.icons.filled.ContentPaste
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bnyro.translate.R
import com.bnyro.translate.models.MainModel
import com.bnyro.translate.util.ClipboardHelper

@Composable
fun MainContent(
    viewModel: MainModel = viewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ElevatedCard(
            modifier = Modifier
                .weight(1.0f)
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxSize()
            ) {
                StyledTextField(
                    text = viewModel.insertedText,
                    onValueChange = {
                        viewModel.insertedText = it
                        viewModel.translate()
                    },
                    maxLines = 8,
                    placeholder = stringResource(R.string.enter_text)
                )

                Divider(
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .padding(10.dp)
                        .align(alignment = Alignment.CenterHorizontally)
                        .size(70.dp, 1.dp)
                )

                val clipboardHelper = ClipboardHelper(
                    LocalContext.current.applicationContext
                )
                val copiedText = clipboardHelper.get()

                if (copiedText != null && viewModel.insertedText == "") {
                    Button(
                        onClick = {
                            viewModel.insertedText = copiedText
                            viewModel.translate()
                        },
                        modifier = Modifier
                            .padding(15.dp, 0.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.ContentPaste,
                                null,
                                modifier = Modifier
                                    .size(18.dp)
                            )
                            Text(
                                stringResource(
                                    id = R.string.paste
                                ),
                                modifier = Modifier
                                    .padding(6.dp, 0.dp, 0.dp, 0.dp)
                            )
                        }
                    }
                }

                StyledTextField(
                    text = viewModel.translatedText,
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier
                        .weight(1.0f)
                )

                Divider(
                    color = Color.Gray,
                    modifier = Modifier
                        .align(alignment = Alignment.CenterHorizontally)
                        .size(70.dp, 2.dp)
                )
            }
        }
        Row(
            modifier = Modifier
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            LanguageSelector(
                viewModel.availableLanguages,
                viewModel.sourceLanguage,
                autoLanguageEnabled = true
            ) {
                viewModel.sourceLanguage = it
            }

            IconButton(
                onClick = {
                    val temp = viewModel.sourceLanguage
                    viewModel.sourceLanguage = viewModel.targetLanguage
                    viewModel.targetLanguage = temp
                }
            ) {
                Icon(
                    Icons.Default.CompareArrows,
                    null
                )
            }

            LanguageSelector(
                viewModel.availableLanguages,
                viewModel.targetLanguage
            ) {
                viewModel.targetLanguage = it
            }
        }
    }
}
