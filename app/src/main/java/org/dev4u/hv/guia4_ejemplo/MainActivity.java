package org.dev4u.hv.guia4_ejemplo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileDescriptor;
import java.io.IOException;

@SuppressWarnings("ALL")
public class MainActivity extends AppCompatActivity {

    private int PICK_PHOTO=3;

    ImageView imageView1, imageView2;
    private int seleccion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView1   = (ImageView) findViewById(R.id.imgFondo1);
        imageView2   = (ImageView) findViewById(R.id.imgFondo2);

        //agregando imagen desde codigo y desde la carpeta drawable
        //imageView.setImageDrawable(getResources().getDrawable(R.drawable.android_pic));
        //para eliminar la imagen
        //imageView.setImageDrawable(null);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        //INFO: menu_activity es el archivo que he creado dentro la carpeta menu
        inflater.inflate(R.menu.menu_activity, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //cuando seleccione una opcion pasa por un switch
        //para saber cual es
        switch (item.getItemId()) {
            case R.id.agregar1:
                seleccion = 1;
                agregarIMG();//llamo a mi funcion
                return true;
            case R.id.agregar2:
                seleccion = 2;
                agregarIMG();//llamo a mi funcion
                return true;
            case R.id.eliminar://llamo a mi funcion
                eliminarIMG();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    public void agregarIMG() {
        Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_PHOTO);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_PHOTO && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            Bitmap bmp = null;
            try {
                bmp = getBitmapFromUri(selectedImage);
                if (seleccion == 1){
                    imageView1.setImageBitmap(bmp);
                }else{
                    imageView2.setImageBitmap(bmp);
                }
                seleccion = 0;
            } catch (IOException e) {
                Toast.makeText(this,"Error Cargando imagen",Toast.LENGTH_SHORT);
                e.printStackTrace();
            }
        }
    }

    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }

    private void eliminarIMG(){
        imageView1.setImageDrawable(null);
        imageView2.setImageDrawable(null);
    }
}
