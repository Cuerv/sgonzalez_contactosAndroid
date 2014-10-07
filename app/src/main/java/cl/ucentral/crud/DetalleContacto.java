package cl.ucentral.crud;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class DetalleContacto extends ActionBarActivity implements android.view.View.OnClickListener{

    Button btnSave ,  btnDelete;
    Button btnClose;
    EditText editTextNombre;
    EditText editTextApellido;
    EditText editTextFono;
    private int _Contacto_Id=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_contacto);

        btnSave = (Button) findViewById(R.id.btnSave);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnClose = (Button) findViewById(R.id.btnClose);

        editTextNombre = (EditText) findViewById(R.id.editTextNombre);
        editTextApellido = (EditText) findViewById(R.id.editTextApellido);
        editTextFono = (EditText) findViewById(R.id.editTextFono);

        btnSave.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnClose.setOnClickListener(this);


        _Contacto_Id =0;
        Intent intent = getIntent();
        _Contacto_Id =intent.getIntExtra("contacto_Id", 0);
        ContactoRepo repo = new ContactoRepo(this);
        Contacto contacto = new Contacto();
        contacto = repo.getContactoPorID(_Contacto_Id);

        editTextFono.setText(String.valueOf(contacto.fono));
        editTextNombre.setText(contacto.nombre);
        editTextApellido.setText(contacto.apellido);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detalle_contacto, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClick(View view) {
        if (view == findViewById(R.id.btnSave)){
            ContactoRepo repo = new ContactoRepo(this);
            Contacto contacto = new Contacto();
            contacto.fono= Integer.parseInt(editTextFono.getText().toString());
            contacto.apellido=editTextApellido.getText().toString();
            contacto.nombre=editTextNombre.getText().toString();
            contacto.contacto_ID=_Contacto_Id;

            if (_Contacto_Id==0 ){
                _Contacto_Id = repo.insert(contacto);

                Toast.makeText(this,"Nuevo Contacto Ingresado",Toast.LENGTH_SHORT).show();
            }else{

                repo.update(contacto);
                Toast.makeText(this,"Contacto Actualizado",Toast.LENGTH_SHORT).show();
            }
        }else if (view== findViewById(R.id.btnDelete)){
            ContactoRepo repo = new ContactoRepo(this);
            repo.delete(_Contacto_Id);
            Toast.makeText(this, "Contacto Borrado", Toast.LENGTH_SHORT).show();
            finish();
        }else if (view== findViewById(R.id.btnClose)){
            finish();
        }


    }

}