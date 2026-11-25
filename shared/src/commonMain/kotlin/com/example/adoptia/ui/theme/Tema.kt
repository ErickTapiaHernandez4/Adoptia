package com.example.adoptia.ui.theme

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Surface
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ---------- Colores de Adoptia: naranja cálido + verde azulado ----------
val Naranja = Color(0xFFFF4747)
val NaranjaClaro = Color(0xFF00BCB4)
val VerdeAzulado = Color(0xFF2A8C82)
val VerdeAzuladoClaro = Color(0xFFFFB547)
val Crema = Color(0xFFC4E86B)
val CafeOscuro = Color(0xFF231A15)

// Colores para el estado de las solicitudes
val ColorPendiente = Color(0xFFB7791F)
val ColorAprobada = Color(0xFF2E7D32)
val ColorRechazada = Color(0xFFC62828)

private val Colores = lightColorScheme(
    primary = Naranja,
    onPrimary = Color.White,
    primaryContainer = NaranjaClaro,
    onPrimaryContainer = Color(0xFF4A1F00),
    secondary = VerdeAzulado,
    onSecondary = Color.White,
    secondaryContainer = VerdeAzuladoClaro,
    onSecondaryContainer = Color(0xFF00201D),
    background = Crema,
    onBackground = CafeOscuro,
    surface = Color.White,
    onSurface = CafeOscuro,
    surfaceVariant = Color(0xFFF4E6DD),
    onSurfaceVariant = Color(0xFF52443C),
    outline = Color(0xFFD8C2B5),
    error = Color(0xFFBA1A1A),
)

// ---------- Esquinas redondeadas ----------
private val Formas = Shapes(
    extraSmall = RoundedCornerShape(8.dp),
    small = RoundedCornerShape(12.dp),    // campos de texto y botones
    medium = RoundedCornerShape(16.dp),   // tarjetas
    large = RoundedCornerShape(24.dp),
    extraLarge = RoundedCornerShape(32.dp)
)

// ---------- Letras: títulos en negritas ----------
private val base = Typography()
private val Letras = Typography(
    headlineLarge = base.headlineLarge.copy(fontWeight = FontWeight.Bold),
    headlineMedium = base.headlineMedium.copy(fontWeight = FontWeight.Bold),
    titleLarge = base.titleLarge.copy(fontWeight = FontWeight.Bold),
    titleMedium = base.titleMedium.copy(fontWeight = FontWeight.SemiBold),
    labelLarge = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.SemiBold, letterSpacing = 0.3.sp)
)

// Envuelve toda la app: aplica colores, formas y letras, y pinta el fondo crema.
@Composable
fun AdoptiaTema(contenido: @Composable () -> Unit) {
    MaterialTheme(colorScheme = Colores, shapes = Formas, typography = Letras) {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            contenido()
        }
    }
}