
import androidx.compose.foundation.Image

import androidx.compose.foundation.layout.padding

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Scale
import coil.transform.CircleCropTransformation

import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth

@Composable
fun AsyncImageProfile(
  photoUrl: String?,
  modifier: Modifier = Modifier,
  contentScale: ContentScale = ContentScale.Crop, // Default content scale
  loadingModifier: Modifier = Modifier,
  loadingSize: Int = 0, // Default loading indicator size
  loadingColor: Color = MaterialTheme.colorScheme.primary // Default loading indicator color


) {
  val painter = rememberAsyncImagePainter(
    ImageRequest.Builder(LocalContext.current).data(data = photoUrl).apply {
      crossfade(true)
      transformations(CircleCropTransformation())
      scale(Scale.FILL)
    }.build()
  )

  Image(
    painter = painter,
    contentDescription = "",
    modifier = modifier,
    contentScale = contentScale
  )

  // Show loading indicator while the image is loading
  if (painter.state is AsyncImagePainter.State.Loading) {
    CircularProgressIndicator(
      modifier = Modifier,

      strokeWidth = 2.dp,


      color = loadingColor,







    )
  }
}