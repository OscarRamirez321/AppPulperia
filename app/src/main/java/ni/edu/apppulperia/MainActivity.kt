package ni.edu.apppulperia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ni.edu.apppulperia.ui.theme.AppPulperiaTheme // Cambia esto según el nombre de tu app

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppPulperiaTheme {
                // Contenedor principal
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PantallaPedidoPulperia()
                }
            }
        }
    }
}

// Estructura de datos para guardar el pedido
data class Pedido(val cliente: String, val producto: String, val cantidad: String)

@Composable
fun PantallaPedidoPulperia() {
    // 1. Variables de Estado (Para leer lo que el usuario escribe)
    var nombreCliente by remember { mutableStateOf("") }
    var nombreProducto by remember { mutableStateOf("") }
    var cantidadProducto by remember { mutableStateOf("") }

    // Variables para mostrar el resultado
    var pedidoRegistrado by remember { mutableStateOf<Pedido?>(null) }
    var mostrarConfirmacion by remember { mutableStateOf(false) }

    // 2. Diseño de la pantalla (Columna vertical)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp), // Margen general para que respire el diseño
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Título Principal
        Text(
            text = "Pedido de Pulpería",
            fontSize = 28.sp,
            fontWeight = FontWeight.ExtraBold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 32.dp, top = 16.dp)
        )

        // Campo: Nombre del Cliente
        OutlinedTextField(
            value = nombreCliente,
            onValueChange = { nombreCliente = it },
            label = { Text("Nombre del cliente") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo: Producto
        OutlinedTextField(
            value = nombreProducto,
            onValueChange = { nombreProducto = it },
            label = { Text("Producto solicitado") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo: Cantidad (Configurado para teclado numérico)
        OutlinedTextField(
            value = cantidadProducto,
            onValueChange = { cantidadProducto = it },
            label = { Text("Cantidad") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Botón Registrar
        Button(
            onClick = {
                // Validamos que los campos no estén vacíos
                if (nombreCliente.isNotBlank() && nombreProducto.isNotBlank() && cantidadProducto.isNotBlank()) {
                    // Guardamos el pedido en la variable
                    pedidoRegistrado = Pedido(nombreCliente, nombreProducto, cantidadProducto)
                    mostrarConfirmacion = true

                    // Opcional: Limpiamos los campos para el siguiente pedido
                    nombreCliente = ""
                    nombreProducto = ""
                    cantidadProducto = ""
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp) // Un botón un poco más alto para fácil interacción
        ) {
            Text("Registrar Pedido", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(32.dp))

        // 3. Tarjeta (Card) de Confirmación Dinámica
        if (mostrarConfirmacion && pedidoRegistrado != null) {
            Text(
                text = "¡Pedido registrado con éxito!",
                color = MaterialTheme.colorScheme.tertiary,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Resumen del Pedido",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Divider(modifier = Modifier.padding(vertical = 8.dp)) // Línea separadora

                    Text(text = "👤 Cliente: ${pedidoRegistrado!!.cliente}", fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "🛒 Producto: ${pedidoRegistrado!!.producto}", fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "📦 Cantidad: ${pedidoRegistrado!!.cantidad}", fontSize = 16.sp)
                }
            }
        }
    }
}