package com.practicum.image_list

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.practicum.image_list.ui.theme.ImagelistTheme

data class Picture(
    val id: Int,
    val author: String,
    val url: String
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ImagelistTheme {
                GalleryScreen()
            }
        }
    }
}

@Composable
fun GalleryScreen() {
    var gallery by remember { mutableStateOf(generateSamplePictures()) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                val newPicture = Picture(
                    id = gallery.size + 1,
                    author = "Автор ${gallery.size + 1}",
                    url = "https://avatars.mds.yandex.net/i?id=f8660c2a77a2c2760f4b39c51033128046d4e5f4-9583697-images-thumbs&n=13"
                )
                gallery = gallery + newPicture
            }) {
                Icon(Icons.Default.Add, contentDescription = "Добавить")
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(gallery, key = { it.id }) { picture ->
                PictureItem(
                    picture = picture,
                    onClick = {
                        gallery = gallery.filterNot { it.id == picture.id }
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PictureItem(picture: Picture, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            GlideImage(
                model = picture.url,
                contentDescription = picture.author,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                contentScale = ContentScale.Crop
            )
            Text(
                text = picture.author,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

fun generateSamplePictures(): List<Picture> {
    return listOf(
        Picture(1, "кот", "https://avatars.mds.yandex.net/i?id=216812005aaead043d3723a92cf1c6516f21dfe0-5905309-images-thumbs&n=13"),
        Picture(2, "собака", "https://avatars.mds.yandex.net/i?id=a6a982311bd75b46518faae1ae66b54ae688cc3f-5248826-images-thumbs&n=13"),
        Picture(3, "Город", "https://avatars.mds.yandex.net/i?id=dee5afdb043c5e7316d25d1deec596ef56ea8f8b-6520328-images-thumbs&n=13"),
        Picture(4, "Горы", "https://avatars.mds.yandex.net/i?id=21ea3e282bff6def265a2af12cf783090056e01d-4615427-images-thumbs&n=13")
    )
}
