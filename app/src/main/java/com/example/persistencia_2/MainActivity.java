package com.example.persistencia_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView listaDisciplina;
    ArrayAdapter adapter;
    // Determina o numero total de itens (disciplinas)
    public static final int ACTIVITY_REQUEST_DISCIPLINA = 1;
    public static final int TOTAL_DISCIPLINA = 50;
    public String[] disciplinas = new String[TOTAL_DISCIPLINA];
    public Disciplina[] vetDisciplina = new Disciplina[TOTAL_DISCIPLINA];
    Intent intent;
    public int indiceItem = 0;
    private final String NOMEAQUIVO = "dadosDisciplinas.dat";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listaDisciplina = (ListView) findViewById(R.id.lista);
        iniciarVetor();
        try {
            if (carregarDisciplinasArquivo(NOMEAQUIVO)){
                Toast.makeText(this, "Dados carregados na lista!", Toast.LENGTH_SHORT).show();
            }
        } catch (FileNotFoundException fnfe){
            Toast.makeText(this, "Arquivo INEXISTENTE!", Toast.LENGTH_SHORT).show();
        } catch (IOException e){
            Toast.makeText(this, "Erro de IO!", Toast.LENGTH_SHORT).show();
        }
        atulizarLista();

        setTitle("Disciplina");

        listaDisciplina.setOnClickListener((View.OnClickListener) this);

    }

    private void atulizarLista() {
        for (int i = 0; i < TOTAL_DISCIPLINA; i++){
            disciplinas[i] = vetDisciplina[i].textoLista();
        }
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, disciplinas);
        listaDisciplina.setAdapter(adapter);
    }

    private boolean carregarDisciplinasArquivo(String arq) throws
            FileNotFoundException, IOException{
        File file = new File(getFilesDir(), arq);
        DataInputStream dis = new DataInputStream(new FileInputStream(file));
        for (int i = 0; i < TOTAL_DISCIPLINA; i++){
            vetDisciplina[i].setNome(dis.readUTF());
            vetDisciplina[i].setA1(dis.readDouble());
            vetDisciplina[i].setA2(dis.readDouble());
            vetDisciplina[i].setA3(dis.readDouble());
        }
        dis.close();
        return true;
    }
    public void onItemClick(AdapterView<?> parent, View view,int position, long id){
        indiceItem = position;
        intent = new Intent(getApplicationContext(), TratarDisciplina.class);
            intent.putExtra("nome", vetDisciplina[indiceItem].getNome());
        intent.putExtra("a1", vetDisciplina[indiceItem].getA1());
        intent.putExtra("a2", vetDisciplina[indiceItem].getA2());
        intent.putExtra("a3", vetDisciplina[indiceItem].getA3());

        startActivityForResult(intent, ACTIVITY_REQUEST_DISCIPLINA);

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ACTIVITY_REQUEST_DISCIPLINA) {
            if (resultCode == RESULT_OK) {
                int i = data.getIntExtra("indice", 0);
                vetDisciplina[i].setNome(data.getStringExtra("nome"));
                vetDisciplina[i].setA1(data.getDoubleExtra("a1", 0.0));
                vetDisciplina[i].setA2(data.getDoubleExtra("a2", 0.0));
                vetDisciplina[i].setA3(data.getDoubleExtra("a3", 0.0));

                atulizarLista();

            }
        }
    }

    public void gravarArquivo(View view){
        try {
            if (armazenarDisciplinasArquivo(NOMEAQUIVO)){
                Toast.makeText(this, "Dados carregados na lista!", Toast.LENGTH_SHORT).show();
            }
        } catch (FileNotFoundException fnfe){
            Toast.makeText(this, "Arquivo INEXISTENTE!", Toast.LENGTH_SHORT).show();
        } catch (IOException e){
            Toast.makeText(this, "Erro de IO!", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean armazenarDisciplinasArquivo(String arq) throws
            FileNotFoundException, IOException{
        File file = new File(getFilesDir(), arq);
        DataOutputStream dos = new DataOutputStream(new FileOutputStream(file));
        for (int i = 0; i < TOTAL_DISCIPLINA; i++){
            dos.writeUTF(vetDisciplina[i].getNome());
            dos.writeDouble(vetDisciplina[i].getA1());
            dos.writeDouble(vetDisciplina[i].getA2());
            dos.writeDouble(vetDisciplina[i].getA3());
        }
        dos.close();
        return true;
    }

    private void iniciarVetor() {
        for (int i = 0; i < TOTAL_DISCIPLINA; i++){
            vetDisciplina[i] = new Disciplina();
        }
    }
    public void sair(View view){
        finish();
    }


}