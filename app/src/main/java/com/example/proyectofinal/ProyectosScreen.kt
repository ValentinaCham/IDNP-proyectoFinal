package com.example.proyectofinal

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObjects

class ProyectosViewModel : ViewModel() {
    private val _proyectos = MutableLiveData<List<Proyecto>>()
    val proyectos: LiveData<List<Proyecto>> = _proyectos

    init {
        fetchProyectos()
    }

    private fun fetchProyectos() {
        Firebase.firestore.collection("testing")
            .addSnapshotListener { snapshots, e ->
                if (e != null) {
                    // Handle error
                    return@addSnapshotListener
                }
                _proyectos.value = snapshots?.toObjects()
            }
    }

    fun addProyecto(proyecto: Proyecto) {
        Firebase.firestore.collection("testing")
            .add(proyecto)
    }
}

@Composable
fun ProyectosScreen(viewModel: ProyectosViewModel = viewModel()) {
    val proyectos by viewModel.proyectos.observeAsState(initial = emptyList())
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Add Proyecto")
            }
        }
    ) {
        LazyColumn(modifier = Modifier.padding(it)) {
            items(proyectos) { proyecto ->
                ProyectoItem(proyecto = proyecto)
            }
        }
    }

    if (showDialog) {
        AddProyectoDialog(
            onDismiss = { showDialog = false },
            onConfirm = {
                viewModel.addProyecto(it)
                showDialog = false
            }
        )
    }
}

@Composable
fun ProyectoItem(proyecto: Proyecto) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Nombre: ${proyecto.nombreInversion}")
            Text(text = "Situación: ${proyecto.situacion}")
            Text(text = "Cadena Funcional: ${proyecto.cadenaFuncional}")
            Text(text = "Código Único: ${proyecto.cUnico}")
            Text(text = "Inversión Viable: ${proyecto.cTotalInvViable}")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProyectoDialog(onDismiss: () -> Unit, onConfirm: (Proyecto) -> Unit) {
    var nombreInversion by remember { mutableStateOf("") }
    var situacion by remember { mutableStateOf("") }
    var cadenaFuncional by remember { mutableStateOf("") }
    var cUnico by remember { mutableStateOf("") }
    var cTotalInvViable by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Añadir Proyecto") },
        text = {
            Column {
                OutlinedTextField(value = nombreInversion, onValueChange = { nombreInversion = it }, label = { Text("Nombre Inversión") })
                OutlinedTextField(value = situacion, onValueChange = { situacion = it }, label = { Text("Situación") })
                OutlinedTextField(value = cUnico, onValueChange = { cUnico = it }, label = { Text("Código Único") })
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val proyecto = Proyecto(
                        nombreInversion = nombreInversion,
                        situacion = situacion,
                        cadenaFuncional = cadenaFuncional,
                        cUnico = cUnico,
                        cTotalInvViable = cTotalInvViable.toDoubleOrNull() ?: 0.0
                    )
                    onConfirm(proyecto)
                }
            ) {
                Text("Añadir")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}
