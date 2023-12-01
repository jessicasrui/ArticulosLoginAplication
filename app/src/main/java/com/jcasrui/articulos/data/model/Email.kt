package com.jcasrui.articulos.data.model

import com.jcasrui.articulos.data.account.AccountException
import java.util.regex.Pattern

/**
 * Esta clase comprueba que el Email cumple un patrón establecido en el método compile.
 * En caso contrario lanza una excepción
 */
data class Email(val value: String) {
    private val pattern = Pattern.compile("^\\S+@\\S+\\.\\S+$")
    //   `^`: Coincide con el principio de la cadena.
    // `\S+`: Coincide con uno o más caracteres no blancos.
    //   `@`: Coincide con el signo arroba literal (@).
    // `\S+`: Coincide con uno o más caracteres no blancos para el nombre de dominio.
    //  `\.`: Escapa al carácter punto (".") para que se trate como un carácter literal.
    // `\S+`: Coincide con uno o más caracteres no blancos para el dominio de nivel superior.
    //   `$`: Coincide con el final de la cadena.

    init {
        if(!pattern.matcher(value).matches()){
            throw AccountException.InvalidEmailFormat()
        }
    }
}