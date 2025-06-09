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
import android.text.style.TtsSpan;
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
import com.example.doanungdung2.Controller.ExerciseHandler;
import com.example.doanungdung2.Model.Dictionary;
import com.example.doanungdung2.Model.Exercise;
import com.example.doanungdung2.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

public class Admin_Add_Dictionary extends AppCompatActivity {

    private static final String DB_NAME = "AppHocTiengAnh";
    private static final int DB_VERSION = 1;
    ImageView imgBackToMainPageTTD, imgThemTDRefresh, imgThemAnhTuVungSDTD;
    EditText edtThemMaTuVungTD, edtThemTuTATD, edtThemTuTVTD, edtThemCachPhatAmTD, edtThemGioiTuDiKemTD, edtThemViDuSDTD, edtThemViDuTVSDTD;
    Spinner spinnerThemLoaiTuTD;
    Button btnThemTV;
    RecyclerView rvThemTuDienHienCo;
    TextView tvThemTuVungCount;
    String[] dsLoaiTu = new String[]{"Danh từ (n)", "Động từ (v)", "Tính từ (adj)", "Trạng từ (adv)", "Đại từ (pron)", "Giới từ (prep)", "Liên từ (conj)", "Thán từ (interj)", "Mạo từ (art)"};
    ArrayList<Dictionary> dictionaryArrayListResult = new ArrayList<>();
    Admin_Add_Dictionary_Custom_Adapter admin_add_dictionary_custom_adapter;
    DictionaryHandler dictionaryHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_dictionary);
        addControl();
        dictionaryHandler = new DictionaryHandler(Admin_Add_Dictionary.this, DB_NAME, null, DB_VERSION);
        //------------------------------------//
        String maBaiTap = createAutoDictionaryCode("TD");
        edtThemMaTuVungTD.setText(maBaiTap);
        spinnerLoaiTuCreate();

        setupRecyclerView();
        loadAllDictionary();

        int soTu = dictionaryArrayListResult.size();
        tvThemTuVungCount.setText(soTu + " từ ");
        addEvent();
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack(); // Quay lại Fragment trước đó
        super.onBackPressed();
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
                imgThemAnhTuVungSDTD.setImageBitmap(bitmap);

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
        imgThemTDRefresh = findViewById(R.id.imgThemTDRefresh);
        imgBackToMainPageTTD = findViewById(R.id.imgBackToMainPageTTD);
        imgThemAnhTuVungSDTD = findViewById(R.id.imgThemAnhTuVungSDTD);
        edtThemMaTuVungTD = findViewById(R.id.edtThemMaTuVungTD);
        edtThemTuTATD = findViewById(R.id.edtThemTuTATD);
        edtThemTuTVTD = findViewById(R.id.edtThemTuTVTD);
        edtThemCachPhatAmTD = findViewById(R.id.edtThemCachPhatAmTD);
        edtThemGioiTuDiKemTD = findViewById(R.id.edtThemGioiTuDiKemTD);
        edtThemViDuSDTD = findViewById(R.id.edtThemViDuSDTD);
        edtThemViDuTVSDTD = findViewById(R.id.edtThemViDuTVSDTD);
        spinnerThemLoaiTuTD = findViewById(R.id.spinnerThemLoaiTuTD);
        btnThemTV = findViewById(R.id.btnThemTV);
        rvThemTuDienHienCo = findViewById(R.id.rvThemTuDienHienCo);
        tvThemTuVungCount = findViewById(R.id.tvThemTuVungCount);
    }

    void addEvent() {
        imgBackToMainPageTTD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        imgThemAnhTuVungSDTD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 1);
            }
        });

        btnThemTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String maTD = edtThemMaTuVungTD.getText().toString().trim();
                String tuTATD = edtThemTuTATD.getText().toString().trim();
                String tuTVTD = edtThemTuTVTD.getText().toString().trim();
                String gioiTuTD = edtThemGioiTuDiKemTD.getText().toString().trim();
                String cachPhatAmTD = edtThemCachPhatAmTD.getText().toString().trim();
                String loaiTuTD = spinnerThemLoaiTuTD.getSelectedItem().toString();
                String viDu = edtThemViDuSDTD.getText().toString().trim();
                String viDuTiengViet =  edtThemViDuTVSDTD.getText().toString().trim();
                Bitmap anhTuVung = getBitmapFromImageView(imgThemAnhTuVungSDTD);

                if (maTD.isEmpty() || tuTATD.isEmpty() || tuTVTD.isEmpty() ||  gioiTuTD.isEmpty() || cachPhatAmTD.isEmpty() || loaiTuTD.isEmpty() || viDu.isEmpty() || viDuTiengViet.isEmpty()) {
                    Toast.makeText(Admin_Add_Dictionary.this, "Điền đầy đủ thông tin trước khi thêm.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (dictionaryHandler.checkIfDictionaryCodeExist(maTD)) {
                    Toast.makeText(Admin_Add_Dictionary.this, "Vui lòng không sử dụng trùng mã từ vựng.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (dictionaryHandler.checkDictionaryByNameAndCode(tuTATD, maTD)) {
                    Toast.makeText(Admin_Add_Dictionary.this, "Từ tiếng Anh này đã tồn tại. Kiểm tra lại", Toast.LENGTH_SHORT).show();
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

                createAlertDialogAddDictionary(dictionary).show();
            }
        });

        imgThemTDRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearInputFields();
            }
        });

    }

    void loadAllDictionary() {
        dictionaryArrayListResult.clear();
        dictionaryArrayListResult = dictionaryHandler.loadAllDataOfDictionary();
        admin_add_dictionary_custom_adapter.setDictionaryList(dictionaryArrayListResult);
    }

    public static String createAutoDictionaryCode(String kyTuDau)
    {
        Random random = new Random();
        // Tạo chuỗi số ngẫu nhiên 9 chữ số
        StringBuilder code = new StringBuilder(kyTuDau);
        for (int i = 0; i < 9; i++) {
            int digit = random.nextInt(10); // Tạo số ngẫu nhiên từ 0 đến 9
            code.append(digit);
        }
        return code.toString();
    }

    void spinnerLoaiTuCreate() {
        String[] dsLoaiTuString = new String[dsLoaiTu.length];
        for (int i = 0; i < dsLoaiTu.length; i++) {
            dsLoaiTuString[i] = String.valueOf(dsLoaiTu[i]);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dsLoaiTuString);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerThemLoaiTuTD.setAdapter(adapter);
    }

    void setupRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Admin_Add_Dictionary.this, RecyclerView.VERTICAL, false);
        rvThemTuDienHienCo.addItemDecoration(new DividerItemDecoration(Admin_Add_Dictionary.this, DividerItemDecoration.VERTICAL));
        rvThemTuDienHienCo.setLayoutManager(layoutManager);
        rvThemTuDienHienCo.setItemAnimator(new DefaultItemAnimator());
        admin_add_dictionary_custom_adapter = new Admin_Add_Dictionary_Custom_Adapter(dictionaryArrayListResult, new Admin_Add_Dictionary_Custom_Adapter.ItemClickListener() {
            @Override
            public void onItemClick(Dictionary dictionary) {
                edtThemMaTuVungTD.setText(dictionary.getMaTuVung());
                edtThemTuTATD.setText(dictionary.getTuTiengAnh());
                edtThemTuTVTD.setText(dictionary.getTuTiengViet());
                edtThemGioiTuDiKemTD.setText(dictionary.getGioiTuDiKem());
                edtThemCachPhatAmTD.setText(dictionary.getCachPhatAm());
                edtThemViDuSDTD.setText(dictionary.getViDu());
                edtThemViDuTVSDTD.setText(dictionary.getViDuTiengViet());

                byte[] imageBytes = dictionary.getAnhTuVung(); // Lấy ảnh từ đối tượng Dictionary
                if (imageBytes != null) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                    imgThemAnhTuVungSDTD.setImageBitmap(bitmap); // Thiết lập hình ảnh cho ImageView
                } else {
                    imgThemAnhTuVungSDTD.setImageResource(R.drawable.no_img); // Hình ảnh mặc định nếu không có
                }

                int index = -1;
                for (int i = 0; i < dsLoaiTu.length; i++) {
                    if (dsLoaiTu[i].equals(dictionary.getLoaiTu())) {
                        index = i;
                        break;
                    }
                }
                if (index != -1) {
                    spinnerThemLoaiTuTD.setSelection(index);
                }
            }
        });
        rvThemTuDienHienCo.setAdapter(admin_add_dictionary_custom_adapter);
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
        edtThemMaTuVungTD.setText(createAutoDictionaryCode("TD"));
        edtThemTuTATD.setText("");
        edtThemTuTVTD.setText("");
        edtThemGioiTuDiKemTD.setText("");
        edtThemCachPhatAmTD.setText("");
        edtThemViDuSDTD.setText("");
        edtThemViDuTVSDTD.setText("");
        imgThemAnhTuVungSDTD.setImageResource(R.drawable.no_img);

        spinnerThemLoaiTuTD.setSelection(0);
    }

    private AlertDialog createAlertDialogAddDictionary(Dictionary dictionary) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Admin_Add_Dictionary.this);
        builder.setTitle("Add Dictionary");
        builder.setMessage("Bạn có muốn thêm từ vựng này không ?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dictionaryHandler.insertDictionary(dictionary);
                loadAllDictionary();
                Toast.makeText(Admin_Add_Dictionary.this, "Thêm thành công.", Toast.LENGTH_SHORT).show();
                clearInputFields();
                int soTu = dictionaryArrayListResult.size();
                tvThemTuVungCount.setText(soTu + " từ ");
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