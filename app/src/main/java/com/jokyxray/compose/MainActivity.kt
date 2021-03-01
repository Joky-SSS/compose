package com.jokyxray.compose

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jokyxray.compose.ui.theme.ComposeTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    MyScreenContent()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyScreenContent(names: List<String> = List(1000) { "Hello Android #$it" }) {
    ComposeTheme {
        Surface {
            Column {
                NewStory()
                NameList(names = names)
            }
        }
    }
}

@Composable
fun NameList(names: List<String>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(items = names) { name ->
            Greeting(name = name)
            Divider(color = Color.Black)
        }
    }
}

@Composable
fun Counter(count: Int, updateCount: (Int) -> Unit) {
    Button(
        onClick = { updateCount(count + 1) },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (count > 5) Color.Green else Color.White
        )
    ) {
        Text(text = "I've been clicked $count times.")
    }
}

@Composable
fun NewStory() {
    val image = ImageBitmap.imageResource(R.drawable.header)
    MaterialTheme() {
        val typography = MaterialTheme.typography
        val count = remember { mutableStateOf(0) }
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            val imageModifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .clip(RoundedCornerShape(4.dp))

            Image(image, "", modifier = imageModifier, contentScale = ContentScale.Crop)
            Spacer(Modifier.height(16.dp))
            Text(
                "A day wandering through the sandhills " +
                        "in Shark Fin Cove, and a few of the " +
                        "sights I saw",
                style = typography.h6,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text("Davenport, California", style = typography.body2)
            Text("December 2018", style = typography.body2)
            Counter(count.value) { newCount ->
                count.value = newCount
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    val isSelected = remember { mutableStateOf(false) }
    val backgroundColor =
        animateColorAsState(targetValue = if (isSelected.value) Color.Red else Color.Transparent)
    Text(
        text = "Hello $name!",
        modifier = Modifier
            .padding(24.dp)
            .background(color = backgroundColor.value)
            .clickable { isSelected.value = !isSelected.value }
    )
}

