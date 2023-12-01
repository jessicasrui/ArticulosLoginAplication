package com.jcasrui.articulos.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jcasrui.articulos.data.model.User
import com.jcasrui.articulos.databinding.LayoutUserItemBinding


class UserAdapter(
    //private val dataset: MutableList<User>,
    //private val context: Context,
    private val listener: OnUserClick,
    private val onItemClick: (user: User) -> Unit,
) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    // Se crea la coleccion de datos del adapter
    private var dataset = arrayListOf<User>()

    /**
     * Esta interfaz es el contrato entre el Adapter y el Fragment que lo contiene.
     */
    interface OnUserClick {
        fun userClick(user: User)   // Pulsación corta
        fun userOnLongClick(user: User) // Pulsación larga
        //fun deleteClick(user: User)  // Eliminar usuario
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        //return UserViewHolder(layoutInflater.inflate(R.layout.layout_user_item, parent, false)) // Version 1.0 - false: no se use directamente a la vista??
        return UserViewHolder(LayoutUserItemBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val item = dataset.get(position)
        //holder.bind(item, context)
        holder.bind(item)
    }

    /**
     * Función que devuelve el número de elemetos y por tanto se llamará al método onCreateViewHolder
     * tantas veces como item se visualicen en el RecyclerView
     */
    override fun getItemCount(): Int {
        return dataset.size     // IMPORTANTE: este return es muy importante poner el tamaño, porque si no, no se nos mostrara nada en la lista
    }

    /**
     * Funcino que actializa los datos del adapter y le dice a la vista que se invalide y vuelva a redibujarlos
     */
    fun update(newDataSet: ArrayList<User>){
        // Actualizar mi dataset y notificar a la vista el cambio
        notifyDataSetChanged()
    }

    /**
     * La clase ViewHolder contiene todos los elementos de View o del layout XML que se ha inflado
     */
    /* Version 1.0
    class UserViewHolder(view: View): RecyclerView.ViewHolder(view){

        val tvName = view.findViewById(R.id.tvName) as TextView
        val tvEmail = view.findViewById(R.id.tvEmail) as TextView
        //val imgUser = view.findViewById(R.id.imgUser) as ImageView

        fun bind(item: User, context: Context){
            tvName.text = item.name
            tvEmail.text = item.email
        }
    }*/
    // Hemos refactorizado, hemos quitado el findByIde para utilzar binding
    // mi clase LayoutUserItemBinding la declaramos como propiedad y la pasamos como parametro
    // el parametro de esta clase, no es un parametro como en otros lenguajes, en kotlin este parametro son propiedades de la clase
    // Definimos la clase internal con inner
    inner class UserViewHolder(private val binding: LayoutUserItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        // Esto fue la opcion 1 sin utilizar la clase Binding
        //val tvName = view.findViewById(R.id.tvName) as TextView
        //val tvEmail = view.findViewById(R.id.tvEmail) as TextView
        //val imgUser = view.findViewById(R.id.imgUser) as ImageView

        fun bind(item: User) {
            // version 2.0
            //binding.tvName.text = item.name
            //binding.tvEmail.text = item.email
            //binding.imgUser.text = item.name.substring(0)   // aqui en el text coge la primera letra de cada nombre, que la img muestra la letra

            with(binding) {
                tvName.text = item.name
                tvEmail.text = item.email
                //imgUser.text = item.name.substring(0)
                // Manejamos el evento EventClick
                root.setOnClickListener { _: View ->
                    // Llamaré a un método de la interfaz declarada dentro del adapter
                    // Como no utilizo el parametro de entrada de tipo View, Kotlin me recomienda usar el _
                    //listener.userClick(item)
                    onItemClick(item)
                }
                // Manejar la pulsación larga EventLongClick
                root.setOnLongClickListener { _: View ->
                    listener.userOnLongClick(item)
                    // Se debe indicar el framework/android que se consume el evento
                    true
                    //return@setOnLongClickListener true
                }
            }
        }
    }
}