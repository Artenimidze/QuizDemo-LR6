package com.example.quizdemo_lr6

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.quizdemo_lr6.ui.theme.QuizDemoLR6Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QuizDemoLR6Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

data class PageQuiz (
    val text: String,
    val answer: Boolean
)

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val pages = listOf<PageQuiz>(
        PageQuiz(text="Canberra is the capital of Australia.", answer = true),
        PageQuiz(text="The Pacific Ocean is larger than the Atlantic Ocean.", answer = true),
        PageQuiz(text="The Suez Canal connects the Red Sea and the Indian Ocean. ", answer = false),
        PageQuiz(text="The source of the Nile River is in Egypt.", answer = false),
        PageQuiz(text="The Amazon River is the longest river in the Americas.", answer = true),
        PageQuiz(text="Lake Baikal is the world's oldest and deepest freshwater lake.", answer = true)
    )
    var page_num by rememberSaveable { mutableIntStateOf(0) }
    val page = pages[page_num]
    val answers = rememberSaveable {mutableStateListOf<Boolean>()}



    if (answers.size == pages.size) {
        var answered by remember { mutableIntStateOf(0) }
        var text by remember { mutableStateOf<String>("") }
        Card(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
        ) {
            Column {
                Text("Ваши ответы",
                    style = MaterialTheme.typography.titleLarge)

                Spacer(Modifier.height(16.dp))

                pages.forEachIndexed { index, quiz ->
                    if (quiz.answer == answers[index]) {
                        answered++
                        text = "Верно"
                    }
                    else {
                        text = "Неверно"
                    }
                    Text("Вопрос ${index+1}. ${quiz.text} Ответ: ${answers[index]}. $text")

                    Spacer(Modifier.height(10.dp))
                }

                Spacer(Modifier.height(16.dp))

                Text("Итого: $answered правильных ответов")

            }
        }
    } else {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Карточка с текущим вопросом
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Вопрос ${page_num + 1}",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(text = page.text)
                }
            }

            Spacer(Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = { answers.add(true) },
                    enabled = answers.size == page_num,
                    colors = ButtonColors(
                        contentColor = MaterialTheme.colorScheme.background,
                        disabledContainerColor = MaterialTheme.colorScheme.background,
                        disabledContentColor = MaterialTheme.colorScheme.background,
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text("True")
                }

                Button(
                    onClick = { answers.add(false) },
                    enabled = answers.size == page_num,
                    colors = ButtonColors(
                        contentColor = MaterialTheme.colorScheme.background,
                        disabledContainerColor = MaterialTheme.colorScheme.background,
                        disabledContentColor = MaterialTheme.colorScheme.background,
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text("False")
                }
            }

            Spacer(Modifier.height(100.dp))

            Text("${page_num + 1} / ${pages.size}")

            Button(
                onClick = { page_num++ },
                enabled = answers.size > page_num && answers.size != pages.size,
                colors = ButtonColors(
                    contentColor = MaterialTheme.colorScheme.background,
                    disabledContainerColor = MaterialTheme.colorScheme.background,
                    disabledContentColor = MaterialTheme.colorScheme.background,
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text("Next")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun QuizPreview () {
    MainScreen()
}