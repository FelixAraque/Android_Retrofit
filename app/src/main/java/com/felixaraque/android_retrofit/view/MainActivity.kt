package com.felixaraque.android_retrofit.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import com.felixaraque.android_retrofit.R
import com.felixaraque.android_retrofit.databinding.ActivityMainBinding
import com.felixaraque.android_retrofit.model.Recurso
import com.felixaraque.android_retrofit.model.Usuario
import com.felixaraque.android_retrofit.model.Zona
import com.felixaraque.android_retrofit.viewmodel.MainViewModel
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private var usuario: Usuario?=null
    private lateinit var shareP: SharedPreferences
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        shareP = getSharedPreferences("datos", Context.MODE_PRIVATE)
        getUsuarioSH()
        if (usuario == null) {
            registrerInicio()
        }
    }

    private fun getUsuarioSH() {
        val usuarioTXT = shareP.getString("usuario","n")
        if (!usuarioTXT.equals("n")){
            usuario = Gson().fromJson(usuarioTXT, Usuario::class.java)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_register -> {
                register()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun removeUsuarioSH() {
        val editor = shareP.edit()
        editor.remove("usuario")
        editor.commit()
        usuario = null
    }

    private fun registrerInicio() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Login/Register")
        val ll = LinearLayout(this)
        ll.setPadding(30,30,30,30)
        ll.orientation = LinearLayout.VERTICAL

        val lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
        lp.setMargins(0,50,0,50)

        val textInputLayoutNick = TextInputLayout(this)
        textInputLayoutNick.layoutParams = lp
        val etnick = EditText(this)
        etnick.setPadding(0, 80, 0, 80)
        etnick.textSize = 20.0F
        etnick.hint = "Nick"
        textInputLayoutNick.addView(etnick)

        val textInputLayoutPass = TextInputLayout(this)
        textInputLayoutPass.layoutParams = lp
        val etpass = EditText(this)
        etpass.setPadding(0, 80, 0, 80)
        etpass.textSize = 20.0F
        etpass.hint = "Pass"
        textInputLayoutPass.addView(etpass)

        ll.addView(textInputLayoutNick)
        ll.addView(textInputLayoutPass)

        builder.setView(ll)
        builder.setCancelable(false)

        builder.setPositiveButton("Aceptar") { dialog, which ->
            val usuario = Usuario(0,etnick.text.toString(), etpass.text.toString())
            loginregister(usuario)
        }

        builder.show()
    }

    private fun register() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Login/Register")
        val ll = LinearLayout(this)
        ll.setPadding(30,30,30,30)
        ll.orientation = LinearLayout.VERTICAL

        val lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
        lp.setMargins(0,50,0,50)

        val textInputLayoutNick = TextInputLayout(this)
        textInputLayoutNick.layoutParams = lp
        val etnick = EditText(this)
        etnick.setPadding(0, 80, 0, 80)
        etnick.textSize = 20.0F
        etnick.hint = "Nick"
        textInputLayoutNick.addView(etnick)

        val textInputLayoutPass = TextInputLayout(this)
        textInputLayoutPass.layoutParams = lp
        val etpass = EditText(this)
        etpass.setPadding(0, 80, 0, 80)
        etpass.textSize = 20.0F
        etpass.hint = "Pass"
        textInputLayoutPass.addView(etpass)

        ll.addView(textInputLayoutNick)
        ll.addView(textInputLayoutPass)

        builder.setView(ll)

        builder.setPositiveButton("Aceptar") { dialog, which ->
            val usuario = Usuario(0,etnick.text.toString(), etpass.text.toString())
            loginregister(usuario)
        }

        builder.setNegativeButton("Cancelar") { dialog, which ->
        }

        builder.show()
    }

    private fun loginregister(usuario: Usuario) {
        viewModel.getUserByNickAndPass(usuario).observe(this, Observer { it ->
            if (it==null) {
                viewModel.saveUsuario(usuario).observe(this, Observer { it ->
                    it?.let {
                        this.usuario = it
                        saveUsuarioSH(it)
                        msg("Usuario Creado")
                    }
                })
            }
            else {
                this.usuario = it
                saveUsuarioSH(it)
                msg("Login Ok...")
            }
        })
    }

    private fun saveUsuarioSH(usuario: Usuario) {
        removeUsuarioSH()
        val editor = shareP.edit()
        editor.putString("usuario", Gson().toJson(usuario))
        editor.commit()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    fun onClickZona(v: View) {
        val zona = v.tag as Zona
        val args = Bundle()
        args.putSerializable("usuario", usuario)
        args.putSerializable("zona", zona)
        navController.navigate(R.id.action_ZonasFragment_to_ZonaDetailFragment, args)
    }

    fun onClickRecurso(v: View) {
        val recurso = v.tag as Recurso
        val args = Bundle()
        args.putSerializable("recurso", recurso)
        args.putSerializable("usuario", usuario)
        navController.navigate(R.id.action_RecursosFragment_to_RecursoDetailFragment, args)
    }

    private fun msg(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}