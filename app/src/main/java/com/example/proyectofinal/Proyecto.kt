package com.example.proyectofinal

import com.google.firebase.firestore.PropertyName

data class Proyecto(
    val id: String? = null,
    @get:PropertyName("c_total_inv_viable") @set:PropertyName("c_total_inv_viable") var cTotalInvViable: Double = 0.0,
    @get:PropertyName("c_unico") @set:PropertyName("c_unico") var cUnico: String = "",
    @get:PropertyName("cadena_funcional") @set:PropertyName("cadena_funcional") var cadenaFuncional: String = "",
    @get:PropertyName("nombre_inversion") @set:PropertyName("nombre_inversion") var nombreInversion: String = "",
    @get:PropertyName("situacion") @set:PropertyName("situacion") var situacion: String = "",
)
