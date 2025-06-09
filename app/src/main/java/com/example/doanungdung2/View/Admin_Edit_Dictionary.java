package com.example.doanungdung2.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doanungdung2.Controller.DictionaryHandler;
import com.example.doanungdung2.Model.Dictionary;
import com.example.doanungdung2.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Admin_Edit_Dictionary extends AppCompatActivity {

    private static final String DB_NAME = "AppHocTiengAnh";
    private static final int DB_VERSION = 1;
    ImageView imgBackToMainPageSTD, imgSuaTDSearch, imgSuaAnhTuVungSDTD;
    EditText edtSuaMaTuVungTD, edtSuaTuTATD, edtSuaTuTVTD, edtSuaCachPhatAmTD, edtSuaGioiTuDiKemTD, edtSuaViDuSDTD, edtSuaTDSearch, edtSuaViDuTVSDTD;
    Spinner spinnerSuaLoaiTuTD;
    Button btnSuaTV;
    RecyclerView rvSuaTuDienHienCo;
    TextView tvSuaTuVungCount;
    String[] dsLoaiTu = new String[]{"Danh từ (n)", "Động từ (v)", "Tính từ (adj)", "Trạng từ (adv)", "Đại từ (pron)", "Giới từ (prep)", "Liên từ (conj)", "Thán từ (interj)", "Mạo từ (art)"};
    ArrayList<Dictionary> dictionaryArrayListResult = new ArrayList<>();
    Admin_Add_Dictionary_Custom_Adapter admin_add_dictionary_custom_adapter;
    DictionaryHandler dictionaryHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit_dictionary);
        addControl();
        dictionaryHandler = new DictionaryHandler(Admin_Edit_Dictionary.this, DB_NAME, null, DB_VERSION);
        //------------------------------------//
        spinnerLoaiTuCreate();

        setupRecyclerView();
        loadAllDictionary();

        int soTu = dictionaryArrayListResult.size();
        tvSuaTuVungCount.setText(soTu + " từ ");
        addEvent();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack(); // Quay lại Fragment trước đó
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            // Get the image from the gallery
            Uri selectedImage = data.getData();
            try {
                // Decode the image and display it in the ImageView
                Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage));
                imgSuaAnhTuVungSDTD.setImageBitmap(bitmap);

                // Optional: Convert the bitmap to byte array if you need to store it in the database
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] imageBytes = byteArrayOutputStream.toByteArray();

                // You can now use imageBytes to save the image in the database
                // For example, you can set it to the product object
                // products.setImageProduct(imageBytes);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(this, "Unable to load image", Toast.LENGTH_SHORT).show();
            }
        }
    }

    void addControl() {
        imgBackToMainPageSTD = findViewById(R.id.imgBackToMainPageSTD);
        imgSuaTDSearch = findViewById(R.id.imgSuaTDSearch);
        imgSuaAnhTuVungSDTD = findViewById(R.id.imgSuaAnhTuVungSDTD);
        edtSuaTDSearch = findViewById(R.id.edtSuaTDSearch);
        edtSuaMaTuVungTD = findViewById(R.id.edtSuaMaTuVungTD);
        edtSuaTuTATD = findViewById(R.id.edtSuaTuTATD);
        edtSuaTuTVTD = findViewById(R.id.edtSuaTuTVTD);
        edtSuaCachPhatAmTD = findViewById(R.id.edtSuaCachPhatAmTD);
        edtSuaGioiTuDiKemTD = findViewById(R.id.edtSuaGioiTuDiKemTD);
        edtSuaViDuSDTD = findViewById(R.id.edtSuaViDuSDTD);
        edtSuaViDuTVSDTD = findViewById(R.id.edtSuaViDuTVSDTD);
        spinnerSuaLoaiTuTD = findViewById(R.id.spinnerSuaLoaiTuTD);
        btnSuaTV = findViewById(R.id.btnSuaTV);
        rvSuaTuDienHienCo = findViewById(R.id.rvSuaTuDienHienCo);
        tvSuaTuVungCount = findViewById(R.id.tvSuaTuVungCount);
    }

    void addEvent() {
        imgBackToMainPageSTD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        imgSuaTDSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dictionarySearchValue = edtSuaTDSearch.getText().toString().trim();
                if (dictionarySearchValue.isEmpty()) {
                    loadAllDictionary();
                } else {
                    searchDictionary(dictionarySearchValue);
                    edtSuaTDSearch.setText("");
                }
            }
        });

        imgSuaAnhTuVungSDTD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 1);
            }
        });

        btnSuaTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String maTD = edtSuaMaTuVungTD.getText().toString().trim();
                String tuTATD = edtSuaTuTATD.getText().toString().trim();
                String tuTVTD = edtSuaTuTVTD.getText().toString().trim();
                String gioiTuTD = edtSuaGioiTuDiKemTD.getText().toString().trim();
                String cachPhatAmTD = edtSuaCachPhatAmTD.getText().toString().trim();
                String loaiTuTD = spinnerSuaLoaiTuTD.getSelectedItem().toString();
                String viDu = edtSuaViDuSDTD.getText().toString().trim();
                String viDuTiengViet =  edtSuaViDuTVSDTD.getText().toString().trim();
                Bitmap anhTuVung = getBitmapFromImageView(imgSuaAnhTuVungSDTD);

                if (maTD.isEmpty() || tuTATD.isEmpty() || tuTVTD.isEmpty() ||  gioiTuTD.isEmpty() || cachPhatAmTD.isEmpty() || loaiTuTD.isEmpty() || viDu.isEmpty() || viDuTiengViet.isEmpty()) {
                    Toast.makeText(Admin_Edit_Dictionary.this, "Điền đầy đủ thông tin trước khi thêm.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (dictionaryHandler.checkDictionaryByNameAndCode(tuTATD, maTD)) {
                    Toast.makeText(Admin_Edit_Dictionary.this, "Từ tiếng Anh này đã tồn tại. Kiểm tra lại", Toast.LENGTH_SHORT).show();
                    return;
                }

                Dictionary dictionary = new Dictionary();
                dictionary.setMaTuVung(maTD);
                dictionary.setTuTiengAnh(tuTATD);
                dictionary.setTuTiengViet(tuTVTD);
                dictionary.setGioiTuDiKem(gioiTuTD);
                dictionary.setCachPhatAm(cachPhatAmTD);
                dictionary.setLoaiTu(loaiTuTD);
                dictionary.setViDu(viDu);
                dictionary.setViDuTiengViet(viDuTiengViet);
                dictionary.setAnhTuVung(getBytesFromBitmap(anhTuVung));

                createAlertDialogEditDictionary(dictionary).show();
            }
        });
    }

    void loadAllDictionary() {
        dictionaryArrayListResult.clear();
        dictionaryArrayListResult = dictionaryHandler.loadAllDataOfDictionary();
        admin_add_dictionary_custom_adapter.setDictionaryList(dictionaryArrayListResult);
    }

    void searchDictionary(String searchQuery) {
        dictionaryArrayListResult.clear();
        dictionaryArrayListResult = dictionaryHandler.searchDictionaryByNameOrCode(searchQuery);
        admin_add_dictionary_custom_adapter.setDictionaryList(dictionaryArrayListResult);
    }

    void spinnerLoaiTuCreate() {
        String[] dsLoaiTuString = new String[dsLoaiTu.length];
        for (int i = 0; i < dsLoaiTu.length; i++) {
            dsLoaiTuString[i] = String.valueOf(dsLoaiTu[i]);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dsLoaiTuString);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSuaLoaiTuTD.setAdapter(adapter);
    }

    void setupRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Admin_Edit_Dictionary.this, RecyclerView.VERTICAL, false);
        rvSuaTuDienHienCo.addItemDecoration(new DividerItemDecoration(Admin_Edit_Dictionary.this, DividerItemDecoration.VERTICAL));
        rvSuaTuDienHienCo.setLayoutManager(layoutManager);
        rvSuaTuDienHienCo.setItemAnimator(new DefaultItemAnimator());
        admin_add_dictionary_custom_adapter = new Admin_Add_Dictionary_Custom_Adapter(dictionaryArrayListResult, new Admin_Add_Dictionary_Custom_Adapter.ItemClickListener() {
            @Override
            public void onItemClick(Dictionary dictionary) {
                edtSuaMaTuVungTD.setText(dictionary.getMaTuVung());
                edtSuaTuTATD.setText(dictionary.getTuTiengAnh());
                edtSuaTuTVTD.setText(dictionary.getTuTiengViet());
                edtSuaGioiTuDiKemTD.setText(dictionary.getGioiTuDiKem());
                edtSuaCachPhatAmTD.setText(dictionary.getCachPhatAm());
                edtSuaViDuSDTD.setText(dictionary.getViDu());
                edtSuaViDuTVSDTD.setText(dictionary.getViDuTiengViet());

                byte[] imageBytes = dictionary.getAnhTuVung(); // Lấy ảnh từ đối tượng Dictionary
                if (imageBytes != null) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                    imgSuaAnhTuVungSDTD.setImageBitmap(bitmap); // Thiết lập hình ảnh cho ImageView
                } else {
                    imgSuaAnhTuVungSDTD.setImageResource(R.drawable.no_img); // Hình ảnh mặc định nếu không có
                }

                int index = -1;
                for (int i = 0; i < dsLoaiTu.length; i++) {
                    if (dsLoaiTu[i].equals(dictionary.getLoaiTu())) {
                        index = i;
                        break;
                    }
                }
                if (index != -1) {
                    spinnerSuaLoaiTuTD.setSelection(index);
                }
            }
        });
        rvSuaTuDienHienCo.setAdapter(admin_add_dictionary_custom_adapter);
    }

    public Bitmap getBitmapFromImageView(ImageView imageView) {
        Drawable drawable = imageView.getDrawable();
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            bitmap = ((BitmapDrawable) drawable).getBitmap();
        } else {
            int width = drawable.getIntrinsicWidth();
            int height = drawable.getIntrinsicHeight();
            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
        }
//        if (!Activity_Updating_Products.isImageSizeUnderLimit(bitmap, 100)) {
//            Toast.makeText(this, "Image size is too large! It should be less than 100KB.", Toast.LENGTH_SHORT).show();
//            return null;
//        }
        return bitmap;
    }

    public byte[] getBytesFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    private void clearInputFields() {
        edtSuaMaTuVungTD.setText("");
        edtSuaTuTATD.setText("");
        edtSuaTuTVTD.setText("");
        edtSuaGioiTuDiKemTD.setText("");
        edtSuaCachPhatAmTD.setText("");
        edtSuaViDuSDTD.setText("");
        edtSuaViDuTVSDTD.setText("");
        imgSuaAnhTuVungSDTD.setImageResource(R.drawable.no_img);

        spinnerSuaLoaiTuTD.setSelection(0);
    }

    private AlertDialog createAlertDialogEditDictionary(Dictionary dictionary) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Admin_Edit_Dictionary.this);
        builder.setTitle("Edit Dictionary");
        builder.setMessage("Bạn có muốn sửa từ vựng này không ?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dictionaryHandler.updateDictionary(dictionary);
                loadAllDictionary();
                Toast.makeText(Admin_Edit_Dictionary.this, "Sửa thành công.", Toast.LENGTH_SHORT).show();
                clearInputFields();
                int soTu = dictionaryArrayListResult.size();
                tvSuaTuVungCount.setText(soTu + " từ ");
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        return builder.create();
    }
}