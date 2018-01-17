package com.example.android.nlist;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView lvList;
    ArrayList<String> arrayList;
    ArrayAdapter<String> adapter;
    int index;
    AlertDialog.Builder builder;
    Activity context;
    FloatingActionButton fabAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvList = findViewById(R.id.lvList);
        fabAdd=findViewById(R.id.fabAdd);

        arrayList = new ArrayList<>();
        arrayList.add("Noida");
        arrayList.add("Delhi");
        arrayList.add("Gurugram");
        arrayList.add("Kanpur");
        arrayList.add("Panipat");
        arrayList.add("Patna");
        arrayList.add("Malda Town");
        arrayList.add("Kolkata");
        arrayList.add("Assam");
        arrayList.add("Chennai");
        arrayList.add("DumDum");
        arrayList.add("Salt Lake");
        arrayList.add("New Town");
        arrayList.add("Lake Town");
        arrayList.add("Siliguri");

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        lvList.setAdapter(adapter);

        lvList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                index = position;
                return false;
            }
        });

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem();
            }
        });

        registerForContextMenu(lvList);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(0, v.getId(), 0, "Rename");
        menu.add(0, v.getId(), 1, "Delete");
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle() == "Rename") {
            renameItem();
        } else {
            deleteItem();
            /*AdapterView.AdapterContextMenuInfo data= (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            arrayList.remove(data.position);*/
        }
        return super.onContextItemSelected(item);
    }

    private void renameItem() {
        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.rename_dialog, null);
        final EditText editText = view.findViewById(R.id.itemName);
        editText.setText(arrayList.get(index));
        builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Rename Item").setView(view);
        builder.setIcon(R.drawable.list);
        builder.setPositiveButton("Rename", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                arrayList.set(index, editText.getText().toString());
                adapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "Name Changed", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setCancelable(false);
        builder.create().show();
    }
    public void deleteItem(){
        final String itemName = arrayList.get(index);
        builder=new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Delete "+itemName+"!");
        builder.setMessage("Are You Sure?");
        builder.setIcon(R.drawable.delete_icon);
        builder.setCancelable(false);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                arrayList.remove(index);
                adapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, itemName + " is Deleted", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.addItem){
            addItem();
        }else {
            deleteAll();
        }
        return super.onOptionsItemSelected(item);
    }

    private void addItem() {
        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.rename_dialog, null);
        final EditText editText = view.findViewById(R.id.itemName);
        editText.setHint("Enter a New Item");
        builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Add Item").setView(view);
        builder.setIcon(R.drawable.list);
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                arrayList.add(editText.getText().toString());
                adapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "New Item Added", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setCancelable(false);
        builder.create().show();
    }

    private void deleteAll() {
        builder=new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Delete All!");
        builder.setMessage("Are You Sure?");
        builder.setIcon(R.drawable.delete_icon);
        builder.setCancelable(false);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                arrayList.clear();
                adapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "All Items are Deleted", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();


    }
}
